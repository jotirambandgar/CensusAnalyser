package com.bridgelabz.csv.adapter;

import com.blsolution.exception.CSVBuilderException;
import com.blsolution.factory.CSVBuilderFactory;
import com.blsolution.repository.IOpenCsvBuilder;
import com.bridgelabz.csv.exception.AnalyserException;
import com.bridgelabz.csv.model.CensusCSVDao;
import com.bridgelabz.csv.model.IndiaCensusCSV;
import com.bridgelabz.csv.model.IndiaStateCode;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class IndiaCensusAdapter extends CensusAdapter{

//    public   <E> Map<String, CensusCSVDao> getCensusData(CensusAnalyser.Country country, String... csvFile) {
//        if (country.equals(CensusAnalyser.Country.INDIA)){
//            return getCensusData(IndiaCensusCSV.class,csvFile);
//        }
//        return getCensusData(USCensusCSV.class,csvFile);
//
//    }



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



    @Override
    public Map<String, CensusCSVDao> getCensusData(String... csvFile) {
        return super.getCensusData(IndiaCensusCSV.class,csvFile[0]);
    }
}