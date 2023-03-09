FROM tomcat:9.0.48-jdk11-openjdk-slim
EXPOSE 8080
COPY target/true-caller-rest.war /usr/local/tomcat/webapps
CMD ["catalina.sh","run"]