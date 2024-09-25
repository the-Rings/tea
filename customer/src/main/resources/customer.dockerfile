# 打包
FROM openjdk:17-jdk-alpine
WORKDIR /app/tea
COPY customer-1.0.0.jar .
EXPOSE 8081
ENTRYPOINT ["java", "-Xms500m", "-Xmx500m", "-jar", "customer-1.0.0.jar"]