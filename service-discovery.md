# Overall

http://jasonwilder.com/blog/2014/02/04/service-discovery-in-the-cloud/
http://www.simplicityitself.com/learning/getting-started-microservices/service-discovery-overview/
http://www.infoq.com/articles/rest-discovery-dns
http://nerds.airbnb.com/smartstack-service-discovery-cloud/
https://labs.spotify.com/2013/02/25/in-praise-of-boring-technology/
http://whilefalse.blogspot.co.za/2012/12/building-global-highly-available.html

# Zookeeper

http://tomaszdziurko.pl/2014/07/zookeeper-curator-and-microservices-load-balancing/
http://blog.arungupta.me/zookeeper-microservice-registration-discovery/
https://tech.knewton.com/blog/2014/12/eureka-shouldnt-use-zookeeper-service-discovery/
http://curator.apache.org/curator-x-discovery/index.html
http://techblog.netflix.com/2012/04/introducing-exhibitor-supervisor-system.html
http://sleeplessinslc.blogspot.co.za/2014/09/dynamic-service-discovery-with-apache.html

- Zookeeper is CP: consistent with partitions, not available
- Not actually the most important thing for service discovery -- often better to have AP
- Curator a useful layer on top of Zookeeper
- Once the ZooKeeper instances are started it's not possible to reconfigure the ensemble without updating the configuration file and restarting the instances.
- watch memory usage
- GC can cause disconnect on eph nodes

# etcd

https://github.com/coreos/etcd
http://www.ietf.org/rfc/rfc2052.txt (DNS SRV)
http://the.randomengineer.com/2013/11/28/using-etcd-as-a-highly-available-and-innovative-key-value-storage/
https://www.digitalocean.com/community/tutorial_series/getting-started-with-coreos-2
http://jasonwilder.com/blog/2014/07/15/docker-service-discovery/
http://miek.nl/posts/2014/Jun/08/announcing%20SkyDNS%20version%202/
http://stackoverflow.com/questions/30961156/where-should-i-keep-service-files-for-a-coreos-cluster-and-how-should-i-load-th

- Raft consensus model, written in Go
- linux-y
- Production ready?
- procfile for local cluster
- good documentation
- cluster discovery service: during bootstrap
- fallback proxies
- security model: SSL/TLS, auth through client certs
- tuning: log storage, timeouts for latency
- prefer a smaller cluster, as broadcast to majority before commit
- CoreOS, uses Docker containers for applications
- HTTP API - trailing slash required


# Eureka

https://tech.knewton.com/blog/2014/12/eureka-shouldnt-use-zookeeper-service-discovery/
https://github.com/Netflix/eureka/wiki/Configuring-Eureka-in-AWS-Cloud
http://cloud.spring.io/spring-cloud-netflix/spring-cloud-netflix.html
http://todaysoftmag.com/article/1429/micro-service-discovery-using-netflix-eureka

- Eureka is AP
- heartbeats
- self-preservation mode
- client-side caching
- Especially useful for mid-tier load balancing, which AWS currently does not have
- useful for non-sticky sessions
- one cluster per region, heartbeat every 30s, taken out after 90s no heartbeat
- AWS elastic IP
- Java library and REST API for non-Java users
- 2.0: subset registries, push, read/write clusters, audits, dashboard (RC2)
- Ribbon: client-side load balancing and failover
- Sidecar HTTP API for other languages

# Consul

http://progrium.com/blog/2014/07/29/understanding-modern-service-discovery-with-docker/
http://progrium.com/blog/2014/08/20/consul-service-discovery-with-docker/
https://hub.docker.com/r/progrium/consul/
https://www.digitalocean.com/community/tutorial_series/getting-started-with-consul
https://www.digitalocean.com/community/tutorials/an-introduction-to-using-consul-a-service-discovery-system-on-ubuntu-14-04
http://txt.fliglio.com/2015/07/12-factor-infrastructure-with-consul-and-vault/
http://txt.fliglio.com/2014/10/spring-boot-actuator/
http://www.mammatustech.com/consul-service-discovery-and-health-for-microservices-architecture-tutorial
https://medium.com/@ladislavGazo/easy-routing-and-service-discovery-with-docker-consul-and-nginx-acfd48e1a291
https://www.airpair.com/scalable-architecture-with-docker-consul-and-nginx
http://blog.xebia.com/2015/04/23/how-to-deploy-high-available-persistent-docker-services-using-coreos-and-consul/

- Raft, Serf, Gossip
- advantage over etcd: built-in support for services, monitoring and DNS
- health checks
- multi data center
- consul-template: e.g. rewrite nginx.conf
- linking to vault secure backend


# Jepsen Testing
https://aphyr.com/posts/281-call-me-maybe-carly-rae-jepsen-and-the-perils-of-network-partitions
