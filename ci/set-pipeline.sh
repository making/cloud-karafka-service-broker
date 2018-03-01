#!/bin/sh
fly -t home sp -p cloud-karafka-service-broker \
    -c `dirname $0`/pipeline.yml \
    -l `dirname $0`/credentials.yml