package com.hanshesse.discovery.eureka.orderservice.resource;

import com.hanshesse.discovery.eureka.orderservice.domain.Order;
import com.hanshesse.discovery.eureka.orderservice.domain.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import sun.net.www.http.HttpClient;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;


/**
 * REST resource for orders
 */
@RestController
@RequestMapping("/orders")
public class OrderResource {

    private Logger log = LoggerFactory.getLogger(OrderResource.class);

    @Autowired
    LoadBalancerClient loadBalancerClient;


    @RequestMapping(value = "/{symbol}", method = RequestMethod.POST)
    public Order placeOrder(@PathVariable String symbol, HttpServletResponse response) {
        try {
            ServiceInstance serviceInstance = loadBalancerClient.choose("quote-service");
            if (serviceInstance == null) {
                throw new RuntimeException("could not find quote service instance.");
            }

            URI quoteUri = URI.create(String
                    .format("http://%s:%s/quotes/" + symbol,
                            serviceInstance.getHost(), serviceInstance.getPort()));

            RestTemplate restTemplate = new RestTemplate();
            Quote quote = restTemplate.getForObject(quoteUri, Quote.class);

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
