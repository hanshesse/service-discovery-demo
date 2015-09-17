package com.hanshesse.discovery.consul.orderservice.discovery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Use Consul to resolve a service
 */
public class ConsulServiceDiscovery {

    private static final Logger log = LoggerFactory.getLogger(ConsulServiceDiscovery.class);

    public static String getServiceAddress(String discoveryAddress, String serviceName) {
        log.info("Resolving address for service: " + serviceName);

        RestTemplate restTemplate = new RestTemplate();
        Object[] nodesRaw = restTemplate.getForObject("http://" + discoveryAddress + ":8500/v1/catalog/service/" + serviceName, Object[].class);
        List<Object> nodes = Arrays.asList(nodesRaw);
        if (nodes.size() == 0) {
            throw new RuntimeException("Could not discover quote service.");
        }
        Map<String, Object> node = (Map<String, Object>) nodes.get(0);

        return "http://" + node.get("ServiceAddress") + ":" + node.get("ServicePort") + "/" + serviceName + "s";
    }

}
