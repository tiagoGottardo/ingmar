#!/bin/sh
docker compose up -d test-db

sleep 1

bun test --preload ./apps/server/tests/setup.ts --serial

docker compose down
