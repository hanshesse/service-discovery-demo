ZKIP="$(docker inspect zk2 | sed -n 's/        "IPAddress": "\(.*\)",/\1/p')"

docker run --name zk4 containersol/zookeeper 3 $ZKIP
