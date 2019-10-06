#!/bin/bash

./mvnw install dockerfile:build && docker-compose up
