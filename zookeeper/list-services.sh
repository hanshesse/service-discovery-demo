ZKIP="$(docker inspect zk1 | sed -n 's/        "IPAddress": "\(.*\)",/\1/p')"
docker exec -it zk1 bin/zkCli.sh -server $ZKIP:2181 ls /services/quote
