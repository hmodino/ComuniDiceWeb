package com.comunidice.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.comunidice.web.util.Actions;
import com.comunidice.web.util.ControllerPaths;
import com.comunidice.web.util.ParameterNames;
import com.comunidice.web.util.RedirectOrForward;
import com.comunidice.web.util.ValidationUtils;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    public SearchServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String type = ValidationUtils.parameterIsEmpty(request, ParameterNames.SEARCH_TYPE);
		String target = null;
		String defaultSearch = ValidationUtils.parameterIsEmpty(request, ParameterNames.DEFAULT);
		if(type.equalsIgnoreCase(ParameterNames.SEARCH_USER)) {
			target = ControllerPaths.NO_CONTEXT_USUARIO.concat("?").concat(ParameterNames.ACTION).concat("=").concat(Actions.SEARCH_USERS);
		}
		if(type.equalsIgnoreCase(ParameterNames.SEARCH_PRODUCT)) {
			target = ControllerPaths.NO_CONTEXT_PRODUCTO.concat("?").concat(ParameterNames.ACTION).concat("=").concat(Actions.SEARCH_PRODUCTS)
					.concat("&").concat(ParameterNames.DEFAULT).concat("=").concat(defaultSearch);
		}
		RedirectOrForward.send(request, response, false, target, true);
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
