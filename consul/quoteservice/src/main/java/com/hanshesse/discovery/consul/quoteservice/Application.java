package com.hanshesse.discovery.consul.quoteservice;

import com.hanshesse.discovery.consul.quoteservice.discovery.ConsulRegistrationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * Application entry point
 */
@SpringBootApplication
public class Application {

    private Logger log = LoggerFactory.getLogger(Application.class);

    private static final String SERVICE_NAME = "quote";

    @Value("${discovery.registrationAddress}")
    private String registrationAddress;

    @Value("${server.port}")
    private int servicePort;

    @PostConstruct
    public void registerService() {
        log.info("Registering quote service");

        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.put("http://" + registrationAddress + ":8500/v1/agent/service/register", new ConsulRegistrationDto(SERVICE_NAME, servicePort).getRegistrationData());
        } catch (Exception ex) {
            log.error("Could not register service: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
