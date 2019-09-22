package com.cusonar.ib.util.csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CsvUtil {
	
    private static final String COMMA_DELIMITER = ",";
	private String csvPath;
	
	public CsvUtil(String filepath) {
		csvPath = filepath;
	}
	
	public void readAndDoSomething(
			CsvFirstRowAction firstRowAction,
			CsvOtherRowAction otherRowAction) throws FileNotFoundException, IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
	          String line;
	          line = br.readLine();
	          
	          String[] values = line.split(COMMA_DELIMITER);
	          firstRowAction.action(values);
	          
	          while ((line = br.readLine()) != null) {
	              values = line.split(COMMA_DELIMITER);
	              otherRowAction.action(values);
	          }          
	    }
	}
}