package com.primeservice;


import com.primeservice.util.PrimeNumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Prime calculator thread checks if a range number are prime or not.
 * At the endRange of processing, thread populates the prime numbers in the outputMap.
 */
public class PrimeCalculatorThread extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultiThreadedCachedPrimeService.class);

    /**
     * Map to hold values of individual threads by thread numbers.
     */
    private final ConcurrentHashMap outputMap;

    /**
     * Number assigned to individual thread.
     */
    private final int threadNumber;

    /**
     * Start of the Range for individual thread to work on.
     */
    private final long startRange;

    /**
     * End of the Range for individual thread to work on.
     */
    private final long endRange;

    public PrimeCalculatorThread(final int threadNumber, final long startRange, final long endRange,
                                 final ConcurrentHashMap<Integer, List<Long>> outputMap) {
        this.threadNumber = threadNumber;
        this.startRange = startRange;
        this.endRange = endRange;
        this.outputMap = outputMap;
        LOGGER.info("Created thread No: " + threadNumber + ".");
    }

    /**
     * Run method of the Thread which finds the List of Prime numbers in the range assigned and updates the
     * output map.
     */
    public void run() {
        //Validate input data.
        validateInputData();

        //Use local arraylist to contain the result of PrimeNumbers within a range.
        final ArrayList<Long> primeList = new ArrayList<>();

        //Loop through the start and end range and if a number is prime, then add it to the output map.
        for (long primeCandidate = startRange; primeCandidate <= endRange; primeCandidate++) {
            if (PrimeNumberUtil.isPrime(primeCandidate)) {
                primeList.add(primeCandidate);
            }
        }

        //Add the Result back in the outputMap.
        if (!primeList.isEmpty()) {
            outputMap.put(threadNumber, primeList);
        }
    }

    /**
     * Validates the start and end range to be positive numbers.
     * Validates outputMap is not null.
     * Validates threadNumber is not negative.
     */
    private void validateInputData() {
        if (!(startRange > 0 && endRange > 0 && endRange >= startRange && null != outputMap && threadNumber >= 0)) {
            throw new IllegalArgumentException("Invalid inputs for thread: " + threadNumber + " startRange: "
                    + startRange + " endRange: " + endRange);
        }
    }
}