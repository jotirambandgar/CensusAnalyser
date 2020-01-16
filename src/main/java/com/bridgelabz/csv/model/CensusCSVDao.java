package com.bridgelabz.csv.model;

import com.bridgelabz.csv.CensusAnalyser;

public class CensusCSVDao {

    public String state;
    public String stateCode;
    public double totalArea;
    public double populationDensity;
    public double population;

    public CensusCSVDao(IndiaCensusCSV censusCSV) {

        state = censusCSV.state;
        totalArea = censusCSV.areaInSqKm;
        populationDensity = censusCSV.densityPerSqKm;
        population = censusCSV.population;

    }

    public CensusCSVDao(USCensusCSV usCensusCSV) {

        state = usCensusCSV.stateName;
        stateCode=usCensusCSV.stateId;
        totalArea = usCensusCSV.totalArea;
        populationDensity =usCensusCSV.populationDensity;
        population = usCensusCSV.population;

    }

    public Object getCensusDto(CensusAnalyser.Country country){

        if(country.equals(CensusAnalyser.Country.US))
            return new USCensusCSV(state,stateCode,totalArea,populationDensity,population);

        return new IndiaCensusCSV( state , (int) totalArea ,(int) populationDensity , (int)population);
    }


    @Override
    public String toString() {
        return "IndiaCensusCSVDao{" +
                "state='" + state + '\'' +
                ", areaInSqKm=" + totalArea +
                ", densityPerSqKm=" + populationDensity +
                ", population=" + population +
                '}';
    }

}
