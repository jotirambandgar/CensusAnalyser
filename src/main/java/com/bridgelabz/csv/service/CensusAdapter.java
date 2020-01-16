package com.bridgelabz.csv.service;

import com.blsolution.exception.CSVBuilderException;
import com.blsolution.factory.CSVBuilderFactory;
import com.blsolution.repository.IOpenCsvBuilder;
import com.bridgelabz.csv.CensusAnalyser;
import com.bridgelabz.csv.exception.AnalyserException;
import com.bridgelabz.csv.model.CensusCSVDao;
import com.bridgelabz.csv.model.IndiaCensusCSV;
import com.bridgelabz.csv.model.IndiaStateCode;
import com.bridgelabz.csv.model.USCensusCSV;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public abstract class CensusAdapter {

    public  abstract   Map<String, CensusCSVDao> getCensusData(String... csvFile);

    public  <E> Map<String, CensusCSVDao> getCensusData(Class censusCSVClass,String csvFile){
        Map<String,CensusCSVDao> censusCSVMap = new HashMap<>();
        try(Reader reader = Files.newBufferedReader(Paths.get(csvFile))) {

            IOpenCsvBuilder csvBuilder=  CSVBuilderFactory.createCSVBuilder();
            Iterator<E> iterator = csvBuilder.getIterator(reader, censusCSVClass);
            Iterable<E> iterable = () -> iterator;
            if(censusCSVClass.getName().equals("com.bridgelabz.csv.model.IndiaCensusCSV")) {
                StreamSupport.stream(iterable.spliterator(), false).
                        map(IndiaCensusCSV.class::cast).
                        forEach(indianCensus ->
                                censusCSVMap.put(indianCensus.state, new CensusCSVDao(indianCensus)));
            }else {
                StreamSupport.stream(iterable.spliterator(), false).
                        map(USCensusCSV.class::cast).
                        forEach(UsCensus ->
                                censusCSVMap.put(UsCensus.stateName, new CensusCSVDao(UsCensus)));
            }

                return censusCSVMap;


        } catch (IOException e) {

            throw new AnalyserException(e.getMessage(),
                    AnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);

        } catch (CSVBuilderException e){

            throw new AnalyserException(e.getMessage(),
                    e.type.name());

        } catch (RuntimeException e){

            throw new AnalyserException(e.getMessage(),
                    AnalyserException.ExceptionType.INVALID_DATA);
        }
    }

}
