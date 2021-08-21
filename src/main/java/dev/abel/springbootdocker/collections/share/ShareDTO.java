package dev.abel.springbootdocker.collections.share;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@Data
public class ShareDTO {

	@Id
	private String id;
	
	@Field( "properties")
	@NonNull private ShareProp properties;
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		ShareDTO other = (ShareDTO) obj;

		if (!other.id.equalsIgnoreCase(this.id))
			return false;

		if (!other.properties.getCode().equalsIgnoreCase(this.properties.getCode()))
			return false;
		
		return true;
	}
}
