package com.hanshesse.discovery.consul.orderservice.resource;

import com.hanshesse.discovery.consul.orderservice.discovery.ConsulServiceDiscovery;
import com.hanshesse.discovery.consul.orderservice.domain.Order;
import com.hanshesse.discovery.consul.orderservice.domain.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;

/**
 * REST resource for orders
 */
@RestController
@RequestMapping("/orders")
public class OrderResource {

    private Logger log = LoggerFactory.getLogger(OrderResource.class);

    private static final String QUOTE_SERVICE = "quote";

    @Value("${discovery.address}")
    private String discoveryAddress;

    @RequestMapping(value = "/{symbol}", method = RequestMethod.POST)
    public Order placeOrder(@PathVariable String symbol, HttpServletResponse response) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            Quote quote = restTemplate.getForObject(ConsulServiceDiscovery.getServiceAddress(discoveryAddress, QUOTE_SERVICE) + "/" + symbol, Quote.class);

            Order order = new Order(quote.getSymbol(), quote.getValue());

            log.info("Successfully placed order: " + order.toString());
            return order;
        } catch (HttpClientErrorException ex) {
            log.error("Could not process order: " + ex.getMessage());
            response.setStatus(ex.getStatusCode().value());
            return null;
        } catch (Exception ex) {
            log.error("Could not process order: " + ex.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return null;
        }
    }
}
