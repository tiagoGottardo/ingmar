#!/bin/sh

docker compose up -d test-db

sleep 1

bun test

docker compose down
