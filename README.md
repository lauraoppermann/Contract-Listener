# Marketplace Server

## What is this?
This project was developed as a prototype in the scope of a master thesis with the topic "Concept and Design of an efficient search and discovery mechanism in decentralized ledger-based marketplaces". 

Goal of this prototype is to provide a solution for a search mechanism that can be used to query blockchain (especially Ethereum) data. Querying data from Ethereum is not efficiently  possible in the standard setup. This is because of the structure of a blockchain. Each block only knows about it's parents block by a hash value, as the blockchain ist a chain of blocks with unique hash identifiers. 
Searching for elements within the blockchain would therefore require knowing the exact hash of the block where the wanted information is stored in or iterating through the whole blockchain until the information is found. 
As this a very unefficient procedure and not very user friendly, this prototype provides a solution with an in-between service. 

## Architecture


## Setup & Installation
To start the service, you need to install [Docker Compose](https://docs.docker.com/compose/).
Docker compose handles the connection between the three different components, the marketplace server, the mongoDB database and the geth node. 

You need to configure the location where the volumes of the docker container should be stored. 
so change the lines

```
volumes:
      - /home/lauraoppermann/ethereum-docker:/home/lauraoppermann/.ethereum
  
```
and 
```
        "--datadir=/home/lauraoppermann/.ethereum"
```
to your prefered locations. 

To start the service with docker compose simply run 
```
docker-compose up
```
You can add the "-d" option to run the containers detached or use a screen session on linux based systems.
After the container started it might need a while until the geth node finds any peers to connect to and starts the synchronization of the blockchain. 

To enable the indexing functionality in the mongoDB database, you need to attach to the mongoDB container, execute the running database console  and create the index by running the command 
```
docker exec -it contractlistener_mongo_db_1 mongo
use blade-registry
db.bladeApp.createIndex({appName:"text"})
```

## Development

## Ethereum ropsten net merge

https://decrypt.co/102320/ethereum-merge-ropsten-live
https://blog.ethereum.org/2022/05/30/ropsten-merge-announcement/
https://blog.ethereum.org/2022/06/03/ropsten-merge-ttd/

