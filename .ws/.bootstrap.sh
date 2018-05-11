#!/bin/bash

THIS_PATH=$(cd "$(dirname ${BASH_SOURCE[0]})";pwd)
TOP_PATH=$(dirname $THIS_PATH)

PRODUCT_PKG='com.tribodys.ph7_android'
BOOTER='.CommonActivity'
PRODUCT_LUNCHER="$PRODUCT_PKG/$BOOTER"

PRODUCT_LOCATION="$TOP_PATH/app/build/outputs/apk"
PRODUCT_FILE_NAME='app-debug.apk'

echo "switch your workspace under $TOP_PATH"
