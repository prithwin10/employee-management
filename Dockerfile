FROM openjdk:17

EXPOSE 8080

ADD target/employee-management.jar employee-management.jar

ENTRYPOINT ["java", "-jar", "employee-management.jar"]