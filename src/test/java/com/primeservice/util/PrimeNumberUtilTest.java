package com.primeservice.util;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test for {@link PrimeNumberUtil}
 */
public class PrimeNumberUtilTest {

    @Test
    public void isPrimePositive() throws Exception {
        assertThat(PrimeNumberUtil.isPrime(2), is(true));
        assertThat(PrimeNumberUtil.isPrime(3), is(true));
        assertThat(PrimeNumberUtil.isPrime(5333), is(true));
    }

    @Test
    public void isPrimeNegative() throws Exception {
        assertThat(PrimeNumberUtil.isPrime(1), is(false));
        assertThat(PrimeNumberUtil.isPrime(32), is(false));
        assertThat(PrimeNumberUtil.isPrime(53335), is(false));
        assertThat(PrimeNumberUtil.isPrime(-21L), is(false));
    }


    @Test
    public void getFlooredSquareRoot() throws Exception {
        assertThat(PrimeNumberUtil.getFlooredSquareRoot(2), is(1L));
        assertThat(PrimeNumberUtil.getFlooredSquareRoot(356543), is(597L));
        assertThat(PrimeNumberUtil.getFlooredSquareRoot(-87), is(0L));
    }

}