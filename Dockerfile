FROM python:3

COPY . /usr/src/app/
WORKDIR /usr/src/app/

RUN python -m pip install --upgrade pip && pip install -r requirements.txt

# Install OpenJDK-11
RUN apt-get update && \
    apt-get install -y default-jre && \
    apt-get install -y ant && \
    apt-get clean;

# Maven
RUN apt-get install -y maven

# Fix certificate issues
RUN apt-get update && \
    apt-get install ca-certificates-java && \
    apt-get clean && \
    update-ca-certificates -f;

# Setup JAVA_HOME -- useful for docker commandline
ENV JAVA_HOME /usr/lib/jvm/java-11-openjdk-amd64/
RUN export JAVA_HOME

RUN cd ./java/covid-evolution-diff && mvn compile
RUN cd ./java/covid-graph-spread && mvn compile
RUN cd ./java/covid-sci-discoveries && mvn compile
RUN cd ./java/covid-query && mvn compile

CMD ["python", "run.py"]