#!/usr/bin/env bash
set -euo pipefail

# run-javafx-direct.sh
# Helper to run the JavaFX GUI without `mvn javafx:run` by using
# a locally-downloaded JavaFX SDK (macOS) and project runtime dependencies.
#
# Usage:
#   - Ensure JavaFX SDK is downloaded (macOS x64/arm64) and its `lib` dir
#     is at `$HOME/javafx-sdk-21/lib` (or set env `JAVA_FX_LIB` to its lib path).
#   - Run: `./run-javafx-direct.sh`

JAVA_FX_LIB=${JAVA_FX_LIB:-"$HOME/javafx-sdk-21/lib"}

if [ ! -d "$JAVA_FX_LIB" ]; then
  echo "ERROR: JavaFX SDK lib directory not found: $JAVA_FX_LIB"
  echo "Download a matching OpenJFX SDK for your JDK from https://gluonhq.com/products/javafx/"
  echo "Then set: export JAVA_FX_LIB=~/path/to/javafx-sdk-21/lib"
  exit 1
fi

echo "Using JavaFX libs from: $JAVA_FX_LIB"

echo "Copying runtime dependencies into target/dependency..."
mvn -q dependency:copy-dependencies -DoutputDirectory=target/dependency

echo "Compiling project..."
mvn -q compile

CP="target/classes:target/dependency/*"

echo "Launching JavaFX application (Java must be 11+)"
exec java --module-path "$JAVA_FX_LIB" --add-modules=javafx.controls,javafx.fxml -cp "$CP" com.companyz.ems.JavaFxLauncher "$@"
