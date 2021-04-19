FROM openjdk:11-jre
COPY target/pplflw-api-0.0.1-SNAPSHOT.jar /opt/pplflw-api.jar
RUN bash -c 'touch /opt/pplflw-api.jar'
ENTRYPOINT ["java","-jar","/opt/pplflw-api.jar"]