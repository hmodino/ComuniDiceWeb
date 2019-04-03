package com.comunidice.web.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.mysql.cj.util.StringUtils;

public class ValidationUtils {
	
	public static final String getParameter(HttpServletRequest request, String name) {		
		String value = (String) request.getParameter(name);							
		if (value==null) {
			value = "";
		}else {
			value = value.trim();
		}
		return value;
	}
	
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
	
	public static final Integer parseIntParameter(HttpServletRequest request, String name) {
		
		String s = getParameter(request, name);
		
		if(!StringUtils.isEmptyOrWhitespaceOnly(s)) {
			Integer i = Integer.parseInt(s.trim());
			return i;
		}else {
		return null;
		}
	}
	
	public static final String parameterIsEmpty(HttpServletRequest request, String name) {
		
		String s = getParameter(request, name);
		
		if(!StringUtils.isEmptyOrWhitespaceOnly(s)) {
			s=s.trim();
			return s;
		}else {
			return null;
		}
	}
	
	public static Date parameterDateFormat(HttpServletRequest request, String name) {
		
		String s = getParameter(request, name);
		
		DateFormat format = new SimpleDateFormat("yyyy");
		if(s!=null && !StringUtils.isEmptyOrWhitespaceOnly(s)) {
			Date date;
			try {
				date = format.parse(s);
				return date;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}

		}else {
			return null;
		}
	}
	
	public static final Double parseDoubleParameter(HttpServletRequest request, String name) {
		
		String s = getParameter(request, name);
		
		if(!StringUtils.isEmptyOrWhitespaceOnly(s)) {
			String[] split = s.split(",");
			if(split.length>1 && split.length<3) {
				s = split[0]+"."+split[1];
				Double i = Double.parseDouble(s.trim());
				return i;
			}else if(split.length==1) {
				Double i = Double.parseDouble(s.trim());
				return i;
			}else {
				return null;
			}
		}else {
		return null;
		}
	}

}
