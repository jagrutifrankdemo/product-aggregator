FROM openjdk:17-jdk-slim-buster
copy ./target/product-aggregator-0.0.1.jar product-aggregator-0.0.1.jar
EXPOSE 8080
CMD ["java","-jar","product-aggregator-0.0.1.jar"]