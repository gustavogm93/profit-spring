package dev.abel.springbootdocker.collections.region;

import dev.abel.springbootdocker.generics.Property;
import lombok.NonNull;

public class RegionProp extends Property {

	public RegionProp(@NonNull String code, @NonNull String title) {
		super(code, title);
	}

}