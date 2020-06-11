#!/bin/bash
cd /usr/src/app/covid-graph-spread/
mvn exec:java -Dexec.mainClass=main.Main
cp /usr/src/app/covid-graph-spread/HTML/covid-graph-spread.html /usr/src/app/templates/