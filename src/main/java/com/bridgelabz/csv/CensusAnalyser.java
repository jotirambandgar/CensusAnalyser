package com.bridgelabz.csv;

import com.bridgelabz.csv.exception.AnalyserException;
import com.bridgelabz.csv.exception.CSVBuilderException;
import com.bridgelabz.csv.factory.CSVBuilderFactory;
import com.bridgelabz.csv.model.IndiaCensusCSV;
import com.bridgelabz.csv.model.IndiaCensusCSVDao;
import com.bridgelabz.csv.model.IndiaStateCode;
import com.bridgelabz.csv.repository.IOpenCsvBuilder;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    List<IndiaCensusCSVDao> censusCSVList = null;

    public CensusAnalyser() {
        this.censusCSVList = new ArrayList<>();
    }

    public int loadCensusData(String csvFilePath)    {

        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            IOpenCsvBuilder csvBuilder= CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> iterator = csvBuilder.getIterator(reader, IndiaCensusCSV.class);
            while (iterator.hasNext()){
                censusCSVList.add(new IndiaCensusCSVDao(iterator.next()));
            }

            return censusCSVList.size();

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

            if (censusCSVList.size() == 0 || censusCSVList == null){
                throw new AnalyserException("No Census Data",
                        AnalyserException.ExceptionType.NO_CENSUS_DATA);
            }
            censusCSVList = this.sort();
            return new Gson().toJson(censusCSVList);


    }

    private  List sort(){

            return censusCSVList.stream()
                    .sorted((census1,census2)->census1.state.compareTo(census2.state))
                    .collect(Collectors.toList());

    }


}
