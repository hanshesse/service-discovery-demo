ZKIP="$(docker inspect zk1 | sed -n 's/        "IPAddress": "\(.*\)",/\1/p')"

docker run --name zk3 containersol/zookeeper 3 $ZKIP
