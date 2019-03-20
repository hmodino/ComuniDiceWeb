package com.comunidice.web.util;

import com.mysql.cj.util.StringUtils;

public class ValidationUtils {
	
	public static final Integer parseInt(String s) {
		
		if(!StringUtils.isEmptyOrWhitespaceOnly(s)) {
			Integer i = Integer.parseInt(s.trim());
			return i;
		}else {
		return null;
		}
	}
	
	public static final String isEmpty(String s) {
		
		if(!StringUtils.isEmptyOrWhitespaceOnly(s)) {
			s=s.trim();
			return s;
		}else {
			return null;
		}
	}

}
