package dev.abel.springbootdocker.collections.company;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Instrument {

	public String title;
	public Double value;
	public LocalDate periodEnding;
		
}
