#!/bin/bash

#Hay que pararse en la raiz del proyecto

rm -rfv build/distributions/*

gradle build

cd build/distributions

unzip "SimulacionFinal_*.zip" -d SimuFinal

cd SimuFinal/

java -jar SimulacionFinal.jar

cd ../../../
