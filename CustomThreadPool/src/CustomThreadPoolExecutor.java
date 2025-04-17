import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;

public class CustomThreadPoolExecutor implements CustomExecutor {

    private final int corePoolSize;
    private final int maxPoolSize;
    private final long keepAliveTime;
    private final TimeUnit timeUnit;
    private final int queueSize;
    private final int minSpareThreads;
    private final CustomThreadFactory threadFactory;
    private final String poolName = "MyCustomPool";

    private final List<Worker> workers = new ArrayList<>();
    private final List<BlockingQueue<Runnable>> queues = new ArrayList<>();
    private final AtomicInteger currentQueueIndex = new AtomicInteger(0);
    private final ReentrantLock mainLock = new ReentrantLock();
    private volatile boolean isShutdown = false;

    private static final Logger logger = Logger.getLogger(CustomThreadPoolExecutor.class.getName());

    public CustomThreadPoolExecutor(int corePoolSize, int maxPoolSize, long keepAliveTime, TimeUnit timeUnit, int queueSize, int minSpareThreads) {

        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        this.keepAliveTime = keepAliveTime;
        this.timeUnit = timeUnit;
        this.queueSize = queueSize;
        this.minSpareThreads = minSpareThreads;
        this.threadFactory = new CustomThreadFactory(poolName);

        int initialThreads = Math.max(corePoolSize, minSpareThreads);
        for (int i = 0; i < initialThreads; i++) {
            addWorker();
        }
    }


    /**
     * @param command the runnable task
     */
    @Override
    public void execute(Runnable command) {
        if (command == null)
            throw new NullPointerException();

        if (isShutdown) {
            logger.warning(() -> "ThreadPool is shutdown. Task rejected: " + command);
            return;

        }
        mainLock.lock();
        try {
            if (isShutdown) {
                logger.warning(() -> "ThreadPool is shutdown. Task rejected: " + command);
                return;
            }
            // Проверка минимального количества свободных потоков
            if (getFreeWorkers() < minSpareThreads && workers.size() < maxPoolSize) {
                addWorker();
            }
            // Поиск очереди со свободным местом
            boolean taskAdded = false;
            int startIndex = currentQueueIndex.get();
            for (int i = 0; i < queues.size(); i++) {
                int index = (startIndex + i) % queues.size();
                BlockingQueue<Runnable> queue = queues.get(index);

                if (queue.offer(command)) {
                    currentQueueIndex.set((index + 1) % queues.size());
                    logger.info(() -> "[Pool] Task added to queue #" + index + ": " + command);
                    taskAdded = true;
                    break;
                }
            }

            // Если все очереди заполнены, создаём новый поток (если возможно)
            if (!taskAdded) {
                if (workers.size() < maxPoolSize) {
                    addWorker();
                    queues.get(queues.size() - 1).offer(command);
                    logger.info(() -> "[Pool] Created new worker for task: " + command);
                } else {
                    logger.warning(() -> "[Rejected] Task " + command + " rejected!");
                }
            }
        } finally {
            mainLock.unlock();
        }
    }

    /**
     * @param //callable
     * @param <T>
     * @return
     */
    @Override
    public <T> Future<T> submit(Callable<T> task) {
        logger.info(() -> "[Pool] Submitting task: " + task);
        FutureTask<T> future = new FutureTask<>(task);
        execute(future);
        return future;
    }

    /**
     *
     */
    @Override
    public void shutdown() {
        mainLock.lock();
        try {
            isShutdown = true;
            workers.forEach(Worker::shutdown);
        } finally {
            mainLock.unlock();
        }

    }

    /**
     *
     */
    @Override
    public void shutdownNow() {
        mainLock.lock();
        try {
            isShutdown = true;
            workers.forEach(Worker::shutdown);
            queues.forEach(BlockingQueue::clear);
            logger.info("All tasks cleared and workers interrupted.");
        } finally {
            mainLock.unlock();
        }
    }

    private void addWorker() {
        mainLock.lock();
        try {
            BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(queueSize);
            queues.add(queue);
            Worker worker = new Worker(queue);
            workers.add(worker);
            worker.start();
            logger.info(() -> "Added new worker: " + worker.getName());
        } finally {
            mainLock.unlock();
        }
    }

    private int getFreeWorkers() {
        return (int) workers.stream().filter(Worker::isIdle).count();
    }

    private class Worker implements Runnable {
        private final BlockingQueue<Runnable> taskQueue; // Своя очередь задач
        private final Thread thread;
        private volatile boolean isIdle = true;
        private volatile boolean isRunning = true;


        public Worker(BlockingQueue<Runnable> queue) {
            this.taskQueue = queue;
            this.thread = threadFactory.newThread(this);
        }

        public void start() {
            thread.start();
            logger.info(() -> "Worker started: " + thread.getName());

        }

        public boolean isIdle() {
            return isIdle;
        }

        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted() && isRunning) {
                    isIdle = true;
                    Runnable task = taskQueue.poll(keepAliveTime, timeUnit);

                    if (task != null) {
                        isIdle = false;
                        logger.info(() -> "[Worker] " + thread.getName() + " executes " + task);
                        task.run();
                    } else if (isShutdown) {
                        logger.info(() -> "[Worker] " + thread.getName() + " shutdown requested.");
                    }
                    else if (shouldTerminate()) {
                        logger.info(() -> "[Worker] Idle timeout, terminating: " + thread.getName());
                        break;
                    }
                }
            } catch (InterruptedException e) {
                logger.info(() -> "Worker interrupted: " + thread.getName());
            } finally {
                cleanup();
            }
        }

        private boolean shouldTerminate() {
            // Завершаем поток, если общее количество > corePoolSize
            return workers.size() > corePoolSize
                    && (workers.size() - 1) >= minSpareThreads;
        }

        private void cleanup() {
            mainLock.lock();
            try {
                workers.remove(this);
                queues.remove(taskQueue);
                if (!isShutdown && getFreeWorkers() < minSpareThreads && workers.size() < maxPoolSize) {
                    addWorker();
                }
            } finally {
                mainLock.unlock();
            }
            logger.info(() -> "Worker terminated: " + thread.getName());
        }

        public void shutdown() {
            thread.interrupt();
            isRunning = false;
            logger.info("Shutdown initiated.");
        }


        public String getName() {
            return thread.getName();
        }
    }


    private class CustomThreadFactory implements ThreadFactory {
        private final AtomicInteger threadCounter = new AtomicInteger(0);
        private final String poolName;
        private static final Logger logger = Logger.getLogger(CustomThreadFactory.class.getName());

        public CustomThreadFactory(String poolName) {
            this.poolName = poolName;
        }

        /**
         * Constructs a new unstarted {@code Thread} to run the given runnable.
         *
         * @param r a runnable to be executed by new thread instance
         * @return constructed thread, or {@code null} if the request to
         * create a thread is rejected
         * @see <a href="../../lang/Thread.html#inheritance">Inheritance when
         * creating threads</a>
         */
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, poolName + threadCounter.incrementAndGet());
            logger.info(() -> "[ThreadFactory] Creating new thread: " + thread.getName());
            return thread;
        }
    }
}
