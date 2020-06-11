#!/bin/bash
cd ..
pwd
cp ./templates/header.html ./covid-sci-discoveries/HTML/
cp ./templates/footer.html ./covid-sci-discoveries/HTML/
cd ./covid-sci-discoveries/
mvn exec:java -Dexec.mainClass=covid_sci_discoveries.covid_sci_discoveries.Main
cd ..
rm ./templates/Covid_Scientific_Discoveries_Repository/*
cp -a ./covid-sci-discoveries/HTML/. ./templates/