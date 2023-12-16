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

# Compile the source files
# javac -d "$OUT_DIR" src/*.java
javac -d "$OUT_DIR" src/Server/*.java src/Client/*.java src/Reservation/*.java

# Check if compilation was successful
if [ $? -ne 0 ]; then
    echo "Compilation failed."
    exit 1
else
    echo "Compilation successful."
fi

# Create manifest files for server and client
# echo "Main-Class: Server.RentalsServer" > server_manifest.txt
# echo "Main-Class: Client.RentalsClient" > client_manifest.txt

# Create manifest files for server and client
echo "Main-Class: production.CD_Backend_Tests.Server.RentalsServer" > server_manifest.txt
echo "Main-Class: production.CD_Backend_Tests.Client.RentalsClient" > client_manifest.txt

# Create the jar files
jar cvfm rmi_client.jar client_manifest.txt -C out/production/CD_Backend_Tests/ Client/RentalsClient.class Server/RentalsServerIntf.class
jar cvfm rmi_server.jar server_manifest.txt -C out/production/CD_Backend_Tests/ .

# jar cvfm rmi_client.jar client_manifest.txt $(find . -name 'RentalsClient.class' -o -name 'RentalsServerIntf.class' )

# jar cvfm rmi_server.jar server_manifest.txt $(find . \( -name '*.class' -o -name 'application.properties' \) ! -path "*Client*")
# jar cvfm rmi_server.jar server_manifest.txt $(find . \( -name '*.class' -o -path './input-files/*' \) ! -path "*Client*")

# Create the jar files
# jar cvfm rmi_client.jar client_manifest.txt $(find . -path './out/Client/*.class' -o -path './out/Server/RentalsServerIntf.class' )
# jar cvfm rmi_server.jar server_manifest.txt $(find . -path './out/Server/*.class' -o -path './out/Reservation/*.class' -o -path './input-files/*' ! -path "*Client*")

# jar cvfm rmi_client.jar client_manifest.txt -C out/production/CD_Backend_Tests/ Client/RentalsClient.class Server/RentalsServerIntf.class
# jar cvfm rmi_server.jar server_manifest.txt -C out/production/CD_Backend_Tests/ .

rm server_manifest.txt
echo "Server manifest file removed."
rm client_manifest.txt
echo "Client manifest file removed."