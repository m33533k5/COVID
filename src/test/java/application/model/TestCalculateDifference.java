package application.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestCalculateDifference {
	
	@Test
	public void testGetDiffAnfang() {
		assertEquals(309l, new CalculateDifference(5,2020).getDiffStart());
		assertEquals(309, new CalculateDifference(5,2020).getDiffStart());
	}
	
	@Test
	public void testGetDiffEnde() {
		assertEquals(279l, new CalculateDifference(5,2020).getDiffEnd());
		assertEquals(279, new CalculateDifference(5,2020).getDiffEnd());
	}
}
