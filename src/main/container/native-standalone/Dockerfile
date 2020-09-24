####
# This Dockerfile is used in order to build a container that runs the Quarkus application in native (no JVM) mode
#
# Build the image with:
#
# docker build -f src/main/container/native-standalone/Dockerfile \
#   -t cardil/knative-serving-showcase .
#
# Then run the container using:
#
# docker run -i --rm -p 21111:21111 cardil/knative-serving-showcase
#
###

ARG GRAALVM_VERSION=20.1.0-java11
ARG UBI_VERSION=8.2
FROM quay.io/quarkus/ubi-quarkus-native-image:${GRAALVM_VERSION} as builder

USER root
ADD . /work
WORKDIR /work
RUN ./mvnw -B -V verify
RUN ./mvnw -B -V -Pnative verify

FROM registry.access.redhat.com/ubi8/ubi-minimal:${UBI_VERSION}
WORKDIR /work/
COPY --from=builder /work/target/*-runner /work/application

# set up permissions for user `1001`
RUN chmod 775 /work /work/application \
  && chown -R 1001 /work \
  && chmod -R "g+rwX" /work \
  && chown -R 1001:root /work

EXPOSE 21111
USER 1001

CMD ["./application", "-Dquarkus.http.host=0.0.0.0"]
