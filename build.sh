#!/usr/bin/env bash
set -x
set -e

BuildCommand="mvn"

cd `dirname $0`
BUILD_DIR=`pwd`
rm -rf $BUILD_DIR/output
rm -rf $BUILD_DIR/target
case $1 in
online)
    # online
    $BuildCommand -P online clean package
    ;;
local)
    # local
    $BuildCommand -P local clean package
    ;;
test)
    # local
    $BuildCommand -P test clean package
    ;;
*)
    # online
    $BuildCommand -P online clean package
esac
mkdir -p $BUILD_DIR/output
cp -r $BUILD_DIR/target/strategy-service-release-1.0.20/* $BUILD_DIR/output
echo "build successed"