package com.comunidice.web.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.comunidice.web.model.Errors;
import com.rollanddice.comunidice.model.Usuario;

public class SetAttribute {
	
	public static final void setErrors(HttpServletRequest request, Errors errors) {
		request.setAttribute(AttributeNames.ERRORS, errors);
	}
	
	public static final void setUser(HttpServletRequest request, Usuario u) {
		request.setAttribute(AttributeNames.USER, u);
	}
	
	public static final void setResults(HttpServletRequest request, List<Object> results) {
		request.setAttribute(AttributeNames.RESULTS, results);
	}
	
	public static final void setResult(HttpServletRequest request, Object o) {
		request.setAttribute(AttributeNames.RESULTS, o);
	}
	
	public static final void setOthers(HttpServletRequest request, String s, Object o) {
		request.setAttribute(s, o);
	}

}
