import java.util.concurrent.*;
import java.util.logging.Logger;

public class Main {
    private static final Logger demoLogger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws InterruptedException {
        // Инициализация пула
        CustomExecutor pool = new CustomThreadPoolExecutor(
                2,  // corePoolSize
                4,  // maxPoolSize
                5,  // keepAliveTime
                TimeUnit.SECONDS,
                5,  // queueSize
                2   // minSpareThreads
        );

        // Запуск 10 задач
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            try {
                pool.execute(() -> {
                    demoLogger.info(() -> "Task " + taskId + " started");
                    try {
                        Thread.sleep(2000); // Имитация работы
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    demoLogger.info(() -> "Task " + taskId + " finished");
                });
            } catch (Exception e) {
                demoLogger.warning(() -> "Task " + taskId + " rejected: " + e.getMessage());
            }
        }

        Thread.sleep(10000); // Ожидаем 10 секунд
        pool.shutdown();

        try {
            pool.execute(() -> System.out.println("This should not run"));
        } catch (Exception e) {
            demoLogger.warning("Task after shutdown rejected (expected)");
        }
    }
}