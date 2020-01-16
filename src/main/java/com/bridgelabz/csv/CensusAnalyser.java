package com.bridgelabz.csv;

import com.blsolution.exception.CSVBuilderException;
import com.bridgelabz.csv.exception.AnalyserException;
import com.bridgelabz.csv.factory.CensusAdapterFactory;
import com.bridgelabz.csv.model.*;
import com.google.gson.Gson;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    public enum Country{ INDIA,US;
    }

    Map<String,CensusCSVDao> censusCSVMap = null;


    public CensusAnalyser() {

        this.censusCSVMap = new HashMap<>();


    }



    public int loadCensusData(Country country,String... csvFilePath) {

       censusCSVMap = CensusAdapterFactory.getCensusData(country,csvFilePath);
       return censusCSVMap.size();

    }


    private <E> int getCount(Iterator<E> iterator) {

        try {
            Iterable<E> CSVIterable = () -> iterator;
            int numOfEnteries = (int) StreamSupport.stream(CSVIterable.spliterator(), false).count();
            return numOfEnteries;
        }catch (RuntimeException e){
            throw new CSVBuilderException(e.getMessage(),
                CSVBuilderException.ExceptionType.INVALID_DATA);
        }

    }

    public String sortCensusDataStateWise() {

            if (censusCSVMap.size() == 0 || censusCSVMap == null){
                throw new AnalyserException("No Census Data",
                        AnalyserException.ExceptionType.NO_CENSUS_DATA);
            }
            List<CensusCSVDao> sortedData =  this.sortBaseOnStateName();
            return new Gson().toJson(sortedData);

    }
    public String sortCensusDataPopulationWise() {

        if (censusCSVMap.size() == 0 || censusCSVMap == null){
            throw new AnalyserException("No Census Data",
                    AnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        String sortedData =  this.sortBasedOnPopulation();
        return sortedData;

    }



    private  List<CensusCSVDao> sortBaseOnStateName(){

            return censusCSVMap.values().stream().sorted((census1,census2)-> census1.
                    state.compareTo(census2.state)).collect(Collectors.toList());

    }

    public String sortBasedOnPopulation(){

       List censusDto= censusCSVMap.values().stream().sorted((census1,census2)-> (int) (census2.population-census1.population))
                .map(censusCSVDao -> censusCSVDao.getCensusDto(Country.INDIA)).collect(Collectors.toList());

       return new Gson().toJson(censusDto);

    }

    public String sortPopulationDensityWiseInDescendingOrder() {
        List censusDto= censusCSVMap.values().stream().sorted((census1,census2)-> (int) (census2.populationDensity-census1.populationDensity))
                .map(censusCSVDao -> censusCSVDao.getCensusDto(Country.INDIA)).collect(Collectors.toList());
        System.out.println(censusDto.stream());
        return new Gson().toJson(censusDto);
    }

    public String sortTotalAreaWiseInDescendingOrder() {

        List censusDto= censusCSVMap.values().
                        stream().sorted((census1,census2)->
                                        (int) (census2.totalArea-census1.totalArea))
                                        .map(censusCSVDao -> censusCSVDao.getCensusDto(Country.INDIA))
                                        .collect(Collectors.toList());
        System.out.println(censusDto);
        return new Gson().toJson(censusDto);

    }


}
