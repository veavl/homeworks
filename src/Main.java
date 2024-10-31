import java.util.Scanner;
import java.util.Arrays;
import java.io.IOException;

public class Main {     // add comment 31.10.2024

    static boolean have_task = true;

    public static void main(String[] args) throws IOException {
        while (have_task) {
            process();
        }
    }

    public static void process() throws IOException {
        Scanner str_input = new Scanner(System.in);
        System.out.println("Input:");
        System.out.println(calc(str_input.nextLine())); // the calc()
    }

    public static String calc(String input) throws IOException {

        String[] numbers = input.split(" ");
        String[] romanNumbers = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        String[] arabicNumbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        String[] allNumbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        String[] operators = {"+","-","*","/"};
        int result;                         // результат арифметических операций
        String resultStr;                   // сразу переводим результат арифметических операций в строку
        String plus = "+";
        String minus = "-";
        String multiply = "*";
        String divide = "/";

        
        // str_input_again
        if (numbers.length != 3) {
            Scanner str_input_again = new Scanner(System.in);
            System.out.println("Введите выражение, разделяя каждый символ _пробелом_");
            input = str_input_again.nextLine();
            return calc(input);
        }

        // 1. Проверяем введенный оператор
        String operator = numbers[1];
        boolean foundOperator = Arrays.asList(operators).contains(operator); // преобразуем массив в список и проверяем наличие элемента
        if (!foundOperator) {
            throw new IOException("Программа завершена: выражение не является математической операцией: (+, -, /, *)");
        }

        // 2. Получаем введенные числа и проверяем их наличие в массиве allNumbers
        String arabicOrRoman1 = numbers[0];
        String arabicOrRoman2 = numbers[2];
        boolean found1 = Arrays.asList(allNumbers).contains(arabicOrRoman1);
        boolean found2 = Arrays.asList(allNumbers).contains(arabicOrRoman2);

        // 2.1 Если есть в allNumbers: проверяем их наличие в массивах Арабских и Римских чисел
        if (found1 && found2) {

            boolean foundArabic1 = Arrays.asList(arabicNumbers).contains(arabicOrRoman1);
            boolean foundArabic2 = Arrays.asList(arabicNumbers).contains(arabicOrRoman2);

            boolean foundRoman1 = Arrays.asList(romanNumbers).contains(arabicOrRoman1);
            boolean foundRoman2 = Arrays.asList(romanNumbers).contains(arabicOrRoman2);

            // Оба числа -- Арабские
            if (foundArabic1 && foundArabic2) {
                int num1 = arabicToNumber(arabicOrRoman1);  // Конвертируем строки в числа arabicToNumber()
                int num2 = arabicToNumber(arabicOrRoman2);
                if (operator.equals(plus)) {              // equals() -- сравниваем строки
                    result = num1 + num2;
                    input = Integer.toString(result);
                } else if (operator.equals(multiply)) {
                    result = num1 * num2;
                    input = Integer.toString(result);
                } else if (operator.equals(divide)) {
                    result = num1/num2;
                    input = Integer.toString(result);
                } else if (operator.equals(minus)) {
                    result = num1 - num2;
                    input = Integer.toString(result);
                }
            }
            // Оба числа -- Римские
            else if (foundRoman1 && foundRoman2) {
                int num1 = romanToNumber(arabicOrRoman1);      // Конвертируем строки в числа romanToNumber()
                int num2 = romanToNumber(arabicOrRoman2);
                if (operator.equals(plus)) {
                    resultStr = Integer.toString(num1 + num2); // Переводим результата арифметической операции в строку
                    input = IntToRoman(resultStr);             // Строку "Арабскую" конвертируем в "Римскую"
                } else if (numbers[1].equals(multiply)) {
                    resultStr = Integer.toString(num1 * num2);
                    input = IntToRoman(resultStr);
                } else if (numbers[1].equals(divide)) {
                    resultStr = Integer.toString(num1 / num2);
                    input = IntToRoman(resultStr);
                } else if (operator.equals(minus)) {
                    result = num1 - num2;
                    if (result <= 0) {
                            throw new IOException("Программа завершена: в римской системе нет отрицательных чисел");
                    }
                    resultStr = Integer.toString(result);
                    input = IntToRoman(resultStr);
                }
            } // Римское - Арабское
            else {
                throw new IOException("Программа завершена: используются одновременно разные системы счисления");
            }
        // Нет в массиве allNumbers
        } else {
            Scanner str_input_again = new Scanner(System.in);
            System.out.println("Введите римские или арабские числа от 1 до 10");
            input = str_input_again.nextLine();
            return calc(input);
        }
        System.out.println("Output:");
        return input;
    } // END calc();

    // Преобразование Арабского числа в Римское (метод получает и возвращает строку)
    public static String IntToRoman(String numStr) {

        int num = Integer.parseInt(numStr);

        String[] roman = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] arabic = {100, 90, 50, 40, 10, 9, 5, 4, 1};

        StringBuilder ret = new StringBuilder(); // Изменяемая строка
        int ind = 0;

        while (ind < roman.length) {
            while (num >= arabic[ind]) {
                int d = num / arabic[ind];
                num = num % arabic[ind];
                for (int i = 0; i < d; i++) {
                    ret.append(roman[ind]);
                }
            }
            ind++;
        }
        return ret.toString(); // toString - возвращает значение, переданное ему, в строковом формате.
    }

    private static int romanToNumber(String letter) {
        switch (letter) {
            case "I":
                return 1;
            case "II":
                return 2;
            case "III":
                return 3;
            case "IV":
                return 4;
            case "V":
                return 5;
            case "VI":
                return 6;
            case "VII":
                return 7;
            case "VIII":
                return 8;
            case "IX":
                return 9;
            case "X":
                return 10;
            default:
                return -1;
        }
    }

    private static int arabicToNumber(String letter) {
        switch (letter) {
            case "1":
                return 1;
            case "2":
                return 2;
            case "3":
                return 3;
            case "4":
                return 4;
            case "5":
                return 5;
            case "6":
                return 6;
            case "7":
                return 7;
            case "8":
                return 8;
            case "9":
                return 9;
            case "10":
                return 10;
            default:
                return -1;
        }
    }
}

