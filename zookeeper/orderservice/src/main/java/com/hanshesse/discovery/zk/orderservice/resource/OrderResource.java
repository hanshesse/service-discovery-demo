package com.hanshesse.discovery.zk.orderservice.resource;

import com.hanshesse.discovery.zk.orderservice.discovery.ServiceDiscoveryClient;
import com.hanshesse.discovery.zk.orderservice.domain.Order;
import com.hanshesse.discovery.zk.orderservice.domain.Quote;
import com.hanshesse.discovery.zk.quoteservice.discovery.InstanceDetails;
import org.apache.curator.x.discovery.ServiceInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    ServiceDiscoveryClient serviceDiscoveryClient;


    @RequestMapping(value = "/{symbol}", method = RequestMethod.POST)
    public Order placeOrder(@PathVariable String symbol, HttpServletResponse response) {
        try {
            ServiceInstance<InstanceDetails> serviceInstance = serviceDiscoveryClient.getServiceInstance();

            RestTemplate restTemplate = new RestTemplate();
            Quote quote = restTemplate.getForObject(serviceInstance.buildUriSpec() + "/" + symbol, Quote.class);

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
