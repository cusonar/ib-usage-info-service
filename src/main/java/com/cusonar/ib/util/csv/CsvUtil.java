package com.cusonar.ib.util.csv;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class CsvUtil {
	
    private static final String COMMA_DELIMITER = ",";
	private String csvPath;
	
	public CsvUtil(String filepath) {
		csvPath = filepath;
	}
	
	public void readAndDoSomething(
			CsvFirstRowAction firstRowAction,
			CsvOtherRowsAction otherRowsAction) throws FileNotFoundException, IOException {
	    if (!csvPath.endsWith(".csv")) throw new IllegalArgumentException("csv 파일만 지원합니다.");
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(csvPath), "UTF8"))) {
	          String line;
	          line = br.readLine();
	          
	          String[] values = line.split(COMMA_DELIMITER);
	          firstRowAction.action(values);
	          
	          while ((line = br.readLine()) != null) {
	              values = line.split(COMMA_DELIMITER);
	              otherRowsAction.action(values);
	          }          
	    }
	}
}
