#!/bin/sh
set -e

if [ "$1" = 'snack-bar' ]; then
    shift;
    # allows passing arguments to java
    # in the "docker run" command
    exec java -jar snack-bar.jar "$@"
fi

exec "$@"