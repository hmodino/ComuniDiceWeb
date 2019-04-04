package com.comunidice.web.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectOrForward {
	
	public static final void send(HttpServletRequest request, HttpServletResponse response,
			Boolean redirect, String target, Boolean send) throws ServletException, IOException {
		
		if(send) {
			StringBuilder s = new StringBuilder();
			s.append(request.getContextPath()).append(target);
			
			if(redirect) {
				response.sendRedirect(s.toString());
			}
			if(!redirect) {
				request.getRequestDispatcher(target).forward(request, response);
			}
		}
	}
}
