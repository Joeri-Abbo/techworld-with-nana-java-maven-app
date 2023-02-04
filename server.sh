#!/usr/bin/env bash

docker-compose -f docker-compose.yml up --detach
echo "Success"
export TEST=testvalue