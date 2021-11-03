FROM ubuntu:20.04


ENV TZ=Europe/Warsaw
RUN /bin/sh -c "ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone"


RUN apt-get update 
RUN apt-get upgrade 


RUN apt-get install -y unzip vim git curl wget zip 


RUN useradd -ms /bin/bash adev
RUN adduser adev sudo

USER adev
WORKDIR /home/adev/


RUN curl -s "https://get.sdkman.io" | bash
RUN chmod a+x "/home/adev/.sdkman/bin/sdkman-init.sh"
RUN bash -c "source /home/adev/.sdkman/bin/sdkman-init.sh && sdk install java 11.0.12-zulu"
RUN bash -c "source /home/adev/.sdkman/bin/sdkman-init.sh && sdk install gradle 7.1"
RUN bash -c "source /home/adev/.sdkman/bin/sdkman-init.sh && sdk install kotlin"


RUN mkdir projekt
WORKDIR /home/adev/projekt/
