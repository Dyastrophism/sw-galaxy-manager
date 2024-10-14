FROM ubuntu:latest
LABEL authors="Dyastrophism"

ENTRYPOINT ["top", "-b"]

FROM mysql:8.0
ENV MYSQL_DATABASE=sw-galaxy-manager-db
ENV MYSQL_ROOT_PASSWORD=root