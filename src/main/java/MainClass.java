import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MainClass {

    public static void main(String[] args) throws IOException, InterruptedException {

        BufferedWriter writer = new BufferedWriter(new FileWriter("primeNumbers"));

        var maxPrime = Integer.parseInt(args[0]);

        var startTime = System.currentTimeMillis();
        var primes = new OptimizedPrimeCalculator().getPrimes(maxPrime);
        long endTime = System.currentTimeMillis();

        writer.write("Total execution time taken to identify all the prime numbers less than or equal to " + maxPrime + ": " +
                (endTime - startTime) + " ms \n");

        for (Integer prime : primes) {
            writer.write(prime + "\n");
        }

        writer.close();
    }
}
