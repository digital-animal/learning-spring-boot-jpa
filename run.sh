#!/usr/bin/bash
clear
export JAVA_HOME="/usr/lib/jvm/java-1.11.0-openjdk-amd64"
mvn clean
mvn spring-boot:run