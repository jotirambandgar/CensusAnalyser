package com.bridgelabz.csv;

import com.bridgelabz.csv.exception.CSVBuilderException;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public interface IOpenCsvBuilder<E> {

    public  Iterator<E> getIterator(Reader reader, Class csvClass) throws CSVBuilderException;

    public  List<E> getFileList(Reader reader, Class csvClass) throws CSVBuilderException ;
}
