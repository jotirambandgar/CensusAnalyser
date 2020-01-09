package com.bridgelabz.csv.exception;

public class CensusAnalyserException extends RuntimeException {

    public enum ExceptionType {
        CENSUS_FILE_PROBLEM,
         INVALID_DATA
    }

    public ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CensusAnalyserException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
