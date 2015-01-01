package com.illucit.partyinvoice;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test currency conversion.
 * 
 * @author Christian Simon
 *
 */
public class CurrencyTest {

	@Test
	public void testCurrencyToString() {
		Assert.assertEquals("0,00 €", CurrencyUtil.currencyToString(0));
		Assert.assertEquals("0,01 €", CurrencyUtil.currencyToString(1));
		Assert.assertEquals("0,02 €", CurrencyUtil.currencyToString(2));
		Assert.assertEquals("0,03 €", CurrencyUtil.currencyToString(3));
		Assert.assertEquals("0,10 €", CurrencyUtil.currencyToString(10));
		Assert.assertEquals("0,11 €", CurrencyUtil.currencyToString(11));
		Assert.assertEquals("0,99 €", CurrencyUtil.currencyToString(99));
		Assert.assertEquals("1,00 €", CurrencyUtil.currencyToString(100));
		Assert.assertEquals("1,01 €", CurrencyUtil.currencyToString(101));
		Assert.assertEquals("1,10 €", CurrencyUtil.currencyToString(110));
		Assert.assertEquals("2,00 €", CurrencyUtil.currencyToString(200));
		Assert.assertEquals("19,99 €", CurrencyUtil.currencyToString(1999));
		Assert.assertEquals("379,31 €", CurrencyUtil.currencyToString(37931));

		Assert.assertEquals("0,00 €", CurrencyUtil.currencyToString(-0));
		Assert.assertEquals("-0,01 €", CurrencyUtil.currencyToString(-1));
		Assert.assertEquals("-0,02 €", CurrencyUtil.currencyToString(-2));
		Assert.assertEquals("-0,03 €", CurrencyUtil.currencyToString(-3));
		Assert.assertEquals("-0,10 €", CurrencyUtil.currencyToString(-10));
		Assert.assertEquals("-0,11 €", CurrencyUtil.currencyToString(-11));
		Assert.assertEquals("-0,99 €", CurrencyUtil.currencyToString(-99));
		Assert.assertEquals("-1,00 €", CurrencyUtil.currencyToString(-100));
		Assert.assertEquals("-1,01 €", CurrencyUtil.currencyToString(-101));
		Assert.assertEquals("-1,10 €", CurrencyUtil.currencyToString(-110));
		Assert.assertEquals("-2,00 €", CurrencyUtil.currencyToString(-200));
		Assert.assertEquals("-19,99 €", CurrencyUtil.currencyToString(-1999));
		Assert.assertEquals("-379,31 €", CurrencyUtil.currencyToString(-37931));
	}

	@Test
	public void testCurrencyFromString() {
		Assert.assertEquals(0, CurrencyUtil.stringToCurrency("0,00 €"));
		Assert.assertEquals(1, CurrencyUtil.stringToCurrency("0,01 €"));
		Assert.assertEquals(2, CurrencyUtil.stringToCurrency("0,02 €"));
		Assert.assertEquals(3, CurrencyUtil.stringToCurrency("0,03 €"));
		Assert.assertEquals(10, CurrencyUtil.stringToCurrency("0,10 €"));
		Assert.assertEquals(11, CurrencyUtil.stringToCurrency("0,11 €"));
		Assert.assertEquals(99, CurrencyUtil.stringToCurrency("0,99 €"));
		Assert.assertEquals(100, CurrencyUtil.stringToCurrency("1,00 €"));
		Assert.assertEquals(101, CurrencyUtil.stringToCurrency("1,01 €"));
		Assert.assertEquals(110, CurrencyUtil.stringToCurrency("1,10 €"));
		Assert.assertEquals(200, CurrencyUtil.stringToCurrency("2,00 €"));
		Assert.assertEquals(1999, CurrencyUtil.stringToCurrency("19,99 €"));
		Assert.assertEquals(37931, CurrencyUtil.stringToCurrency("379,31 €"));

		Assert.assertEquals(0, CurrencyUtil.stringToCurrency("-0,00 €"));
		Assert.assertEquals(-1, CurrencyUtil.stringToCurrency("-0,01 €"));
		Assert.assertEquals(-2, CurrencyUtil.stringToCurrency("-0,02 €"));
		Assert.assertEquals(-3, CurrencyUtil.stringToCurrency("-0,03 €"));
		Assert.assertEquals(-10, CurrencyUtil.stringToCurrency("-0,10 €"));
		Assert.assertEquals(-11, CurrencyUtil.stringToCurrency("-0,11 €"));
		Assert.assertEquals(-99, CurrencyUtil.stringToCurrency("-0,99 €"));
		Assert.assertEquals(-100, CurrencyUtil.stringToCurrency("-1,00 €"));
		Assert.assertEquals(-101, CurrencyUtil.stringToCurrency("-1,01 €"));
		Assert.assertEquals(-110, CurrencyUtil.stringToCurrency("-1,10 €"));
		Assert.assertEquals(-200, CurrencyUtil.stringToCurrency("-2,00 €"));
		Assert.assertEquals(-1999, CurrencyUtil.stringToCurrency("-19,99 €"));
		Assert.assertEquals(-37931, CurrencyUtil.stringToCurrency("-379,31 €"));
	}

}
