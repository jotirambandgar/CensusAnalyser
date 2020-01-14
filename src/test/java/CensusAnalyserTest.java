import com.bridgelabz.csv.CensusAnalyser;
import com.bridgelabz.csv.exception.AnalyserException;
import com.bridgelabz.csv.model.IndiaCensusCSV;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_TYPE="./src/main/resources/IndiaStateCensusData.txt";
    private static final String WRONG_CSV_FILE_DELIMITER="./src/test/resources/delimeter.csv";
    private static final String CSV_FILE_INVALID_HEADER="./src/test/resources/InvalidHeader.csv";
    private static final String INDIA_STATECODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_INDIAN_STATECODE_CSV_FILE_PATH = "./src/main/resources/IndiaStateCode.csv";
    private static final String WRONG_INDIAN_STATECODE_CSV_FILE_TYPE="./src/main/resources/IndiaStateCode.txt";
    private static final String WRONG_ISC_CSV_FILE_DELIMITER="./src/test/resources/IscDelimiter.csv";
    private static final String WRONG_ISC_CSV_FILE_INVALID_HEADER="./src/test/resources/IscHeader.csv";
    private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";
    @Test
    public void givenCsvFilePathShouldReturnProperRecods() {
        CensusAnalyser csvStateCensus = new CensusAnalyser();
        int count = csvStateCensus.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
        Assert.assertEquals(29,count);
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(AnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (AnalyserException e) {
            Assert.assertEquals(AnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileType_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(AnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_TYPE);
        } catch (AnalyserException e) {
            Assert.assertEquals(AnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileData_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_DELIMITER);
        } catch (AnalyserException e) {
            Assert.assertEquals(AnalyserException.ExceptionType.INVALID_DATA,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileHeader_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(AnalyserException.class);
            censusAnalyser.loadIndiaCensusData(CSV_FILE_INVALID_HEADER);
        } catch (AnalyserException e) {
            Assert.assertEquals(AnalyserException.ExceptionType.INVALID_DATA,e.type);
        }
    }

    @Test
    public void givenIndianStateCodeCsvFilePath_ShouldReturnProperRecords() {
        CensusAnalyser csvStateCensus = new CensusAnalyser();
        int count = csvStateCensus.loadIndianStateData(INDIA_STATECODE_CSV_FILE_PATH);
        Assert.assertEquals(37,count);
    }

    @Test
    public void givenIndianStateData_WithWrongFilePath_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(AnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_INDIAN_STATECODE_CSV_FILE_PATH);
        } catch (AnalyserException e) {
            Assert.assertEquals(AnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongFileType_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(AnalyserException.class);
            censusAnalyser.loadIndianStateData(WRONG_INDIAN_STATECODE_CSV_FILE_TYPE);
        } catch (AnalyserException e) {
            Assert.assertEquals(AnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongFileData_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(AnalyserException.class);
            censusAnalyser.loadIndianStateData(WRONG_ISC_CSV_FILE_DELIMITER);
        } catch (AnalyserException e) {
            Assert.assertEquals(AnalyserException.ExceptionType.INVALID_DATA,e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongFileHeader_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(AnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_ISC_CSV_FILE_INVALID_HEADER);
        } catch (AnalyserException e) {
            Assert.assertEquals(AnalyserException.ExceptionType.INVALID_DATA,e.type);
        }
    }

    @Test
    public void whenGivenCensusData_WhenSortedOnState_ShouldReturnSortedResult() {

        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
        String sortedData = censusAnalyser.sortCensusDataStateWise();
        Type indianCsvType = new TypeToken<LinkedHashMap<String, IndiaCensusCSV>>() {}.getType();
        LinkedHashMap<String ,IndiaCensusCSV> sortedCsvData = new Gson().fromJson(sortedData, indianCsvType);
        String firstState =  (String) sortedCsvData.keySet().toArray()[0];
        Assert.assertEquals("Andhra Pradesh", firstState);

    }

    @Test
    public void whenFailedToStoreData_WhenSortedOnState_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedData = censusAnalyser.sortCensusDataStateWise();
            Type indianCsvType = new TypeToken<LinkedHashMap<String, IndiaCensusCSV>>() {}.getType();
            LinkedHashMap<String ,IndiaCensusCSV> sortedCsvData = new Gson().fromJson(sortedData, indianCsvType);
            String firstState =  (String) sortedCsvData.keySet().toArray()[0];
            Assert.assertEquals("Andhra Pradesh", firstState);

        } catch (AnalyserException e){
            Assert.assertEquals(AnalyserException.ExceptionType.NO_CENSUS_DATA,e.type);
        }

    }

    @Test
    public void givenUSCensusData_ShouldReturnCorrectRecords() {

        CensusAnalyser censusAnalyser = new CensusAnalyser();
        int censusDataCount = censusAnalyser.loadUSCensusData(US_CENSUS_CSV_FILE_PATH);
        Assert.assertEquals(51,censusDataCount);

    }
}
