#!/bin/bash
dockerImage=ccr.ccs.tencentyun.com/alct-tool/android-sdk:1.0
docker run --rm -i -v $(pwd):/app -v "$HOME/.gradle":/root/.gradle $dockerImage sh -c "cd /app/ && ./gradlew build"