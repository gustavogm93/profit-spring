package dev.abel.springbootdocker.utils;

import java.util.Locale;

public interface Validates {
	
	/**
	 * Returns {@code boolean} value if {@code String} object is null
	 *  or can become into a {@code T extends Number} 
	 * @param value of string {@code String}
	 * @return the {@code boolean}.
	 */
	public boolean isNumOrEmpty(String strNum);
	
	public boolean isString(String str);

	/**
	 * check if value {@code isSummaryObject(str)} OR {@code NumOrEmpty()}; 
	 * @param string value
	 * @param summaryType st
	 * @return returns {@code boolean}.
	 */ 
	public boolean isSummaryModelValue(String str);
	/**
	 * check if value {@code Summary<T>.getTitle().equalsIgnoreCase(str)}
	 * @param string value
	 * @param summaryType st
	 * @return returns {@code boolean}.
	 */ 
	public boolean isSummaryObject(String str);

	public boolean isValidRegion(String region);

	public boolean isDate(String s);
	
	public void stringIsNumeric(String id) throws NumberFormatException;
}
