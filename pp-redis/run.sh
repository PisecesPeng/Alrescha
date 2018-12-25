#!/bin/bash
set -e

# build Dockerfile
docker build -t pp-redis .

# docker run
docker run -d -p 6379:6379 --restart=always --name pp-redis pp-redis redis-server --requirepass "q1w2e3r4"
