package com.hanshesse.discovery.zk.quoteservice;

import com.hanshesse.discovery.zk.quoteservice.discovery.ServiceRegistrationClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Application entry point
 */
@SpringBootApplication
public class Application {

    private Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    ServiceRegistrationClient serviceRegistrationClient;

    private static ApplicationContext applicationContext;

    @PostConstruct
    public void registerService() {
        log.info("Registering quote service");

        try {
            serviceRegistrationClient.registerService();
        } catch (Exception ex) {
            log.error("Could not register service: " + ex.getMessage());
        }
    }

    @PreDestroy
    public void deregisterService() {
        log.info("Deregistering quote service");


    }

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(Application.class, args);
    }

}
