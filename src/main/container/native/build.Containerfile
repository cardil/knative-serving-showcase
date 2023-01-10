####
# This Containerfile is used in order to pre-build a container that runs the
# Quarkus application in native mode
#
# Build the app with:
#
# podman build -f src/main/container/native/build.Containerfile .
#

ARG GRAALVM_VERSION=22.3
FROM registry.access.redhat.com/quarkus/mandrel-22-rhel8:${GRAALVM_VERSION}

ADD --chown=quarkus . /project/work
WORKDIR /project/work
ENV MVNW_VERBOSE=true
RUN ./mvnw -V -B --no-transfer-progress -Pnative -Dmaven.artifact.threads=50 clean dependency:go-offline
RUN ./mvnw -V -B --no-transfer-progress -Pnative verify
