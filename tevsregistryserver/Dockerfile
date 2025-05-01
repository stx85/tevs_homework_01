FROM openjdk:23-slim
LABEL authors="bswe-tevs-group-j"

COPY build/libs/tevsregistryserver.jar /app.jar

EXPOSE 8090/tcp

CMD [ "java", "-jar", "/app.jar" ]