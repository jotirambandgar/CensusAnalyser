package com.bridgelabz.csv;

import com.bridgelabz.csv.exception.CensusAnalyserException;
import com.bridgelabz.csv.pojo.IndiaCensusCSV;
import com.bridgelabz.csv.pojo.IndiaStateCode;

import java.io.IOException;
import java.io.Reader;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    public int loadCensusData(String csvFilePath) {

        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            IOpenCsvBuilder csvBuilder=CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> censusCSVIterator = csvBuilder.getIterator(reader, IndiaCensusCSV.class);
            int numOfEnteries = this.getCount(censusCSVIterator);
            return numOfEnteries;

        } catch (IOException e) {

                throw new CensusAnalyserException(e.getMessage(),
                        CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);

        }
        catch (RuntimeException e){

            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INVALID_DATA);

        }

    }

    public int loadIndianStateData(String csvFilePath) {

        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            IOpenCsvBuilder csvBuilder=CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCode> csvIterator = csvBuilder.getIterator(reader,IndiaStateCode.class);
            int numOfEnteries = this.getCount(csvIterator);
            return numOfEnteries;

        } catch (IOException e) {

            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);

        }

    }



    private <E> int getCount(Iterator<E> iterator) {

        Iterable<E> CSVIterable = ()-> iterator;
        int numOfEnteries = (int) StreamSupport.stream(CSVIterable.spliterator(),false).count();
        return numOfEnteries;

    }

}
