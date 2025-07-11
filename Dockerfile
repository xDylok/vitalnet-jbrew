FROM icr.io/appcafe/open-liberty:full-java21-openj9-ubi-minimal
COPY src/main/liberty/config/server.xml /config/server.xml
COPY target/jbrew-web.war /config/apps/
