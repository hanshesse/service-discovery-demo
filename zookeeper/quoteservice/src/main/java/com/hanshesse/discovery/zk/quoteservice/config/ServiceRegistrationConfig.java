package com.hanshesse.discovery.zk.quoteservice.config;

import com.hanshesse.discovery.zk.quoteservice.discovery.ServiceRegistrationClient;
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

import java.net.UnknownHostException;

/**
 * Configuration for service registration
 */
@Configuration
public class ServiceRegistrationConfig implements EnvironmentAware {

    private Logger log = LoggerFactory.getLogger(ServiceRegistrationConfig.class);

    private static final String SERVICE_BASE_PATH = "services";

    private static final String SERVICE_NAME = "quote";

    private RelaxedPropertyResolver discoveryPropertyResolver;

    private RelaxedPropertyResolver serverPropertyResolver;

    private Environment env;

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
        this.discoveryPropertyResolver = new RelaxedPropertyResolver(env, "discovery.zookeeper.");
        this.serverPropertyResolver = new RelaxedPropertyResolver(env, "server.");
    }

    @Bean(destroyMethod = "close")
    public ServiceRegistrationClient serviceRegistrationClient() throws UnknownHostException, Exception {
        return new ServiceRegistrationClient(curatorClient(), SERVICE_BASE_PATH, SERVICE_NAME,
                Integer.parseInt(serverPropertyResolver.getProperty("port")));
    }

    @Bean(initMethod = "start", destroyMethod = "close")
    @Scope("prototype")
    public CuratorFramework curatorClient() {
        log.info("Address:" + discoveryPropertyResolver.getProperty("address"));

        return CuratorFrameworkFactory.newClient(discoveryPropertyResolver.getProperty("address"), new ExponentialBackoffRetry(3000, 3));
    }

}
