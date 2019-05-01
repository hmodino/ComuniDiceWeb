package com.comunidice.web.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.comunidice.web.util.Actions;
import com.comunidice.web.util.ControllerPaths;
import com.comunidice.web.util.ParameterNames;
import com.comunidice.web.util.ParameterUtils;
import com.comunidice.web.util.RedirectOrForward;
import com.comunidice.web.util.ValidationUtils;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    public SearchServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = ValidationUtils.parameterIsEmpty(request, ParameterNames.ACTION);
		String target = null;
		String defaultSearch = null;
		String url = ValidationUtils.parameterIsEmpty(request, ParameterNames.URL);
		Map <String, String> urlMap = null;
		
		if(Actions.SEARCH_USERS.equalsIgnoreCase(action)) {
			urlMap = new HashMap<String, String>();
			urlMap.put(ParameterNames.ACTION, Actions.SEARCH_USERS);
			target = ParameterUtils.URLBuilder(ControllerPaths.NO_CONTEXT_USUARIO, urlMap);
		}
		else if(Actions.SEARCH_PRODUCTS.equalsIgnoreCase(action)) {
			
			if(url==null) {
				urlMap = new HashMap<String, String>();
				urlMap.put(ParameterNames.ACTION, Actions.SEARCH_PRODUCTS);
				urlMap.put(ParameterNames.DEFAULT, "false");
				target = ParameterUtils.URLBuilder(ControllerPaths.NO_CONTEXT_PRODUCTO, urlMap);
			} else {
				target = url;
			}
		} else if(!Actions.SEARCH_USERS.equalsIgnoreCase(action)
				&& !Actions.SEARCH_USERS.equalsIgnoreCase(action)) {
			
			urlMap = new HashMap<String, String>();
			urlMap.put(ParameterNames.ACTION, Actions.SEARCH_PRODUCTS);
			urlMap.put(ParameterNames.DEFAULT, "true");
			urlMap.put(ParameterNames.CATEGORY_ID, "3");
			target = ParameterUtils.URLBuilder(ControllerPaths.NO_CONTEXT_PRODUCTO, urlMap);
		}
		RedirectOrForward.send(request, response, false, target, true);
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
