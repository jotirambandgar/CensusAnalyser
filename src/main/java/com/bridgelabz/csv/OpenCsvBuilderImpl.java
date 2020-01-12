package com.bridgelabz.csv;

import com.bridgelabz.csv.exception.AnalyserException;
import com.bridgelabz.csv.exception.CSVBuilderException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;


public class OpenCsvBuilderImpl<E> implements IOpenCsvBuilder {

    @Override
    public  Iterator getIterator(Reader reader, Class csvClass)throws CSVBuilderException  {
        try {
            CsvToBean<E> csvToBean = this.getCsvBean(reader, csvClass);

            return csvToBean.iterator();
        }catch (RuntimeException e){

            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.INVALID_DATA);
        }

    }


    @Override
    public List getFileList(Reader reader, Class csvClass) throws CSVBuilderException {
        try {
            return this.getCsvBean(reader, csvClass).parse();
        }catch (RuntimeException e){
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.INVALID_DATA);
        }

    }

    private  CsvToBean<E> getCsvBean(Reader reader, Class csvClass) throws CSVBuilderException {
        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            return csvToBeanBuilder.build();

        } catch (RuntimeException e){
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.INVALID_DATA);
        }
    }

}
