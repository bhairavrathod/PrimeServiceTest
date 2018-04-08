package com.primeservice.config;

import com.primeservice.api.PrimeService;
import com.primeservice.builder.PrimeServiceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Prime Service App.
 */
@Configuration
public class PrimeServiceConfiguration {

    /**
     * Configure whether the prime service calculation uses caching or not.
     */
    private static final boolean IS_CACHE_ENABLED = true;

    /**
     * Defines the number of parallel threads to execute.
     */
    private static final int NO_OF_PARALLEL_THREADS = 10;

    @Bean
    public PrimeService primeService() {
        final PrimeServiceBuilder primeServiceBuilder = new PrimeServiceBuilder();
        if (IS_CACHE_ENABLED) {
            primeServiceBuilder.withCacheEnabled();
        }
        primeServiceBuilder.withMultiThreadedEnabled(NO_OF_PARALLEL_THREADS);
        return primeServiceBuilder.build();
    }
}
