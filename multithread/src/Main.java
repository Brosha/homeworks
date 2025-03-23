import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        double[] numbers = new double[4];

        System.out.println("Введите 4 числа типа double, разделенных пробелами:");

        try {

            String inputLine = scanner.nextLine();


            String[] input = inputLine.split(" ");


            if (input.length != 4) {
                System.out.println("Ошибка: Введено не 4 числа.");
                return;
            }

            for (int i = 0; i < 4; i++) {
                try {
                    numbers[i] = Double.parseDouble(input[i]);
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: Некорректный формат числа в позиции " + (i + 1) + ".");
                    return;
                }
            }


            System.out.println("Введенные числа:");
            for (double number : numbers) {
                System.out.print(number + " ");
            }
            System.out.println();

        } finally {
            scanner.close();
        }

        CompletableFuture<Double> sumSQuaresFuture = CompletableFuture.supplyAsync(() -> calculateSumOfSquares(numbers[0], numbers[1]));
        CompletableFuture<Double> logFuture = CompletableFuture.supplyAsync(() -> calculateLogarithm(numbers[2]));
        CompletableFuture<Double> rootFuture = CompletableFuture.supplyAsync(() -> calculateSquareRoot(numbers[3]));
        CompletableFuture<Double> multiplyFutures = sumSQuaresFuture.thenCombine(logFuture, (x, y) -> x * y);
        CompletableFuture<Double> resultFuture = multiplyFutures.thenCombine(rootFuture, (x, y) -> x / y);

        System.out.println("Final result of the formula: " + resultFuture.get());


    }

    private static double calculateSumOfSquares(double a, double b) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        double result = a * a + b * b;
        System.out.println("Calculating sum of squares: 25.0: " + result);
        return result;
    }

    private static double calculateLogarithm(double c) {
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        double result = Math.log(c);
        System.out.println("Calculating log(c): " + result);
        return result;
    }

    private static double calculateSquareRoot(double d) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        double result = Math.sqrt(d);
        System.out.println("Calculating sqrt(d): " + result);
        return result;
    }

    // Метод для вычисления окончательного результата
    private static int calculateFinalResult(int product, int e) {
        System.out.println("Вычисляем окончательный результат в потоке: " + Thread.currentThread().getName());
        return product + e;
    }

}