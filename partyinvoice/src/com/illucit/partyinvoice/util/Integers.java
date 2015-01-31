package com.illucit.partyinvoice.util;

/**
 * Integer utilities.
 * 
 * @author Christian Simon
 *
 */
public class Integers {

	/**
	 * Convert (nullable) Integer value to non-null int value.
	 * 
	 * @param value
	 *            input value
	 * @return value or 0, if value was null
	 */
	public static int nullToZero(Integer value) {
		return value == null ? 0 : value;
	}

	/**
	 * Convert non-null int to nullable Integer.
	 * 
	 * @param value
	 *            input value
	 * @return value or null, if value was 0
	 */
	public static Integer zeroToNull(int value) {
		return value == 0 ? null : value;
	}

	/**
	 * Try to parse integer. Return null, if not possible.
	 * 
	 * @param value
	 *            value to parse
	 * @return parsed integer or null
	 */
	public static Integer parseIntOrNull(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}

}
