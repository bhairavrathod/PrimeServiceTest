package com.primeservice.domainmodel;

import java.util.List;

/**
 * Domain model for the Prime number results.
 */
public class PrimeResult {
    private final long initialVal;

    private final List<Long> primies;

    public PrimeResult(long initialVal, List<Long> primies) {
        this.initialVal = initialVal;
        this.primies = primies;
    }

    public List<Long> getPrimies() {
        return primies;
    }

    public long getInitialVal() {
        return initialVal;
    }


}
