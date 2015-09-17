# Service Discovery Materials

This repo includes docs and code examples that were compiled as part of research and a presentation for <a href="http://www.spin.org.za/">Cape Town Spin</a> on Service Discovery.

I've included the presentation materials and some notes I took that might be valuable for the links.  Then there are examples for the four service discovery tools discussed in the presentation: ZooKeeper, Eureka, Consul and etcd.  In the examples I use two quick demo services: quote and order.  Quote is a simple spring boot app that responds with a stock quote on /quotes/{symbol} (quotes predefined, you can use e.g. AAPL) and registers itself with a service discovery tool.  Order uses a service discovery tool to find the quote service and generates a simple response on /orders/{symbol}.  Quick and dirty stuff but might help get things moving.

## ZooKeeper

* Uses the containersol/zookeer docker image for ZooKeeper.  See <a href="http://container-solutions.com/dynamic-zookeeper-cluster-with-docker/">here</a> for more info.
* There are scripts to reset, start the whole cluster, individual cluster members, and the services.  You can start cluster, start quote, then start order.

## Eureka

* A simple Spring Boot app that implements Eureka with Spring Cloud.
* Configured to to run a three member cluster with hosts eureka1, eurek1 and eureka3.  You'll need to add eureka{1,2,3} pointing to localhost in your hosts file.  Similar with quote{1,2,3}.
* You can start a server by running ./server N, N in {1,2,3}
* Similar with quote, add quote{1,2,3} to localhost in your hosts file and run ./quote N to start.  Order service starts with ./order

## Consul

* Using progrium/consul docker image, see <a href="https://hub.docker.com/r/progrium/consul/">here</a> for details on running.  Some scripts in there to get things going.

## etcd

* Using vagrant to get a cluster going, as per <a href="https://coreos.com/blog/coreos-clustering-with-vagrant/">this page</a>.
* vagrant up in the directory, and then you can vagrant ssh into any box.
* Digital ocean has a <a href="https://www.digitalocean.com/community/tutorials/how-to-use-confd-and-etcd-to-dynamically-reconfigure-services-in-coreos">great tutorial</a> to get things moving.
