package dev.abel.springbootdocker.collections.share;

import dev.abel.springbootdocker.generics.Property;

import java.util.Comparator;

public class ShareProp extends Property{

	public ShareProp(String code, String title) {
		super(code, title);
	}
	
	public static Comparator<ShareProp> byTitle = Comparator.comparing(ShareProp::getTitle);

}