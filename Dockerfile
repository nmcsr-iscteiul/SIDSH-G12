# Source Image name
from ubuntu:16.04

WORKDIR /usr/src/app
COPY ./ubuntu .

# Command to update and install Apache packages
RUN apt-get update && apt-get install apache2 -y
# open port 
EXPOSE 80
# Command to run Apache server in background
CMD /usr/sbin/apache2ctl -D FOREGROUND