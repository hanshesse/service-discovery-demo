ZKIP="$(docker inspect zk1 | sed -n 's/        "IPAddress": "\(.*\)",/\1/p')"

java -jar quoteservice/target/quoteservice-1.0-SNAPSHOT.jar --server.port=$RANDOM --discovery.zookeeper.address=$ZKIP
