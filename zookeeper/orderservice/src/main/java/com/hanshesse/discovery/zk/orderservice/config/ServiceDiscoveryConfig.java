package com.hanshesse.discovery.zk.orderservice.config;

import com.hanshesse.discovery.zk.orderservice.discovery.ServiceDiscoveryClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

/**
 * Configuration for ZooKeeper service discovery
 */
@Configuration
public class ServiceDiscoveryConfig implements EnvironmentAware {

    private Logger log = LoggerFactory.getLogger(ServiceDiscoveryConfig.class);

    private static final String SERVICE_BASE_PATH = "services";

    private static final String SERVICE_NAME = "quote";

    private RelaxedPropertyResolver propertyResolver;

    private Environment env;


    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
        this.propertyResolver = new RelaxedPropertyResolver(env, "discovery.zookeeper.");
    }

    @Bean(destroyMethod = "close")
    public ServiceDiscoveryClient serviceDiscoveryClient() {
        ServiceDiscoveryClient serviceDiscoveryClient = new ServiceDiscoveryClient(curatorClient(),
                SERVICE_BASE_PATH, SERVICE_NAME);
        serviceDiscoveryClient.start();
        return serviceDiscoveryClient;
    }


    @Bean(initMethod = "start", destroyMethod = "close")
    @Scope("prototype")
    public CuratorFramework curatorClient() {
        log.info("Address:" + propertyResolver.getProperty("address"));

        CuratorFramework client = CuratorFrameworkFactory.newClient(propertyResolver.getProperty("address"),
                new ExponentialBackoffRetry(3000, 3));
        return client;
    }
}
