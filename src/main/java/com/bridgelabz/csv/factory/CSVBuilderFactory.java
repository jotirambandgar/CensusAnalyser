package com.bridgelabz.csv.factory;

import com.bridgelabz.csv.IOpenCsvBuilder;
import com.bridgelabz.csv.OpenCsvBuilderImpl;

public class CSVBuilderFactory<E> {
    public static IOpenCsvBuilder createCSVBuilder() {
        return new OpenCsvBuilderImpl();
    }
}
