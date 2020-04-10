package com.bridgelabz.csv.adapter;

import com.bridgelabz.csv.model.CensusCSVDao;
import com.bridgelabz.csv.model.USCensusCSV;

import java.util.Map;

public class USCensusAdapter extends CensusAdapter {
    @Override
    public Map<String, CensusCSVDao> getCensusData(String... csvFile) {
        return super.getCensusData(USCensusCSV.class,csvFile[0]);
    }
}
