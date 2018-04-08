package com.primeservice.api;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interface which gets all the PrimeNumbers upto a maximum value.
 * The max value is currently restricted to a long range.
 */
@Service
public interface PrimeService {

    List<Long> getPrimeNumbers(long maxValue);
}