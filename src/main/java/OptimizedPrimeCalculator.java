import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OptimizedPrimeCalculator {

    public List<Integer> getPrimes(int maxPrime) throws InterruptedException {

        if (maxPrime < 0)
            throw new UnsupportedOperationException();
        if (maxPrime <= 1)
            return new ArrayList<>();
        if (maxPrime == 2)
            return List.of(2);

        var maxDivider = (int) Math.sqrt(maxPrime);
        var basePrimes = Collections.unmodifiableList(new LinearPrimeCalculator().getPrimes(maxDivider));
        var primeNumbers = Collections.synchronizedList(new LinkedList<>(basePrimes));

        int numberOfCores = Runtime.getRuntime().availableProcessors();
        ExecutorService executors = Executors.newFixedThreadPool(numberOfCores);

//        var delta = Math.max(numberOfCores, (maxPrime - maxDivider) / numberOfCores);
//        CountDownLatch latch = new CountDownLatch((int) Math.ceil((double) (maxPrime - maxDivider) / delta));

        var delta = maxDivider;
        CountDownLatch latch = new CountDownLatch((int) Math.ceil((double) (maxPrime - maxDivider - 1) / (delta + 1)));

        for (int i = maxDivider + 1; i <= maxPrime; i += delta + 1) {
            int finalI = i;
            executors.submit(() -> {
                for (int j = finalI; j <= Math.min(finalI + delta, maxPrime); j++) {

                    if (isPrime(basePrimes, j)) {
                        primeNumbers.add(j);
                    }
                }

                latch.countDown();
            });
        }

        latch.await();
        executors.shutdownNow();

        Collections.sort(primeNumbers);

        return primeNumbers;
    }

    private static boolean isPrime(List<Integer> basePrimeNumbers, Integer candidate) {
        var maxPrime = Math.sqrt(candidate);
        for (int i = 0; i < basePrimeNumbers.size(); i++) {

            if (basePrimeNumbers.get(i) > maxPrime)
                return true;
            if (candidate % basePrimeNumbers.get(i) == 0) {
                return false;
            }
        }
        return true;
    }
}
