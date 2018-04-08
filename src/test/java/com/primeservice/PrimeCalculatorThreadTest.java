package com.primeservice;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

/**
 * Test for {@link PrimeCalculatorThread}
 */
public class PrimeCalculatorThreadTest {

    private PrimeCalculatorThread primeCalculatorThread;

    @Test
    public void isPrimeSingleThreadedBasic() throws Exception {

        //Given
        final ConcurrentHashMap<Integer, List<Long>> primeNumbersList = new ConcurrentHashMap<>();
        int threadNumber = 1;
        primeCalculatorThread = new PrimeCalculatorThread(threadNumber, 1, 10, primeNumbersList);

        //When
        primeCalculatorThread.run();

        //Then
        assertThat(primeNumbersList.size(), is(1));
        assertThat(primeNumbersList.get(threadNumber), hasItems(2L, 3L, 5L, 7L));
    }

    //region Negative Test Cases
    @Test
    public void isPrimeNegativeStartRange() throws Exception {
        //Given
        final ConcurrentHashMap<Integer, List<Long>> primeNumbersList = new ConcurrentHashMap<>();
        int threadNumber = 1;
        primeCalculatorThread = new PrimeCalculatorThread(threadNumber, -1, 10, primeNumbersList);

        //When
        try {
            primeCalculatorThread.run();
            fail("Should not execute this");
        } catch (IllegalArgumentException ex) {
            //Then
            assertThat(primeNumbersList.size(), is(0));
        } catch (Exception e) {
            fail("Should not throw this exception");
        }
    }

    @Test
    public void isPrimeNegativeEndRange() throws Exception {
        //Given
        final ConcurrentHashMap<Integer, List<Long>> primeNumbersList = new ConcurrentHashMap<>();
        int threadNumber = 1;
        primeCalculatorThread = new PrimeCalculatorThread(threadNumber, 1, -10, primeNumbersList);

        //When
        try {
            primeCalculatorThread.run();
            fail("Should not execute this");
        } catch (IllegalArgumentException ex) {
            //Then
            assertThat(primeNumbersList.size(), is(0));
        } catch (Exception e) {
            fail("Should not throw this exception");
        }
    }

    @Test
    public void isPrimeNegativeThreadNumber() throws Exception {
        //Given
        final ConcurrentHashMap<Integer, List<Long>> primeNumbersList = new ConcurrentHashMap<>();
        int threadNumber = -1;
        primeCalculatorThread = new PrimeCalculatorThread(threadNumber, 1, 10, primeNumbersList);

        //When
        try {
            primeCalculatorThread.run();
            fail("Should not execute this");
        } catch (IllegalArgumentException ex) {
            //Then
            assertThat(primeNumbersList.size(), is(0));
        } catch (Exception e) {
            fail("Should not throw this exception");
        }
    }

    @Test
    public void isPrimeNullOutputMap() throws Exception {
        //Given
        int threadNumber = 1;
        primeCalculatorThread = new PrimeCalculatorThread(threadNumber, 1, 10, null);

        //When
        try {
            primeCalculatorThread.run();
            fail("Should not execute this");
        } catch (IllegalArgumentException ex) {

        } catch (Exception e) {
            fail("Should not throw this exception");
        }
    }

    @Test
    public void isPrimeEdgeCasesRange1And1() throws Exception {
        //Given
        final ConcurrentHashMap<Integer, List<Long>> primeNumbersList = new ConcurrentHashMap<>();
        int threadNumber = 1;
        primeCalculatorThread = new PrimeCalculatorThread(threadNumber, 1, 1, primeNumbersList);

        //When
        primeCalculatorThread.run();

        //Then
        assertThat(primeNumbersList.size(), is(0));
    }

    @Test
    public void isPrimeEdgeCasesRange1And2() throws Exception {
        //Given
        final ConcurrentHashMap<Integer, List<Long>> primeNumbersList = new ConcurrentHashMap<>();
        int threadNumber = 1;
        primeCalculatorThread = new PrimeCalculatorThread(threadNumber, 1, 2, primeNumbersList);

        //When
        primeCalculatorThread.run();

        //Then
        assertThat(primeNumbersList.size(), is(1));
        assertThat(primeNumbersList.get(threadNumber), hasItem(2L));
    }

    //region end

}