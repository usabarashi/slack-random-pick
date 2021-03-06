#-------------------------------------------------------------------------------------------------------------
# Copyright (c) Microsoft Corporation. All rights reserved.
# Licensed under the MIT License. See https://go.microsoft.com/fwlink/?linkid=2090316 for license information.
#-------------------------------------------------------------------------------------------------------------

FROM openjdk:11-jdk-slim

# This Dockerfile adds a non-root user with sudo access. Use the "remoteUser"
# property in devcontainer.json to use it. On Linux, the container user's GID/UIDs
# will be updated to match your local UID/GID (when using the dockerFile property).
# See https://aka.ms/vscode-remote/containers/non-root-user for details.
ARG USERNAME=vscode
ARG USER_UID=1000
ARG USER_GID=$USER_UID

# Configure apt
ENV DEBIAN_FRONTEND=noninteractive
RUN apt-get update \
    && apt install -y --no-install-recommends apt-utils dialog 2>&1 \
    #
    # Create a non-root user to use if preferred - see https://aka.ms/vscode-remote/containers/non-root-user.
    && groupadd --gid $USER_GID $USERNAME \
    && useradd -s /bin/bash --uid $USER_UID --gid $USER_GID -m $USERNAME \
    # [Optional] Add sudo support for the non-root user
    && apt install -y \
        sudo \
    && echo $USERNAME ALL=\(root\) NOPASSWD:ALL > /etc/sudoers.d/$USERNAME\
    && chmod 0440 /etc/sudoers.d/$USERNAME \
    #
    # Verify git, needed tools installed
    && apt install -y \
        git \
        openssh-client \
        iproute2 \
        procps \
        curl \
        lsb-release \
        gnupg

#-------------------Uncomment the following steps to install Maven CLI Tools----------------------------------
# ARG MAVEN_VERSION=3.6.3
# ARG MAVEN_SHA=c35a1803a6e70a126e80b2b3ae33eed961f83ed74d18fcd16909b2d44d7dada3203f1ffe726c17ef8dcca2dcaa9fca676987befeadc9b9f759967a8cb77181c0
# RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
#   && curl -fsSL -o /tmp/apache-maven.tar.gz https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
#   && echo "${MAVEN_SHA} /tmp/apache-maven.tar.gz" | sha512sum -c - \
#   && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
#   && rm -f /tmp/apache-maven.tar.gz \
#   && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn
# COPY maven-settings.xml /usr/share/maven/ref/
# ENV MAVEN_HOME /usr/share/maven
# ENV MAVEN_CONFIG /root/.m2
#-------------------------------------------------------------------------------------------------------------

#-------------------Uncomment the following steps to install Gradle CLI Tools---------------------------------
# ENV GRADLE_HOME /opt/gradle
# ENV GRADLE_VERSION 5.4.1
# ARG GRADLE_DOWNLOAD_SHA256=7bdbad1e4f54f13c8a78abc00c26d44dd8709d4aedb704d913fb1bb78ac025dc
# RUN curl -sSL --output gradle.zip "https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip" \
#     && echo "${GRADLE_DOWNLOAD_SHA256} *gradle.zip" | sha256sum --check - \
#     && unzip gradle.zip \
#     && rm gradle.zip \
#     && mv "gradle-${GRADLE_VERSION}" "${GRADLE_HOME}/" \
#     && ln -s "${GRADLE_HOME}/bin/gradle" /usr/bin/gradle
#-------------------------------------------------------------------------------------------------------------

# Setup scala build tools 
# https://www.scala-sbt.org/1.x/docs/Installing-sbt-on-Linux.html
RUN echo "deb https://dl.bintray.com/sbt/debian /" | sudo tee -a /etc/apt/sources.list.d/sbt.list \
    &&  curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x2EE0EA64E40A89B84B2DF73499E82A75642AC823" | sudo apt-key add \
    &&  apt update -y \
    &&  apt install sbt

# Clean up
RUN apt autoremove -y \
    && apt clean -y \
    && rm -rf /var/lib/apt/lists/*
ENV DEBIAN_FRONTEND=dialog

# Allow for a consistant java home location for settings - image is changing over time
RUN if [ ! -d "/docker-java-home" ]; then ln -s "${JAVA_HOME}" /docker-java-home; fi