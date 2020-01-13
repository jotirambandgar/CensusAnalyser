package com.bridgelabz.csv.exception;

public class AnalyserException extends RuntimeException {



    public enum ExceptionType {
        CENSUS_FILE_PROBLEM,
         INVALID_DATA,
        NO_CENSUS_DATA;
    }

    public ExceptionType type;

    public AnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public AnalyserException(String message, String name) {
        super(message);
        this.type = ExceptionType.valueOf(name);
    }

    public AnalyserException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }

}
