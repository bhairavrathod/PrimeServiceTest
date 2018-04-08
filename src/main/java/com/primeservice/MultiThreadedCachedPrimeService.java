package com.primeservice;

import com.primeservice.api.PrimeService;
import com.primeservice.exception.InvalidInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;


/**
 * A cached and multi-threaded PrimeService implementation which uses threads to calculate a range of the Prime numbers
 * from 1 to the given MaxValue.
 * The threads divide the range and works on part of the range.
 */
@Service
public class MultiThreadedCachedPrimeService implements PrimeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultiThreadedCachedPrimeService.class);

    /**
     * Default min value for the Service
     */
    private long DEFAULT_MIN = 2L;

    /**
     * Determines whether .
     */
    private boolean cacheEnabled;

    /**
     * A threadPool of threads for processing the results.
     */
    private ArrayList<PrimeCalculatorThread> threadPool;

    /**
     * The threadStartValues time when the processing began. This is used for benchmarking.
     */
    private long benchmarkStartTime;

    /**
     * The threadEndValues time when the processing ended. This is used for benchmarking.
     */
    private long benchmarkEndTime;

    /**
     * This is the number of threads to be created.
     */
    private int numberOfThreads = 10;

    /**
     * This is the cache for previously resolved PrimeNumbers
     */
    private final SortedMap<Long, List<Long>> cachedPrimeNumbers = new TreeMap<>();

    /**
     * This is the container of list of output primeNumbers per thread.
     */
    private ConcurrentHashMap<Integer, List<Long>> primeNumbersList;

    /**
     * This is the array used by the multiple threads to identify which
     * long to begin with.
     */
    private long[] threadStartValues;

    /**
     * This is the array used by the multiple threads to identify which
     * long to threadEndValues at.
     */
    private long[] threadEndValues;

    public MultiThreadedCachedPrimeService(boolean cacheEnabled, int numberOfThreads) {
        this.cacheEnabled = cacheEnabled;
        this.numberOfThreads = numberOfThreads;
    }


    /**
     * Gets all the PrimeNumbers upto a maximum value.
     * The max value is currently restricted to a long range.
     *
     * @param maxValue
     * @return
     */
    @Override
    public List<Long> getPrimeNumbers(long maxValue) {

        validateInput(maxValue);

        //Reset Data Structures
        final ArrayList<Long> consolidatedPrimeResult = new ArrayList<>();

        primeNumbersList = new ConcurrentHashMap<>(numberOfThreads);

        //check for values in cache and return a new minValue
        final long minValue = getCachedValues(maxValue, consolidatedPrimeResult);

        //Edge case where maxVal is 2L
        if (maxValue == 2L) {
            consolidatedPrimeResult.add(2L);
        }

        //If there is a subset of applicable values from cache or no data found in cache, then calculate the prime
        //numbers for the new range.
        if (minValue < maxValue) {
            try {
                int numThreads = numberOfThreads;
                LOGGER.info("Calculating Prime numbers for Min Val: " + minValue + " Max Val: " + maxValue);
                initializeStarEndForThreads(minValue, maxValue - 1, numThreads);
                executeThreads(numThreads);
                LOGGER.info("Execution Time: " + (benchmarkEndTime - benchmarkStartTime) + " ms");
            } catch (Exception e) {
                LOGGER.error("Exception occurred when waiting for Threads to complete " + e.getMessage());
            }

            //Consolidate the results
            primeNumbersList.values().forEach(consolidatedPrimeResult::addAll);
        }

        //Update the cache
        updateCache(maxValue, consolidatedPrimeResult);

        return consolidatedPrimeResult;
    }

    /**
     * If Cache is enabled, then it gets the value of largest cached key smaller than or equal to the maxValue.
     *
     * @param maxValue
     * @param consolidatedPrimeResult
     * @return
     */
    private long getCachedValues(long maxValue, ArrayList<Long> consolidatedPrimeResult) {
        if (!cacheEnabled) {
            return DEFAULT_MIN;
        }
        //Returns a submap with keys <= maxvalue
        final SortedMap<Long, List<Long>> validCacheMap = cachedPrimeNumbers.headMap(maxValue + 1);

        long lastKey = DEFAULT_MIN;

        if (!validCacheMap.isEmpty()) {
            lastKey = validCacheMap.lastKey();
            LOGGER.info("Cache hit for Current Max: " + maxValue + " is Value: " + lastKey);
            consolidatedPrimeResult.addAll(validCacheMap.get(lastKey));
        }
        return lastKey;
    }

    /**
     * Updates the cache with the latest value obtained from the calculation.
     *
     * @param maxValue
     * @param consolidatedPrimeResult
     */
    private void updateCache(long maxValue, ArrayList<Long> consolidatedPrimeResult) {
        if (cacheEnabled) {
            cachedPrimeNumbers.putIfAbsent(maxValue, consolidatedPrimeResult);
        }
    }

    /**
     * @param maxValue
     */
    private void validateInput(long maxValue) {
        if (!(maxValue > 0)) {
            throw new InvalidInputException(maxValue);
        }
    }

    /**
     * Initializes the multiple threads and their division of work loads.
     *
     * @param maxValue
     * @param numThreads
     */
    public void initializeStarEndForThreads(long minValue, long maxValue, int numThreads) {
        threadStartValues = new long[numberOfThreads];
        threadEndValues = new long[numberOfThreads];

        /**
         * The size of a work load
         */
        long size = (maxValue - minValue) / (long) numberOfThreads;

        //If size load is same or less than number of threads.
        if (size <= 1L) {
            numThreads = 1;
            return;
        }

        // initialize threadStartValues-threadEndValues
        threadStartValues[0] = minValue;

        for (int i = 0; i < numberOfThreads - 1; i++) {
            threadStartValues[i + 1] = threadStartValues[i] + size;
            threadEndValues[i] = threadStartValues[i + 1] - 1;
        }
        threadEndValues[numberOfThreads - 1] = maxValue;
    }

    public void executeThreads(int numThreads) {
        threadPool = new ArrayList<>();

        // initialize threads if threadEndValues is valid
        for (int i = 0; i < numThreads; i++) {
            if (threadEndValues[i] > 1L) {
                // distribute work load
                final PrimeCalculatorThread thread = new PrimeCalculatorThread(i + 1, threadStartValues[i],
                        threadEndValues[i], primeNumbersList);
                // add to threadPool
                threadPool.add(thread);
            }
        }

        // threadStartValues benchmark
        benchmarkStartTime = System.currentTimeMillis();

        // run the threads
        for (PrimeCalculatorThread thread : threadPool) {
            thread.start();
        }

        // Wait for all the threads to complete
        for (PrimeCalculatorThread thread : threadPool) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                LOGGER.error("Exception occurred when waiting for Threads to complete " + e.getMessage());
            }
        }
        benchmarkEndTime = System.currentTimeMillis();
        // threadEndValues benchmark
    }

}
