package com.hanshesse.discovery.consul.quoteservice.resource;

import com.hanshesse.discovery.consul.quoteservice.discovery.ConsulRegistrationDto;
import com.hanshesse.discovery.consul.quoteservice.domain.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Basic service to provide a stock quote
 */
@RestController
@RequestMapping("/quotes")
public class QuoteResource {

    private Logger log = LoggerFactory.getLogger(QuoteResource.class);


    private static final HashMap<String, Quote> quotes = new HashMap<String, Quote>();

    static {
        quotes.put("AAPL", new Quote("AAPL", "114.21"));
        quotes.put("AMZN", new Quote("AMZN", "529.44"));
        quotes.put("GOOG", new Quote("GOOG", "625.57"));
        quotes.put("MSFT", new Quote("MSFT", "43.48"));
    }

    @RequestMapping(value = "/{symbol}", method= RequestMethod.GET)
    public Quote getQuote(@PathVariable String symbol, HttpServletResponse response) {
        log.info("REST request for quote: " + symbol);

        if (quotes.containsKey(symbol)) {
            return quotes.get(symbol);
        } else {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }

    @RequestMapping(method=RequestMethod.GET)
    public Map<String, Object> test() {
        Map<String, Object> reg = new HashMap<String, Object>();

        reg.put("Datacenter", "dc1");

        Map<String, String> check = new HashMap<String, String>();
        check.put("Node", "test");
        reg.put("Check", check);

        return reg;
    }
}
