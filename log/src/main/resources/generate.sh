#!/bin/bash

java \
-cp ../../../target/log-processor-0.0.1-SNAPSHOT.jar \
com.quantil.logger.LogGenerator \
$1