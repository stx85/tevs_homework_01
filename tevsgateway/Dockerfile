FROM openjdk:23-slim
LABEL authors="bswe-tevs-group-j"

COPY build/libs/tevsgateway.jar /app.jar

EXPOSE 8080/tcp

CMD [ "java", "-jar", "/app.jar" ]