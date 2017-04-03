import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NarcissisticNumbersGenerator {
    private int power(int base, int exponent) {
        int res = 1;
        for (int i = 0; i < exponent; i++) {
            res *= base;
        }
        return res;
    }

    private List<Integer> getDigits(int number) {
        List<Integer> digits = new ArrayList<>();

        while (number > 0) {
            digits.add(number % 10);
            number /= 10;
        }

        return digits;
    }

    private boolean isNarcissisticNumber(int number) {
        List<Integer> digits = getDigits(number);
        int sum = 0;
        for (Integer i : digits) {
            sum += power(i, digits.size());
        }
        return sum == number;
    }

    public void printNarcissisticNumbers(int intervalStart, int intervalEnd, int step) {
        for (int i = intervalStart; i < intervalEnd; i += step) {
            if (isNarcissisticNumber(i)) {
                System.out.println(i);
            }
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int intervalStart = in.nextInt();
        int intervalEnd = in.nextInt();
        int threadCount = in.nextInt();

        for (int i = 0; i < threadCount; i++) {
            int finalI = i;
            ((Runnable) () -> new NarcissisticNumbersGenerator()
                    .printNarcissisticNumbers(intervalStart + finalI, intervalEnd, threadCount)).run();
        }
    }
}
