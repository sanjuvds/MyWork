# define base docker image
FROM openjdk:17
LABEL maintainer="splunkHRconsulting.com"
ADD target/metrictoimperial-0.0.1-SNAPSHOT.jar springboot-docker-metrictoimperial-demo.jar
ENTRYPOINT [ "java", "-jar", "springboot-docker-metrictoimperial-demo.jar" ]