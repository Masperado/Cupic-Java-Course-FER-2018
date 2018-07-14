package hr.fer.zemris.java.hw06.demo2;

import java.util.Iterator;

/**
 * This class represents primes collection. It is only used for generating prime numbers in for-each loop. It
 * generates as many numbers as given in constructor.
 */
public class PrimesCollection implements Iterable<Integer> {

    /**
     * Number of primes that will be generated.
     */
    private int noOfPrimes;

    /**
     * Basic constructor.
     *
     * @param noOfPrimes Number of primes that will be generated
     */
    public PrimesCollection(int noOfPrimes) {
        this.noOfPrimes = noOfPrimes;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new primesIterator(noOfPrimes);
    }

    /**
     * This class represented iterator implementation for prime numbers.
     */
    private class primesIterator implements Iterator<Integer> {

        /**
         * Maximum number of primes.
         */
        private int maxNoOfPrimes;

        /**
         * Number of primes generated.
         */
        private int currNoOfPrimes;

        /**
         * Last generated prime.
         */
        private int lastGeneratedPrime;

        /**
         * Basic constructor.
         *
         * @param maxNoOfPrimes Number of primes generated
         */
        public primesIterator(int maxNoOfPrimes) {
            this.maxNoOfPrimes = maxNoOfPrimes;
            this.lastGeneratedPrime = 1;
        }

        @Override
        public boolean hasNext() {
            return currNoOfPrimes < maxNoOfPrimes;
        }

        @Override
        public Integer next() {
            for (int i = lastGeneratedPrime + 1; ; i++) {
                int noOfDivisors = 0;
                for (int j = 1; j < i; j++) {
                    if (i % j == 0) {
                        noOfDivisors++;
                    }
                }

                if (noOfDivisors == 1) {
                    currNoOfPrimes++;
                    lastGeneratedPrime = i;
                    return lastGeneratedPrime;
                }
            }

        }
    }
}
