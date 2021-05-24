import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PrimeCalculatorTests {

    private static Stream<Arguments> provideParameters() throws IOException {
        return Stream.of(
                Arguments.of(0, new ArrayList<Integer>()),
                Arguments.of(1, new ArrayList<Integer>()),
                Arguments.of(2, Arrays.asList(2)),
                Arguments.of(3, Arrays.asList(2, 3)),
                Arguments.of(4, Arrays.asList(2, 3)),
                Arguments.of(7, Arrays.asList(2, 3, 5, 7)),
                Arguments.of(10, Arrays.asList(2, 3, 5, 7)),
                Arguments.of(100, Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97)),
                Arguments.of(10000000, readDataFromTestFile("PrimeNumbersTill10000000"))
        );
    }

    private static List<Integer> readDataFromTestFile(String fileName) throws IOException {
        var stream = PrimeCalculatorTests.class.getClassLoader().getResourceAsStream(fileName);
        var str = new String(stream.readAllBytes());
        stream.close();
        return Arrays.stream(str.split(",")).map(x->Integer.parseInt(x)).collect(Collectors.toList());
    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    void LinearPrimeCalculator_WorksOnSmallSequences(Integer number, List<Integer> expected) {
        var primeNumbers = new LinearPrimeCalculator().getPrimes(number);
        Assertions.assertArrayEquals(expected.toArray(), primeNumbers.toArray());
    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    void SieveOfEratosthenesPrimeCalculator_WorksOnSmallSequences(Integer number, List<Integer> expected) {
        var primeNumbers = new SieveOfEratosthenesPrimeCalculator().getPrimes(number);
        Assertions.assertArrayEquals(expected.toArray(), primeNumbers.toArray());
    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    void OptimizedPrimeCalculator_WorksOnSmallSequences(Integer number, List<Integer> expected) throws InterruptedException {
        var primeNumbers = new OptimizedPrimeCalculator().getPrimes(number);
        Assertions.assertArrayEquals(expected.toArray(), primeNumbers.toArray());
    }

}
