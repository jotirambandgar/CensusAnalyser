package com.bridgelabz.csv;

import com.bridgelabz.csv.exception.CensusAnalyserException;
import com.bridgelabz.csv.pojo.IndiaCensusCSV;
import com.bridgelabz.csv.pojo.IndiaStateCode;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;
import java.io.Reader;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    public int loadCensusData(String csvFilePath) {

        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {

            CsvToBeanBuilder<IndiaCensusCSV> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(IndiaCensusCSV.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<IndiaCensusCSV> csvToBean = csvToBeanBuilder.build();
            Iterator<IndiaCensusCSV> censusCSVIterator = csvToBean.iterator();
            Iterable<IndiaCensusCSV> censusCSVIterable = ()-> censusCSVIterator;
            int numOfEnteries = (int) StreamSupport.stream(censusCSVIterable.spliterator(),false).count();
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
            CsvToBeanBuilder<IndiaStateCode> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(IndiaStateCode.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<IndiaStateCode> csvToBean = csvToBeanBuilder.build();
            Iterator<IndiaStateCode> censusCSVIterator = csvToBean.iterator();;
            Iterable<IndiaStateCode> censusCSVIterable = ()-> censusCSVIterator;
            int numOfEnteries = (int) StreamSupport.stream(censusCSVIterable.spliterator(),false).count();
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

}
