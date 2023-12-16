#!/bin/bash

# Define the output directory for compiled classes
OUT_DIR="out"

# Create the output directory if it doesn't exist
if [ ! -d "$OUT_DIR" ]; then
    mkdir "$OUT_DIR"
    echo "Output directory created: $OUT_DIR"
else
    echo "Output directory already exists: $OUT_DIR"
fi

# Compilation of the Java files
echo "Compiling Java files into $OUT_DIR/"
javac -d "$OUT_DIR" src/Server/*.java src/Client/*.java src/Reservation/*.java

# Check if compilation was successful
if [ $? -ne 0 ]; then
    echo "Compilation failed."
    exit 1
else
    echo "Compilation successful."
fi

# Create manifest files for server and client
echo "Main-Class: Server.RentalsServer" > server_manifest.txt
echo "Main-Class: Client.RentalsClient" > client_manifest.txt

# Package the server JAR including the common interface
jar cvfm rmi_server.jar server_manifest.txt -C "$OUT_DIR" .

# Check if compilation was successful
if [ $? -ne 0 ]; then
    echo "Compilation failed."
    exit 1
else
    echo "Compilation successful."
fi

# Package the client JAR including the common interface and the client
jar cvfm rmi_client.jar client_manifest.txt -C "$OUT_DIR" .

# jar cvfm rmi_client.jar client_manifest.txt $(find . -name 'RentalsClient.class' -o -name 'RentalsServerIntf.class' )
# jar cvfm rmi_client.jar client_manifest.txt $(find . -path './out/Client/*.class' -o -path './out/Server/RentalsServerIntf.class')

# Check if compilation was successful
if [ $? -ne 0 ]; then
    echo "Compilation failed."
    exit 1
else
    echo "Compilation successful."
fi

echo "JAR files created: rmi_server.jar, rmi_client.jar"

# Clean up manifest files
rm server_manifest.txt client_manifest.txt
echo "Manifest files removed."

# Instructions to run the server and client
echo "To run the server, use: java -jar rmi_server.jar"
echo "To run the client, use: java -jar rmi_client.jar <server_ip>"

