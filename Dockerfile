FROM openjdk:14-jdk-alpine
COPY target/market_place_rest-0.0.1-SNAPSHOT.jar market_place_rest.jar
ENTRYPOINT ["java","-jar","/market_place_rest.jar"]