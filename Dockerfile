FROM openjdk:18-alpine

USER root


RUN apk update && \
	apk add tzdata && \
	mkdir -p /home/deployments

ENV TZ America/Bogota

COPY ./target/registro-0.0.1.jar /home/deployments
WORKDIR /home/deployments

CMD ["java", "-jar", "registro-0.0.1.jar"]
