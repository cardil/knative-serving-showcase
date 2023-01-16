#!/usr/bin/env bash

set -Eeuo pipefail

KIND="${1:?Missing kind}"
GITHUB_EVENT_NAME="${GITHUB_EVENT_NAME:?Missing event name}"

PRE_BUILD=true
PLATFORMS=linux/amd64,linux/ppc64le,linux/s390x
VERSION=noop
PUSH=true
if [ "${GITHUB_EVENT_NAME}" = "schedule" ]; then
  VERSION=nightly
elif [[ "$GITHUB_REF" == refs/tags/* ]]; then
  VERSION="${GITHUB_REF#refs/tags/}"
elif [[ "$GITHUB_REF" == refs/heads/* ]]; then
  VERSION="${GITHUB_REF#refs/heads/}"
  if [[ "${VERSION}" == bugfix/* ]] || [[ "${VERSION}" == hotfix/* ]] || [[ "${VERSION}" == feature/* ]]; then
    PUSH=false
  fi
  VERSION="$(echo "${VERSION}" | sed -r 's#/+#-#g')"
elif [[ "$GITHUB_REF" == refs/pull/* ]]; then
  VERSION="pr-${GITHUB_REF#refs/pull/}"
  VERSION="${VERSION%/*}"
  PUSH=false
fi
if [[ "${KIND}" == *"native"* ]]; then
  VERSION="${VERSION}-native"
  PLATFORMS=linux/amd64
fi
if [[ "${KIND}" == *"standalone"* ]]; then
  PRE_BUILD=false
fi
TAGS="${VERSION}"

{
  echo "version=${VERSION}"
  echo "tags=${TAGS}"
  echo "created=$(date -u +'%Y-%m-%dT%H:%M:%SZ')"
  echo "platforms=${PLATFORMS}"
  echo "pre_build=${PRE_BUILD}"
  echo "push=${PUSH}"
} >> "$GITHUB_OUTPUT"
