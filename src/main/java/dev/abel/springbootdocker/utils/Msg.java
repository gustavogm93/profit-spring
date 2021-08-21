package dev.abel.springbootdocker.utils;

import dev.abel.springbootdocker.generics.Property;

public class Msg {

	
	public final static String executor = "Starting Chrome Driver...";
	public final static String setUpDriver = "Set up Chrome Driver...";
	public final static String startDriver = "Starting Chrome Driver...";
	public final static String startDriverSuccess = "Chrome start success";
	
	public final static String fetchingAllRegions = "Getting all regions from...";
	public final static String fetchingSpecificRegion = "Getting # regions...";
	
	public final static String fetchingCountry = "Getting country...";
	public final static String saveCountry = "Start process of Save country";
	
	public final static String fetchingMarketIndex = "Getting to market Index List...";
	public final static String saveMarketIndex = "Start process of Save country";
	
	public final static String fetchingShares = "Getting to shares...";
	public final static String saveShares = "Save shares...";
	
	public static < T extends Property > String compound(T object, String msg) {

		return String.format(("%s: %s"),msg, object.getTitle() );
	}
	
	
	public static < T extends Property > String error(T object, String msg) {

		return String.format(("Error ocurred in country: %s: %s"),object.getTitle(),msg);
	}
	
	

	
}
