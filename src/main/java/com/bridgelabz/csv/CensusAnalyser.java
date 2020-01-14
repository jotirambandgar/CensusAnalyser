package com.bridgelabz.csv;

import com.blsolution.exception.CSVBuilderException;
import com.blsolution.factory.CSVBuilderFactory;
import com.blsolution.repository.IOpenCsvBuilder;
import com.bridgelabz.csv.exception.AnalyserException;
import com.bridgelabz.csv.model.IndiaCensusCSV;
import com.bridgelabz.csv.model.CensusCSVDao;
import com.bridgelabz.csv.model.IndiaStateCode;
import com.bridgelabz.csv.model.USCensusCSV;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    Map<String,CensusCSVDao> censusCSVMap = null;

    public CensusAnalyser() {
        this.censusCSVMap = new HashMap<>();
    }

    public int loadIndiaCensusData(String csvFilePath)    {
        return this.loadCensusData(csvFilePath,IndiaCensusCSV.class);

    }

    private <E> int loadCensusData(String csvFilePath, Class<E> censusCSVClass) {

        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {

            IOpenCsvBuilder csvBuilder=  CSVBuilderFactory.createCSVBuilder();
            Iterator<E> iterator = csvBuilder.getIterator(reader, censusCSVClass);
            Iterable<E> iterable = () -> iterator;
            if(censusCSVClass.getName().equals("com.bridgelabz.csv.model.IndiaCensusCSV")) {
                StreamSupport.stream(iterable.spliterator(), false).
                        map(IndiaCensusCSV.class::cast).
                        forEach(indianCensus ->
                                censusCSVMap.put(indianCensus.state, new CensusCSVDao(indianCensus)));
                return censusCSVMap.size();
            }
            StreamSupport.stream(iterable.spliterator(), false).
                    map(USCensusCSV.class::cast).
                    forEach(indianCensus ->
                            censusCSVMap.put(indianCensus.stateName, new CensusCSVDao(indianCensus)));
            return censusCSVMap.size();

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

    public int loadUSCensusData(String usCensusCsvFilePath) {
        return this.loadCensusData(usCensusCsvFilePath,USCensusCSV.class);
    }

    public int loadIndianStateData(String csvFilePath) {

        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            IOpenCsvBuilder csvBuilder= CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCode> stateCodeCSVIterator = csvBuilder.getIterator(reader, IndiaStateCode.class);

            int numOfEnteries = this.getCount(stateCodeCSVIterator);
            return numOfEnteries;

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
            censusCSVMap = this.sort();
            return new Gson().toJson(censusCSVMap);


    }

    private  Map sort(){

            return censusCSVMap.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,
                            (oldValue,newValue)->newValue,
                            LinkedHashMap::new));

    }



}
