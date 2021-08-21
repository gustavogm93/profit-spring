package dev.abel.springbootdocker.enums;

import com.google.common.collect.ImmutableList;

import java.util.Arrays;

public enum RegionConstant {

	 AMERICA("1", "America"),
	 EUROPE("2", "Europe"),
	 ASIA_PACIFIC("3", "Asia-Pacific"),
	 MIDDLE_EAST("4", "Middle-East"),
	 AFRICA("5", "Africa");
	
	public static final ImmutableList<RegionConstant> values = ImmutableList.copyOf(Arrays.asList(RegionConstant.values()));
	
	private final String code;
	
	private final String title;
	
	public String getCode() {
		return this.code;
	}
	public String getTitle() {
		return this.title;
	}
	private RegionConstant(String code, String title) {
		this.code = code;
		this.title = title;
	}
}
