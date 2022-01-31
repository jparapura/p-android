FROM ubuntu:20.04


ENV TZ=Europe/Warsaw
RUN /bin/sh -c "ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone"


RUN apt-get update -y
RUN apt-get upgrade -y


RUN apt-get install -y unzip vim git curl wget zip net-tools


RUN useradd -ms /bin/bash adev
RUN adduser adev sudo

USER adev
WORKDIR /home/adev/


RUN curl -s "https://get.sdkman.io" | bash
RUN chmod a+x "/home/adev/.sdkman/bin/sdkman-init.sh"
RUN bash -c "source /home/adev/.sdkman/bin/sdkman-init.sh && sdk install java 11.0.12-zulu"
RUN bash -c "source /home/adev/.sdkman/bin/sdkman-init.sh && sdk install gradle 7.1"
RUN bash -c "source /home/adev/.sdkman/bin/sdkman-init.sh && sdk install kotlin"

ENV PATH=/home/adev/.sdkman/candidates/java/current/bin:$PATH
ENV PATH=/home/adev/.sdkman/candidates/gradle/current/bin:$PATH
ENV PATH=/home/adev/.sdkman/candidates/kotlin/current/bin:$PATH


RUN mkdir projekt
WORKDIR /home/adev/projekt/

RUN git clone https://github.com/tangens90/p-android.git
WORKDIR /home/adev/projekt/p-android/zadanie4-ktor

EXPOSE 8080/tcp

CMD ["gradle", "run"]
