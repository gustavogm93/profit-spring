package dev.abel.springbootdocker.collections.country;

import dev.abel.springbootdocker.generics.Property;
import lombok.NonNull;

public class CountryProp extends Property{


	private static final long serialVersionUID = 1L;

	public CountryProp(@NonNull String code, @NonNull String title) {
		super(code, title);
	}
	

}