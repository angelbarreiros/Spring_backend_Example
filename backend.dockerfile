FROM eclipse-temurin:17-alpine

ARG JAR_FILE=target/pa-project-backend-1.0.0.jar
ARG APP_HOME=/usr/app/

RUN mkdir -p ${APP_HOME}
WORKDIR ${APP_HOME}

COPY ${JAR_FILE} ${APP_HOME}
ENTRYPOINT ["java","-jar","pa-project-backend-1.0.0.jar"]
