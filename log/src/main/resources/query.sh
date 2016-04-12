#!/bin/bash

java \
-cp ../../../target/log-processor-0.0.1-SNAPSHOT.jar \
com.quantil.logger.LogRetriever \
$1