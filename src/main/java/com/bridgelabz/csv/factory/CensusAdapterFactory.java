package com.bridgelabz.csv.factory;

import com.bridgelabz.csv.CensusAnalyser;
import com.bridgelabz.csv.exception.AnalyserException;
import com.bridgelabz.csv.model.CensusCSVDao;
import com.bridgelabz.csv.service.IndiaCensusAdapter;
import com.bridgelabz.csv.service.USCensusAdapter;

import java.rmi.MarshalledObject;
import java.util.Map;

public class CensusAdapterFactory {

    public static Map<String , CensusCSVDao> getCensusData(CensusAnalyser.Country country, String... csvFile){
        if(country.equals(CensusAnalyser.Country.INDIA))
           return new IndiaCensusAdapter().getCensusData(csvFile);

         if(country.equals(CensusAnalyser.Country.US))
            return new USCensusAdapter().getCensusData(csvFile);

         throw new AnalyserException("Invalid Countery",AnalyserException.ExceptionType.INVALID_COUNTRT);

    }
}
