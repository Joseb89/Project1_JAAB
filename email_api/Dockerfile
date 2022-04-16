FROM openjdk:11
COPY target/*.jar email_api.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "email_api.jar"]