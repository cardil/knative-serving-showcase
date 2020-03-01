####
# This Dockerfile is used in order to build a container that runs the Quarkus application in native (no JVM) mode
#
# Build the image with:
#
# docker build -f src/main/docker/native/Dockerfile -t cardil/knative-serving-showcase .
#
# Then run the container using:
#
# docker run -i --rm -p 8080:8080 cardil/knative-serving-showcase
#
###

ARG IMAGE_TAG=20.0.0-java11
# 2G mem limit is for quay.io builds
ARG MAVEN_ARGS='-B -Dquarkus.native.additional-build-args=-J-Xmx2g'
FROM quay.io/quarkus/ubi-quarkus-native-image:${IMAGE_TAG} as builder
USER root
WORKDIR /work
COPY ./ /work
RUN ./mvnw install
RUN ./mvnw ${MAVEN_ARGS} -Pnative quarkus:build

FROM registry.access.redhat.com/ubi8/ubi-minimal:8.1
WORKDIR /work/
COPY --from=builder /work/target/*-runner /work/application

# set up permissions for user `1001`
RUN chmod 775 /work /work/application \
  && chown -R 1001 /work \
  && chmod -R "g+rwX" /work \
  && chown -R 1001:root /work

EXPOSE 8080
USER 1001

CMD ["./application", "-Dquarkus.http.host=0.0.0.0"]
