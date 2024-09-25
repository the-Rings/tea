# 打包
FROM openjdk:17-jdk-alpine
WORKDIR /app/tea
COPY maker-1.0.0.jar .
EXPOSE 8082
ENTRYPOINT ["java", "-Xms500m", "-Xmx500m", "-jar", "maker-1.0.0.jar"]