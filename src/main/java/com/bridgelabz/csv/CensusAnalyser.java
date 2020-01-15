package com.bridgelabz.csv;

import com.blsolution.exception.CSVBuilderException;
import com.bridgelabz.csv.exception.AnalyserException;
import com.bridgelabz.csv.model.*;
import com.bridgelabz.csv.service.CsvLoader;
import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    public enum Country{ INDIA,US}

    Map<String,CensusCSVDao> censusCSVMap = null;
    CsvLoader csvLoader = null;

    public CensusAnalyser() {

        this.censusCSVMap = new HashMap<>();
        csvLoader = new CsvLoader();

    }



    public int loadCensusData(Country country,String... csvFilePath) {

       censusCSVMap = csvLoader.getCensusData(country,csvFilePath);
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
            censusCSVMap = this.sortBaseOnStateName();
            return new Gson().toJson(censusCSVMap);

    }


    private  Map sortBaseOnStateName(){

            return censusCSVMap.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,
                            (oldValue,newValue)->newValue,
                            LinkedHashMap::new));

    }

    public List<CensusCSVDao> sortBasedOnPopulation(){

       return censusCSVMap.values().stream().sorted((census1,census2)-> (int) (census2.population-census1.population))
                .collect(Collectors.toList());

    }



}
