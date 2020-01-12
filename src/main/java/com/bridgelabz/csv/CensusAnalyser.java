package com.bridgelabz.csv;

import com.bridgelabz.csv.exception.AnalyserException;
import com.bridgelabz.csv.exception.CSVBuilderException;
import com.bridgelabz.csv.factory.CSVBuilderFactory;
import com.bridgelabz.csv.pojo.IndiaCensusCSV;
import com.bridgelabz.csv.pojo.IndiaStateCode;

import java.io.IOException;
import java.io.Reader;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    public int loadCensusData(String csvFilePath)    {

        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            IOpenCsvBuilder csvBuilder= CSVBuilderFactory.createCSVBuilder();
            List<IndiaCensusCSV> censusCSVList = csvBuilder.getFileList(reader, IndiaCensusCSV.class);

            return censusCSVList.size();

        } catch (IOException e) {

                throw new AnalyserException(e.getMessage(),
                        AnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);

        } catch (CSVBuilderException e){

            throw new AnalyserException(e.getMessage(),
                    e.type.name());

        }


    }

    public int loadIndianStateData(String csvFilePath) {

        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            IOpenCsvBuilder csvBuilder=CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCode> stateCodeCSVIterator = csvBuilder.getIterator(reader, IndiaStateCode.class);

            int numOfEnteries = this.getCount(stateCodeCSVIterator);
            return numOfEnteries;

        } catch (IOException e) {

            throw new AnalyserException(e.getMessage(),
                    AnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);

        } catch (CSVBuilderException e){

            throw new AnalyserException(e.getMessage(),
                    e.type.name());

        }
        catch (RuntimeException e){

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

}
