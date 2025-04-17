import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PerformanceTest {
    public static void main(String[] args) throws InterruptedException {
        testPool("CustomPool", new CustomThreadPoolExecutor(2, 4, 5, TimeUnit.SECONDS, 5, 2));
        testPool("FixedThreadPool", Executors.newFixedThreadPool(4));
        testPool("CachedThreadPool", Executors.newCachedThreadPool());
    }

    private static void testPool(String poolName, Executor pool) throws InterruptedException {
        long start = System.currentTimeMillis();
        int tasks = 1000;

        for (int i = 0; i < tasks; i++) {
            final int taskId = i;
            try {
                pool.execute(() -> {
                    try { Thread.sleep(10); } catch (InterruptedException ignored) {}
                });
            } catch (Exception e) {
                System.out.println("Task " + taskId + " rejected in " + poolName);
            }
        }

        if (pool instanceof ExecutorService) {
            ((ExecutorService) pool).shutdown();
            ((ExecutorService) pool).awaitTermination(1, TimeUnit.MINUTES);
        } else {
            Thread.sleep(5000); // 5 секунд
            ((CustomExecutor) pool).shutdown();
        }

        long end = System.currentTimeMillis();
        System.out.println(poolName + " time: " + (end - start) + " ms");
    }
}