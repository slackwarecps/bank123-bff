#!/bin/bash
source .env
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
