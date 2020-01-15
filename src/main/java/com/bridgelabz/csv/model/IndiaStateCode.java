package com.bridgelabz.csv.model;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCode {

    @CsvBindByName(column = "SrNo" , required = true)
    public int srNo;
    @CsvBindByName(column = "State Name" , required = true)
    public String stateName;
    @CsvBindByName(column = "TIN" , required = true)
    public int tin;
    @CsvBindByName(column = "StateCode" , required = true)
    public String stateCode;

    @Override
    public String toString() {
        return "IndiaStateCode{" +
                "srNo=" + srNo +
                ", stateName='" + stateName + '\'' +
                ", tin='" + tin + '\'' +
                ", stateCode='" + stateCode + '\'' +
                '}';
    }
}
