package com.comunidice.web.util;

import javax.servlet.http.HttpServletRequest;

public class WebUtils {
	
	public static final Integer getPage(HttpServletRequest request, String name) {
		
		String s = ValidationUtils.getParameter(request, name);
		Integer page = ValidationUtils.parseInt(s);
		
		if (page==null) {
			page = 1;
		}
		return page;
	}

	public static final Integer getPageSize(HttpServletRequest request, String name) {
		
		String s = ValidationUtils.getParameter(request, name);
		Integer size = ValidationUtils.parseInt(s);
		
		if(size==null) {
			size = 5;
		}
		return size;
	}
}
