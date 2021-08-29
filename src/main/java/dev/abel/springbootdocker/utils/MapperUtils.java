package dev.abel.springbootdocker.utils;

import java.time.LocalDate;

public interface MapperUtils {

	public LocalDate toDate(String date);

	public LocalDate convertToDateYYYY_MM_DD(String year, String month);

	public double stringToNum(String s);

	public String addFormat(String old, String next);

	public String generateUrl(String title);
}
