package com.bridgelabz.csv.exception;

public class CSVBuilderException extends RuntimeException {

    public enum ExceptionType {
        CENSUS_FILE_PROBLEM,
        INVALID_DATA
    }

    public ExceptionType type;

    public CSVBuilderException(String message, CSVBuilderException.ExceptionType type) {
        super(message);
        this.type = type;
    }

}
