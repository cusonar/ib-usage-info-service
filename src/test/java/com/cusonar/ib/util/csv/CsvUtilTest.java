package com.cusonar.ib.util.csv;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.assertj.core.util.Arrays;
import org.junit.Test;

public class CsvUtilTest {
	
	String testCsvFilePath = "data/2019년하반기_서버개발자_데이터.csv";
	
	String[] expectedFirstRow = {"기간","이용률","스마트폰","데스크탑 컴퓨터","노트북 컴퓨터","기타","스마트패드"};
	String[][] expectedOtherRow = {{"2011","52.9","26.3","95.1","14.3","9.5","-"},
			{"2012","53.3","33.5","93.9","13.1","1.9","-"},
			{"2013","53.4","64.3","67.1","14.7","4.4","-"},
			{"2014","57.2","64.2","61.5","14.1","0.1","0.9"},
			{"2015","60.3","73.2","61.9","13.9","0.2","2.3"},
			{"2016","63.2","85.1","58.5","19.3","0.2","2.1"},
			{"2017","68","90.6","61.4","10.8","0.2","2"},
			{"2018","68.7","90.5","51.2","17.3","0.3","3.3"}};
	
	@Test
	public void readAndDoSomethingTest() throws FileNotFoundException, IOException {
		CsvUtil util = new CsvUtil(testCsvFilePath);
		util.readAndDoSomething(values -> {
			assertArrayEquals(expectedFirstRow, values);
		}, values -> {
			assertThat(Arrays.asList(expectedOtherRow), hasItem(values));
		});
	}
}
