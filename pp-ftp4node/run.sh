#!/bin/bash
set -e

#username=`whoami`
if [ ! -d "~/Data/ftp4node" ]; then
    mkdir -p ~/Data/ftp4node
fi

# node
tar zxvf ./node-v8.11.3-linux-x64.tar.gz
tar zxvf ./node_src.tar.gz
cd ./node_src/
npm install

# build Dockerfile
cd ./../
docker build -t pp-ftp4node .

# docker run
docker run -d -v ~/Data/ftp4node/:/home/ftp4node/ -p 21:21 -p 3000:3000 -p 30000-30009:30000-30009 --restart=always --name pp-ftp4node pp-ftp4node

