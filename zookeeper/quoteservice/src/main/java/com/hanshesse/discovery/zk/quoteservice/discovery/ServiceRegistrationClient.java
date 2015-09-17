package com.hanshesse.discovery.zk.quoteservice.discovery;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.UriSpec;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * Provides methods for service registration
 */
public class ServiceRegistrationClient {

    private static final Logger log = LoggerFactory.getLogger(ServiceRegistrationClient.class);

    private String serviceBasePath;

    private String serviceName;

    int servicePort;

    private CuratorFramework curatorClient;

    private ServiceDiscovery<InstanceDetails> serviceDiscovery;

    private ServiceInstance<InstanceDetails> thisInstance;

    private final JsonInstanceSerializer<InstanceDetails> serializer;


    public ServiceRegistrationClient(CuratorFramework curatorClient, String serviceBasePath, String serviceName,
                                     int servicePort) throws UnknownHostException, Exception {
        this.curatorClient = curatorClient;
        this.serviceBasePath = serviceBasePath;
        this.serviceName = serviceName;
        this.servicePort = servicePort;

        serializer = new JsonInstanceSerializer<>(InstanceDetails.class);

        UriSpec uriSpec = new UriSpec("{scheme}://{address}:{port}/quotes");
        thisInstance = ServiceInstance.<InstanceDetails>builder().name(serviceName)
                .uriSpec(uriSpec)
                .address(InetAddress.getLocalHost().getHostAddress())
                .payload(new InstanceDetails())
                .port(servicePort)
                .build();

        log.info("Registering service instance on: " + thisInstance.buildUriSpec());
    }

    public void registerService() throws Exception {
        curatorClient.blockUntilConnected();
        serviceDiscovery = ServiceDiscoveryBuilder.builder(InstanceDetails.class)
                .client(curatorClient)
                .basePath(serviceBasePath).serializer(serializer).thisInstance(thisInstance)
                .build();
        serviceDiscovery.start();
    }

    public void close() throws IOException {
        log.info("De-registering Quote Service");

        try {
            serviceDiscovery.close();
        }
        catch (Exception ex) {
            log.error("Error closing ServiceRegistrationClient" + ex.getMessage());
        }
    }
}
