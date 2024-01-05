FROM eclipse-temurin:17 as jre-build

# Create a custom Java runtime
RUN $JAVA_HOME/bin/jlink \
         --add-modules java.base \
         --strip-debug \
         --no-man-pages \
         --no-header-files \
         --compress=2 \
         --output /javaruntime

FROM gradle:8.5.0-jdk17 as app-build
RUN mkdir /opt/app
COPY src build.gradle settings.gradle /opt/app/
RUN cd /opt/app \
    && gradle build \
    && mv build/libs/voting-system-0.0.1-SNAPSHOT.jar /app.jar

# Define your base image
FROM debian:buster-slim
ENV JAVA_HOME=/opt/java/openjdk
ENV PATH "${JAVA_HOME}/bin:${PATH}"
COPY --from=jre-build /javaruntime $JAVA_HOME

RUN mkdir /opt/app
COPY --from=app-build /app.jar /opt/app/japp.jar

CMD ["java", "-jar", "/opt/app/japp.jar"]
EXPOSE 8080
