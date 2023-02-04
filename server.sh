#!/usr/bin/env bash

export IMAGE=$1
docker-compose -f docker-compose.yml up --detach
echo "Success"
