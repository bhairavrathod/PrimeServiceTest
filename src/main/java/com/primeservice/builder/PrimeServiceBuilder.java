package com.primeservice.builder;

import com.primeservice.MultiThreadedCachedPrimeService;
import com.primeservice.api.PrimeService;

/**
 * Builder class for {@link PrimeService}
 */
public class PrimeServiceBuilder {

    private boolean cacheEnabled;

    private int noOfParallelThreads = 1;

    public PrimeServiceBuilder withCacheEnabled() {
        this.cacheEnabled = true;
        return this;
    }

    public PrimeServiceBuilder withMultiThreadedEnabled(int noOfParallelThreads) {
        this.noOfParallelThreads = noOfParallelThreads;
        return this;
    }

    public PrimeService build() {
        return new MultiThreadedCachedPrimeService(cacheEnabled, noOfParallelThreads);
    }
}
