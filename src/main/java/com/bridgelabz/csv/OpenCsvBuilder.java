package com.bridgelabz.csv;

import com.bridgelabz.csv.exception.CensusAnalyserException;
import com.bridgelabz.csv.pojo.IndiaCensusCSV;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;

public class OpenCsvBuilder {

    public <E> Iterator<E> getIterator(Reader reader, Class<E> csvClass) {

        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
            Iterator<E> CsvIterator = csvToBean.iterator();;
            return CsvIterator;
        } catch (RuntimeException e){
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INVALID_DATA);
        }
    }

}
