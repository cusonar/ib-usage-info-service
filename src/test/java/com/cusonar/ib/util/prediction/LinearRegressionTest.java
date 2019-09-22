package com.cusonar.ib.util.prediction;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LinearRegressionTest {

	@Test
	public void predictTest() {
		Double[] x = {1.0, 2.0, 3.0};
		Double[] y = {2.0, 4.0, 6.0};
		
		LinearRegression lr = new LinearRegression(x, y);
		Double result = lr.predict(4.0);
		assertEquals(new Double(8.0), result);
	}
}
