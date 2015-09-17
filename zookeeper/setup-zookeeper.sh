docker kill zk1 zk2 zk3
docker rm zk1 zk2 zk3
docker run --name zk1 -t -d containersol/zookeeper 1

ZKIP="$(docker inspect zk1 | sed -n 's/        "IPAddress": "\(.*\)",/\1/p')"
echo $ZKIP

docker run --name zk2 -d containersol/zookeeper 2 $ZKIP
sleep 3
docker run --name zk3 -d containersol/zookeeper 3 $ZKIP
