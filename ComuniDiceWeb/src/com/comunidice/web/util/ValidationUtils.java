package com.comunidice.web.util;

import com.mysql.cj.util.StringUtils;

public class ValidationUtils {
	
	public static final Integer parseInt(String s) {
		
		if(StringUtils.isEmptyOrWhitespaceOnly(s)) {
			Integer i = Integer.parseInt(s.trim());
			return i;
		}
		return null;
	}

}
