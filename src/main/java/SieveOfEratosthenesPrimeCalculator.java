import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class SieveOfEratosthenesPrimeCalculator {

    public List<Integer> getPrimes(int maxPrime) {

        if (maxPrime < 0)
            throw new UnsupportedOperationException();

        List<Boolean> primeNumbers = Stream.generate(() -> true)
                .limit(maxPrime + 1)
                .collect(Collectors.toCollection(() -> new ArrayList<>()));

        for (int i = 2; i * i <= maxPrime; i++) {
            if (primeNumbers.get(i)) {
                for (int j = i * i; j <= maxPrime; j = j + i) {
                    primeNumbers.set(j, false);
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 2; i < primeNumbers.size(); i++) {
            if (primeNumbers.get(i))
                result.add(i);
        }
        return result;
    }

}
