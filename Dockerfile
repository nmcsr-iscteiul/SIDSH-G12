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

# Write shell files [To run the Java Programs]
RUN echo -e '#!/bin/bash' > ./java/covid-sci-discoveries/run.sh
RUN echo "rm -rf /usr/src/app/java/covid-sci-discoveries/HTML/Covid_Scientific_Discoveries_Repository/* && cp -a /usr/src/app/web/wp-content/uploads/.pdf /usr/src/app/java/covid-sci-discoveries/HTML/Covid_Scientific_Discoveries_Repository && ls /usr/src/app/java/covid-sci-discoveries/HTML/Covid_Scientific_Discoveries_Repository/ && cp /usr/src/app/templates/header.html /usr/src/app/java/covid-sci-discoveries/HTML/ && cp /usr/src/app/templates/footer.html /usr/src/app/java/covid-sci-discoveries/HTML/ && cd /usr/src/app/java/covid-sci-discoveries/ && mvn exec:java -Dexec.mainClass=covid_sci_discoveries.covid_sci_discoveries.Main && rm -rf /usr/src/app/web/Covid_Scientific_Discoveries_Repository/ && cp -a /usr/src/app/java/covid-sci-discoveries/HTML/Covid_Scientific_Discoveries_Repository/. /usr/src/app/web/Covid_Scientific_Discoveries_Repository/ && cp -a /usr/src/app/java/covid-sci-discoveries/HTML/covid-sci-discoveries.html /usr/src/app/templates/" >> ./java/covid-sci-discoveries/run.sh
RUN chmod 777 ./java/covid-sci-discoveries/run.sh
RUN sed -i -e 's/\r$//' ./java/covid-sci-discoveries/run.sh

RUN echo -e '#!/bin/bash' > ./java/covid-graph-spread/run.sh
RUN echo "cp /usr/src/app/templates/header.html /usr/src/app/java/covid-graph-spread/HTML/ && cp /usr/src/app/templates/footer.html /usr/src/app/java/covid-graph-spread/HTML/ && cd /usr/src/app/java/covid-graph-spread/ && mvn exec:java -Dexec.mainClass=main.Main && cp /usr/src/app/java/covid-graph-spread/HTML/covid-graph-spread.html /usr/src/app/templates/" >> ./java/covid-graph-spread/run.sh
RUN chmod 777 ./java/covid-graph-spread/run.sh
RUN sed -i -e 's/\r$//' ./java/covid-graph-spread/run.sh

RUN echo -e '#!/bin/bash' > ./java/covid-evolution-diff/run.sh
RUN echo "cp /usr/src/app/templates/header.html /usr/src/app/java/covid-evolution-diff/HTML/ && cp /usr/src/app/templates/footer.html /usr/src/app/java/covid-evolution-diff/HTML/ && cd /usr/src/app/java/covid-evolution-diff/ && mvn exec:java -Dexec.mainClass=covid_evolution_diff.Main && cp -a /usr/src/app/java/covid-evolution-diff/HTML/covid-evolution-diff.html /usr/src/app/templates/" >> ./java/covid-evolution-diff/run.sh
RUN chmod 777 ./java/covid-evolution-diff/run.sh
RUN sed -i -e 's/\r$//' ./java/covid-evolution-diff/run.sh

CMD ["python", "run.py"]