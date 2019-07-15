package com.yyt.UniAuth.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {
	
	public static boolean isNumber(String input) {
		String regPattern = "^[0-9]*[1-9][0-9]*$";
		Pattern pattern = Pattern.compile(regPattern);
		Matcher matcher = pattern.matcher(input);
		if(matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}

}
