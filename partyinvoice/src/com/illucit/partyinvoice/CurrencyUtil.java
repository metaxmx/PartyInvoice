package com.illucit.partyinvoice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class to convert longs used as currency to readable form and back.
 * 
 * @author Christian Simon
 *
 */
public class CurrencyUtil {

	public static final char MINUS = '-';

	public static final char SPACE = ' ';

	public static final char COMMA = ',';

	public static final char CURRENCY = '€';

	/**
	 * Convert a long value (denoting the cents) to a String value.
	 * 
	 * @param cents
	 *            cents values
	 * @return readable String value (in format "-#####,## €")
	 */
	public static String currencyToString(long cents) {
		StringBuffer result = new StringBuffer();
		if (cents < 0) {
			result.append(MINUS);
		}
		long abs = Math.abs(cents);
		long major = abs / 100;
		int minor = (int) (abs % 100);
		int minor1 = minor / 10;
		int minor2 = minor % 10;
		result.append(major).append(COMMA).append(minor1).append(minor2).append(SPACE).append(CURRENCY);
		return result.toString();
	}

	/**
	 * Convert readable String currency value to long (number of cents). String
	 * must be in format "-#####,## €".
	 * 
	 * @param currencyStr
	 *            input currency String
	 * @return number of cents as long
	 * @throws NumberFormatException
	 *             if String is nor in correct format
	 */
	public static long stringToCurrency(String currencyStr) {
		Pattern p = Pattern.compile("^(\\Q" + MINUS + "\\E)?([0-9]+)\\Q" + COMMA + "\\E([0-9]{2})\\Q" + SPACE
				+ CURRENCY + "\\E$");
		Matcher m = p.matcher(currencyStr);
		if (!m.matches()) {
			throw new NumberFormatException("Invalid format: " + currencyStr);
		}
		String minus = m.group(1);
		String majorStr = m.group(2);
		String minorStr = m.group(3);
		long major = Long.parseLong(majorStr);
		long minor = Long.parseLong(minorStr);
		long abs = (major * 100) + minor;
		return (minus != null) ? -abs : abs;
	}

}
