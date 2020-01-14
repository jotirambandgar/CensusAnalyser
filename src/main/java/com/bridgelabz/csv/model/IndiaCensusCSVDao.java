package com.bridgelabz.csv.model;

public class IndiaCensusCSVDao {

    public  String state;
    public  int areaInSqKm;
    public int densityPerSqKm;
    public int population;

    public IndiaCensusCSVDao(IndiaCensusCSV censusCSV) {
        state = censusCSV.state;
        areaInSqKm = censusCSV.areaInSqKm;
        densityPerSqKm = censusCSV.densityPerSqKm;
        population = censusCSV.population;
    }

    @Override
    public String toString() {
        return "IndiaCensusCSVDao{" +
                "state='" + state + '\'' +
                ", areaInSqKm=" + areaInSqKm +
                ", densityPerSqKm=" + densityPerSqKm +
                ", population=" + population +
                '}';
    }

}
