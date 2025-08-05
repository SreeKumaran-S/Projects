#!/bin/bash

# CONFIG
SRC_DIR=src
CLASSES_DIR=WEB-INF/classes
LIB_DIR=WEB-INF/lib
EXT_JARS=$(find $LIB_DIR -name "*.jar" | tr '\n' ':')

# CLEAN
echo "[INFO] Cleaning old class files..."
rm -rf $CLASSES_DIR/*
mkdir -p $CLASSES_DIR

# COMPILE
echo "[INFO] Compiling .java files from $SRC_DIR..."
find $SRC_DIR -name "*.java" > sources.txt

javac -g -cp "$EXT_JARS" -d $CLASSES_DIR @sources.txt
rm sources.txt

# PACKAGE EACH TOP-LEVEL PACKAGE INTO A SEPARATE JAR
echo "[INFO] Creating JARs for each top-level package..."
for pkg in $(find $CLASSES_DIR -mindepth 1 -maxdepth 1 -type d -exec basename {} \;); do
  jar_path=$LIB_DIR/$pkg.jar
  echo "[INFO] Packaging $pkg -> $jar_path"
  jar -cf $jar_path -C $CLASSES_DIR $pkg
done

# CLEANUP - Remove compiled classes since they're now in JARs
echo "[INFO] Cleaning up compiled classes..."
rm -rf $CLASSES_DIR/*

echo "[SUCCESS] Build complete. JARs created in $LIB_DIR"
