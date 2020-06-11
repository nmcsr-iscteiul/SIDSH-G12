#!/bin/bash
cp /usr/src/app/templates/header.html /usr/src/app/java/covid-sci-discoveries/HTML/
cp /usr/src/app/templates/footer.html /usr/src/app/java/covid-sci-discoveries/HTML/
cd /usr/src/app/java/covid-sci-discoveries/
pwd
mvn exec:java -Dexec.mainClass=covid_sci_discoveries.covid_sci_discoveries.Main
cd ..
pwd
rm /usr/src/app/web/Covid_Scientific_Discoveries_Repository/*
cp -a /usr/src/app/java/covid-sci-discoveries/HTML/Covid_Scientific_Discoveries_Repository/* /usr/src/app/web/Covid_Scientific_Discoveries_Repository/
cp -a /usr/src/app/java/covid-sci-discoveries/HTML/covid-sci-discoveries.html /usr/src/app/templates/
