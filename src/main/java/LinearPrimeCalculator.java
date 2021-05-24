import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LinearPrimeCalculator {

    public List<Integer> getPrimes(int maxPrime) {

        if (maxPrime < 0)
            throw new UnsupportedOperationException();

        List<Integer> leastPrimeNumbers = Stream.generate(() -> 0)
                .limit(maxPrime + 1)
                .collect(Collectors.toCollection(ArrayList::new));
        List<Integer> primeNumbers = new ArrayList<>();

        for (int i = 2; i <= maxPrime; i++) {
            if (leastPrimeNumbers.get(i) == 0) {
                leastPrimeNumbers.set(i, i);
                primeNumbers.add(i);
            }
            for (Integer number : primeNumbers) {
                if (number <= leastPrimeNumbers.get(i) && number * i <= maxPrime) {
                    leastPrimeNumbers.set(number * i, number);
                } else
                    break;
            }
        }
        return primeNumbers;
    }
}
