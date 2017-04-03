import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class NarcissisticNumbersGenerator {
    private int power(int base, int exponent) {
        return IntStream.range(0, exponent).reduce(1, (acc, i) -> acc * base);
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
        final List<Integer> digits = getDigits(number);
        final int sum = digits.stream().reduce(0, (acc, digit) -> acc + power(digit, digits.size()));
        return sum == number;
    }

    public List<Integer> getNarcissisticNumbers(int intervalStart, int intervalEnd, int step) {
        List<Integer> narcissisticNumbers = new ArrayList<>();
        for (int i = intervalStart; i < intervalEnd; i += step) {
            if (isNarcissisticNumber(i)) {
                narcissisticNumbers.add(i);
            }
        }
        return narcissisticNumbers;
    }

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        int intervalStart = in.nextInt();
        int intervalEnd = in.nextInt();
        int threadCount = in.nextInt();
        Long nanoStart = System.nanoTime();
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        List<List<Integer>> lists = new ArrayList<>();
        IntStream.range(0, threadCount).forEach((int i) -> {
            Future<List<Integer>> list = executorService.submit((Callable<List<Integer>>) () -> new NarcissisticNumbersGenerator()
                    .getNarcissisticNumbers(intervalStart + i, intervalEnd, threadCount));
            try {
                lists.add(list.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
        Long nanoEnd = System.nanoTime();
        lists.forEach(l -> l.forEach(System.out::println));
        System.out.println("Time: " + (nanoEnd - nanoStart) / 1000000);
    }
}
