#!/usr/bin/env bash
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#


CONSUL_VER="1.11.2"
CONSUL_ZIP="consul_${CONSUL_VER}_darwin_amd64.zip"

# cleanup
rm -rf "target/consul-data"
rm -rf "target/consul-config"
rm -rf "target/consul"

mkdir -p target/
mkdir -p target/consul-data
mkdir -p target/consul-config


if [ ! -f target/$CONSUL_ZIP ]; then
    echo Downloading: https://releases.hashicorp.com/consul/$CONSUL_VER/$CONSUL_ZIP 
    curl -o target/$CONSUL_ZIP "https://releases.hashicorp.com/consul/$CONSUL_VER/$CONSUL_ZIP"
fi


unzip -d target target/$CONSUL_ZIP
chmod +x target/consul

target/consul \
    agent \
    -server \
    -bootstrap \
    -datacenter camel \
    -advertise 127.0.0.1 \
    -bind 0.0.0.0 \
    -log-level trace \
    -data-dir target/consul-data \
    -config-dir src/main/resources/consul \
    -ui
