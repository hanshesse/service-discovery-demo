ZKIP="$(docker inspect zk1 | sed -n 's/        "IPAddress": "\(.*\)",/\1/p')"

java -jar orderservice/target/orderservice-1.0-SNAPSHOT.jar --server.port=8181 --discovery.zookeeper.address=$ZKIP
