# 1. Start with a base image that already has Java 21 and Tomcat 10.1 installed
FROM tomcat:10.1-jdk21

# 2. Delete the default Tomcat apps just to keep things clean
RUN rm -rf /usr/local/tomcat/webapps/ROOT

COPY target/ServletBank-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Expose the port Tomcat runs on
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]