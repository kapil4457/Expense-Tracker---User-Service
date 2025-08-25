FROM amazoncorretto:21
COPY ./userservice-0.0.1-SNAPSHOT.jar ./user-service.jar
ENTRYPOINT ["java","-jar","user-service.jar"]
