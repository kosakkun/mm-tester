FROM ubuntu:18.04

RUN apt-get update && \
    apt-get install -y \
        default-jre \
        default-jdk \
        build-essential \
        curl \
        zip \
        unzip \
        python3

SHELL ["/bin/bash", "-c"]

RUN curl -s https://get.sdkman.io | bash && \
    source ~/.sdkman/bin/sdkman-init.sh && \
    sdk install gradle
