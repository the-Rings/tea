# 编译
# FROM maven AS build
# WORKDIR /app/build
# VOLUME ~/.m2 /root/.m2
# COPY . .
# RUN mvn clean package

# 打包
FROM openjdk:17-jdk-alpine
WORKDIR /app/tea
COPY binarytea-1.0.0.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-Xms500m", "-Xmx500m", "-jar", "binarytea-1.0.0.jar"]