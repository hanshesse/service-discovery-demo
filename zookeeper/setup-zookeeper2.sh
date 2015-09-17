ZKIP="$(docker inspect zk1 | sed -n 's/        "IPAddress": "\(.*\)",/\1/p')"

docker run --name zk2 containersol/zookeeper 2 $ZKIP
