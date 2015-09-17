package com.hanshesse.discovery.consul.quoteservice.discovery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Consul service registration data format
 */
public class ConsulRegistrationDto {

    private Logger log = LoggerFactory.getLogger(ConsulRegistrationDto.class);

    private String serviceName;

    private int servicePort;

    private Map<String, Object> consulRegistration;

    private Map<String, Object> checkRegistration;


    public ConsulRegistrationDto(String serviceName, int servicePort) {
        this.serviceName = serviceName;
        this.servicePort = servicePort;
        consulRegistration = new HashMap<String, Object>();
        checkRegistration = new HashMap<String, Object>();
        registerData();
    }

    private void registerData() {
        consulRegistration.clear();
        checkRegistration.clear();

        try {
            consulRegistration.put("ID", serviceName);
            consulRegistration.put("Name", serviceName);
            consulRegistration.put("Address", "172.17.42.1");
            consulRegistration.put("Port", servicePort);

            checkRegistration.put("HTTP", "http://172.17.42.1:" + String.valueOf(servicePort) + "/health");
            log.info("check: " + checkRegistration.get("HTTP"));
            checkRegistration.put("Interval", "5s");
            consulRegistration.put("Check", checkRegistration);
        } catch (Exception ex) {
            log.error("Error registering service: " + ex.getMessage());
        }

        log.info("Registration data:" + consulRegistration.toString());
    }

    public Map<String, Object> getRegistrationData() {
        return consulRegistration;
    }
}
