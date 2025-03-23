import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите 4 числа типа long, разделенных пробелами:");

        try {
            // Читаем всю строку целиком
            String inputLine = scanner.nextLine();

            // Разделяем строку на отдельные числа, используя пробел в качестве разделителя
            String[] numbers = inputLine.split(" ");

            // Проверяем, что было введено ровно 4 числа
            if (numbers.length != 4) {
                System.out.println("Ошибка: Введено не 4 числа.");
                return; // Завершаем программу
            }

            // Преобразуем строки в long и сохраняем их в массив
            long[] longNumbers = new long[4];
            for (int i = 0; i < 4; i++) {
                try {
                    longNumbers[i] = Long.parseLong(numbers[i]);
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: Некорректный формат числа в позиции " + (i + 1) + ".");
                    return; // Завершаем программу
                }
            }

            // Выводим введенные числа для проверки (или выполняем с ними нужные действия)
            System.out.println("Введенные числа:");
            for (long number : longNumbers) {
                System.out.print(number + " ");
            }
            System.out.println(); // Переход на новую строку

        } finally {
            scanner.close(); // Закрываем Scanner, чтобы освободить ресурсы
        }
    }
}