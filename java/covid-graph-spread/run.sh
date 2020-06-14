#!/bin/bash
cp /usr/src/app/templates/header.html /usr/src/app/java/covid-graph-spread/HTML/ && cp /usr/src/app/templates/footer.html /usr/src/app/java/covid-graph-spread/HTML/ && cd /usr/src/app/java/covid-graph-spread/ && mvn exec:java -Dexec.mainClass=main.Main && cp /usr/src/app/java/covid-graph-spread/HTML/covid-graph-spread.html /usr/src/app/templates/
