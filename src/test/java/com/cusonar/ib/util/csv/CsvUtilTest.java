package com.cusonar.ib.util.csv;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.assertj.core.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CsvUtilTest {
	
	String csvFilePath = "./test.csv";
	
	String[] expectedFirstRow = {"1", "2", "3"};
	String[][] expectedOtherRow = {{"4", "5", "6"},{"7", "8", "9"}};
	
	File csvFile;
	
	@Before
	public void setup() throws IOException {
	    csvFile = new File(csvFilePath);
	    FileWriter fw = new FileWriter(csvFile);
	    fw.write("1,2,3\n");
	    fw.write("4,5,6\n");
	    fw.write("7,8,9\n");
	    fw.close();
	}
	
	@After
	public void cleanup() {
	    csvFile.deleteOnExit();
	}
	
	
	@Test
	public void readAndDoSomethingTest() throws FileNotFoundException, IOException {
		CsvUtil util = new CsvUtil(csvFilePath);
		util.readAndDoSomething(values -> {
			assertArrayEquals(expectedFirstRow, values);
		}, values -> {
			assertThat(Arrays.asList(expectedOtherRow), hasItem(values));
		});
	}
}
