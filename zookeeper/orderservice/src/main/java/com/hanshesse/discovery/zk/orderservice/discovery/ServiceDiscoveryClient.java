package com.hanshesse.discovery.zk.orderservice.discovery;

import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceProvider;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hanshesse.discovery.zk.quoteservice.discovery.InstanceDetails;

import java.io.Closeable;
import java.util.List;

/**
 * Provides methods for service discovery
 */
public class ServiceDiscoveryClient {

    private Logger log = LoggerFactory.getLogger(ServiceDiscoveryClient.class);

    private String serviceBasePath;

    private String serviceName;

    private CuratorFramework curatorClient;

    private JsonInstanceSerializer<InstanceDetails> serializer;

    private ServiceDiscovery<InstanceDetails> serviceDiscovery;

    private ServiceProvider<InstanceDetails> serviceProvider;

    private final List<Closeable> closeables = Lists.newArrayList();

    public ServiceDiscoveryClient(CuratorFramework curatorClient, String serviceBasePath, String serviceName) {
        this.curatorClient = curatorClient;
        this.serviceBasePath = serviceBasePath;
        this.serviceName = serviceName;

        serializer = new JsonInstanceSerializer<>(InstanceDetails.class);
        serviceDiscovery = ServiceDiscoveryBuilder.builder(InstanceDetails.class).client(curatorClient)
                .basePath(serviceBasePath).serializer(serializer).build();
        serviceProvider = serviceDiscovery.serviceProviderBuilder().serviceName(serviceName).build();
    }

    public void start() {
        try {
            serviceDiscovery.start();
            closeables.add(0, serviceDiscovery);
            serviceProvider.start();
            closeables.add(0, serviceProvider);
        }
        catch (Exception ex) {
            log.error("Error starting service discovery client: " + ex.getMessage());
            throw new RuntimeException("Error starting service discovery client", ex);
        }
    }

    public void close() {
        for (Closeable closeable : closeables) {
            try {
                closeable.close();
            }
            catch (Exception e) {
                log.warn("Failed to close service cleanly:" + closeable, e);
            }
        }
    }

    public ServiceInstance<InstanceDetails> getServiceInstance() {
        try {
            ServiceInstance<InstanceDetails> serviceInstance = serviceProvider.getInstance();
            if (serviceInstance == null) {
                throw new RuntimeException("Could not load a service instance");
            }

            return serviceInstance;
        } catch (Exception ex) {
            log.error("Could not load a service instance: " + ex.getMessage());
            throw new RuntimeException("Error obtaining service instance: ", ex);
        }
    }

}
