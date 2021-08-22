package dev.abel.springbootdocker.generics;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Comparator;

@Data
public class Property {

		@Field("code")
		@NonNull private final String code;
		
		@Field( "title")
		@NonNull private final String title;

		public static Comparator<Property> byTitle = Comparator.comparing(Property::getTitle);
		public static Comparator<Property> byCode = Comparator.comparing(Property::getCode);
		
		
	
}
