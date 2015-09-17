JOIN_IP="$(docker inspect -f '{{.NetworkSettings.IPAddress}}' node2)"
docker run -d --name node1 -h node1 progrium/consul -server -join $JOIN_IP -ui-dir /ui
  
