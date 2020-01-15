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

public class CsvLoader {
    public   <E> Map<String, CensusCSVDao> getCensusData(CensusAnalyser.Country country, String... csvFile) {
        if (country.equals(CensusAnalyser.Country.INDIA)){
            return getCensusData(IndiaCensusCSV.class,csvFile);
        }
        return getCensusData(USCensusCSV.class,csvFile);

    }

    private  <E> Map<String, CensusCSVDao> getCensusData(Class censusCSVClass,String... csvFile){
        Map<String,CensusCSVDao> censusCSVMap = new HashMap<>();
        try(Reader reader = Files.newBufferedReader(Paths.get(csvFile[0]))) {

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
                        forEach(indianCensus ->
                                censusCSVMap.put(indianCensus.stateName, new CensusCSVDao(indianCensus)));
            }
            if (csvFile.length==1)
            return censusCSVMap;
            this.loadIndiaStateCode(censusCSVMap,csvFile[1]);
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

     public <E> int loadIndiaStateCode(Map<String,CensusCSVDao> censusCSVStateMap,String filePath) {
         try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
             IOpenCsvBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
             Iterator<IndiaStateCode> stateCodeCSVIterator = csvBuilder.getIterator(reader, IndiaStateCode.class);
             Iterable<IndiaStateCode> stateIterable = () -> stateCodeCSVIterator;
             StreamSupport.stream(stateIterable.spliterator(), false).filter(stateData -> censusCSVStateMap.get(stateData.stateName) != null)
                     .forEach(stateData -> censusCSVStateMap.get(stateData.stateName).stateCode = stateData.stateCode);
            return censusCSVStateMap.size();
         } catch (IOException e) {

             throw new AnalyserException(e.getMessage(),
                     AnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);

         } catch (CSVBuilderException e) {

             throw new AnalyserException(e.getMessage(),
                     e.type.name());

         } catch (RuntimeException e) {

             throw new AnalyserException(e.getMessage(),
                     AnalyserException.ExceptionType.INVALID_DATA);
         }
     }

}