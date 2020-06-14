#!/bin/bash
cp /usr/src/app/templates/header.html /usr/src/app/java/covid-evolution-diff/HTML/ && cp /usr/src/app/templates/footer.html /usr/src/app/java/covid-evolution-diff/HTML/ && cd /usr/src/app/java/covid-evolution-diff/ && mvn exec:java -Dexec.mainClass=covid_evolution_diff.Main && cp -a /usr/src/app/java/covid-evolution-diff/HTML/covid-evolution-diff.html /usr/src/app/templates/
