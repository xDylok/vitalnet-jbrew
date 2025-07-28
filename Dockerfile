FROM icr.io/appcafe/open-liberty:full-java21-openj9-ubi-minimal
COPY target/jbrew-web.war /config/apps/
COPY libs/postgresql-42.7.6.jar /opt/ol/wlp/usr/shared/resources/

EXPOSE 8081