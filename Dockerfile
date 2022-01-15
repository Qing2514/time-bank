FROM eclipse-temurin:17

COPY *.jar /bampo-spring.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/bampo-spring.jar"]