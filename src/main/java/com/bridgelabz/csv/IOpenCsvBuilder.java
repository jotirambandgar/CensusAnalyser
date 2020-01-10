package com.bridgelabz.csv;

import java.io.Reader;
import java.util.Iterator;

public interface IOpenCsvBuilder {
    public <E> Iterator<E> getIterator(Reader reader, Class<E> csvClass);
}
