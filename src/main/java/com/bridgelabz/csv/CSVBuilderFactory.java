package com.bridgelabz.csv;

public class CSVBuilderFactory {
    public static IOpenCsvBuilder createCSVBuilder() {
        return new OpenCsvBuilderImpl();
    }
}
