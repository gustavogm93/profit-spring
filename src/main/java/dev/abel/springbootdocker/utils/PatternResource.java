package dev.abel.springbootdocker.utils;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class PatternResource {

	public static Pattern getPatternNumber() {
		return Pattern.compile("-?\\d+(\\.\\d+)?");
	}
	
	public static boolean dateStringPattern(String s) {

			String literalMonthRegexp = "(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\s\\d{1,2},\\s(?i)\\d{4}";
			Pattern patter = Pattern.compile(literalMonthRegexp); 
			Matcher matcher = patter.matcher(s);
			return matcher.find();

	}
	

	
}

 