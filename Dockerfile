FROM openjdk:17-jdk

RUN mkdir -p /app/server
WORKDIR /app
COPY ./rmi_server.jar /app/
COPY ./input-files /app/input-files
COPY ./output-files /app/output-files
COPY ./entrypoint.sh /app/
RUN chmod +x /app/entrypoint.sh
CMD ["/app/entrypoint.sh"]
