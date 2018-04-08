package com.primeservice.app;

import com.primeservice.config.PrimeServiceConfiguration;
import com.primeservice.api.PrimeService;
import com.primeservice.domainmodel.PrimeResult;
import com.primeservice.exception.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableAutoConfiguration
@Import({PrimeServiceConfiguration.class})
public class PrimeServiceController {

    @Autowired
    public PrimeService primeService;

    @RequestMapping(value = "/primes/{maxNumber}", method = RequestMethod.GET)
    @ResponseBody
    public PrimeResult getPrimeNumbers(@PathVariable("maxNumber") long maxNumber) {
        final List<Long> primeNumbers = primeService.getPrimeNumbers(maxNumber);
        return new PrimeResult(maxNumber, primeNumbers);

    }

}