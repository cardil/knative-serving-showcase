####
# This Containerfile is used in order to pre-build a container that runs the
# Quarkus application in JVM mode
#
# Build the app with:
#
# podman build -f src/main/container/jvm/build.Containerfile .
#

FROM registry.access.redhat.com/ubi8/openjdk-17

USER root
RUN useradd --create-home --home-dir /project quarkus
USER quarkus
ADD --chown=quarkus . /project/work
WORKDIR /project/work
ENV MVNW_VERBOSE=true
RUN ./mvnw -V -B --no-transfer-progress -Dmaven.artifact.threads=50 clean dependency:go-offline
RUN ./mvnw -V -B --no-transfer-progress verify
