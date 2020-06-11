#!/bin/bash
cp /usr/src/app/templates/header.html /usr/src/app/covid-sci-discoveries/HTML/
cp /usr/src/app/templates/footer.html /usr/src/app/covid-sci-discoveries/HTML/
cd /usr/src/app/covid-sci-discoveries/
pwd
mvn exec:java -Dexec.mainClass=covid_sci_discoveries.covid_sci_discoveries.Main
cd ..
pwd
rm ./templates/Covid_Scientific_Discoveries_Repository/*
cp -a /usr/src/app/covid-sci-discoveries/HTML/* /usr/src/app/templates/