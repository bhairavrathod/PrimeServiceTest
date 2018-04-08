package com.primeservice;

import com.primeservice.exception.InvalidInputException;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Test for {@link MultiThreadedCachedPrimeService}
 */
public class PrimeServiceTest {

    private MultiThreadedCachedPrimeService primeService;

    //region basic test case
    @Test
    public void getPrimeNumbersBasic() throws Exception {
        //Given
        primeService = new MultiThreadedCachedPrimeService(false, 1);

        //When
        final List<Long> primeNumbersList = primeService.getPrimeNumbers(10);

        //Then
        assertThat(primeNumbersList.size(), is(4));
        assertThat(primeNumbersList, hasItems(2L, 3L, 5L, 7L));

    }

    @Test
    public void getPrimeNumbersNegative() throws Exception {
        //Given
        primeService = new MultiThreadedCachedPrimeService(false, 1);

        //When
        try {
            primeService.getPrimeNumbers(-10);
            fail("Should not execute this");
        } catch (InvalidInputException ex) {

        } catch (Exception e) {
            fail("Should not throw this exception");
        }

    }

    @Test
    public void getPrimeNumbersEdgeCaseWithMaxValOne() throws Exception {
        //Given
        primeService = new MultiThreadedCachedPrimeService(false, 1);

        //When
        final List<Long> primeNumbersList = primeService.getPrimeNumbers(1);

        //Then
        assertThat(primeNumbersList.size(), is(0));

    }

    @Test
    public void getPrimeNumbersEdgeCaseWithMaxValTwo() throws Exception {
        //Given
        primeService = new MultiThreadedCachedPrimeService(false, 1);

        //When
        final List<Long> primeNumbersList = primeService.getPrimeNumbers(2);

        //Then
        assertThat(primeNumbersList.size(), is(1));
        assertThat(primeNumbersList, hasItems(2L));

    }
    //endregion

    //region multi threaded
    @Test
    public void getPrimeNumbers100Threads1Million() throws Exception {
        //Given
        primeService = new MultiThreadedCachedPrimeService(false,100);

        //When
        final List<Long> primeNumbersList = primeService.getPrimeNumbers(1000000);

        //Then
        assertThat(primeNumbersList.size(), is(78498));

    }

    @Test
    public void getPrimeNumbers1000Threads10Million() throws Exception {
        //Given
        primeService = new MultiThreadedCachedPrimeService(false,1000);

        //When
        final List<Long> primeNumbersList = primeService.getPrimeNumbers(10000000);

        //Then
        assertThat(primeNumbersList.size(), is(664579));
    }
    //endregion

    //region cached test
    @Test
    public void getPrimeNumbersMultipleCachedRequests() throws Exception {
        //Given
        primeService = new MultiThreadedCachedPrimeService(true,1);

        //When
        final List<Long> primeNumbersList1000 = primeService.getPrimeNumbers(1000);
        final List<Long> primeNumbersList2000 = primeService.getPrimeNumbers(2000);
        final List<Long> primeNumbersList1000_2 = primeService.getPrimeNumbers(1000);
        final List<Long> primeNumbersList3000 = primeService.getPrimeNumbers(3000);

        //Then
        assertThat(primeNumbersList1000.size(), is(168));
        assertThat(primeNumbersList2000.size(), is(303));
        assertThat(primeNumbersList1000_2.size(), is(168));
        assertThat(primeNumbersList3000.size(), is(430));

    }
    //endregion
}