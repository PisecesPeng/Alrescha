#!/bin/bash
set -e

# build Dockerfile
docker build -t pp-mysql .

# docker run
docker run -d -e MYSQL_ROOT_PASSWORD=q1w2e3r4 -p 3306:3306 --restart=always --name pp-mysql pp-mysql

