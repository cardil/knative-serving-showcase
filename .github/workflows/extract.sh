#!/usr/bin/env bash

set -Eeuo pipefail
set -x

IMAGE="${1:?Missing image}"
target_dir=project/work/target

tmpdir="$(mktemp -t -d builder-XXXXXX.fs)"
trap 'rm -rf "${tmpdir}"' EXIT
layer_contents="${tmpdir}/layer-contents.list"

skopeo copy \
  "containers-storage:${IMAGE}" \
  "dir:${tmpdir}"
while read -r file; do
  ft="$(file -b --mime-type "${file}")"

  if [[ "$ft" == 'application/x-tar' ]]; then
    tar -tvf "${file}" > "$layer_contents"
    if grep -q "$target_dir" "$layer_contents"; then
      tar -C "${tmpdir}" -xf "${file}" "$target_dir"
    fi
  fi
  rm -f "${file}"
done < <(find "${tmpdir}" -type f)

PROJECT_DIR="${tmpdir}/$target_dir"
mkdir -p ./target
while read -r file; do
  cp -rv "${file}" ./target/
done < <(find "${PROJECT_DIR}" \
  -type f \
  -path '*/target/*-runner' \
  -or -path '*/target/quarkus-app')
