package com.primeservice.util;

/**
 * A utility class which helps calculate the Prime number.
 */
public class PrimeNumberUtil {

    /**
     * Checks if the number is prime. If a number num is not prime one of the factors has to be
     * in the range 2 to square-root(num).
     *
     * @param num - primeCandidate.
     * @return boolean - if the number is prime.
     */
    public static boolean isPrime(long num) {
        // 1 is not a prime number
        if (num < 2L) {
            return false;
        }
        // 2 is a prime number
        if (num == 2L) {
            return true;
        }
        long sqrt = getFlooredSquareRoot(num);
        for (long i = 2; i <= sqrt; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Utility method to get the floored square root of a long.
     * @param num - input number
     * @return long - floor of square root of a number.
     */
    public static long getFlooredSquareRoot(long num) {
        return (long) Math.floor(Math.sqrt(num));
    }
}