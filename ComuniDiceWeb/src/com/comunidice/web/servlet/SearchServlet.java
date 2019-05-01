package com.comunidice.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.comunidice.web.util.Actions;
import com.comunidice.web.util.ControllerPaths;
import com.comunidice.web.util.ParameterNames;
import com.comunidice.web.util.RedirectOrForward;
import com.comunidice.web.util.SetAttribute;
import com.comunidice.web.util.ValidationUtils;
import com.comunidice.web.util.ViewPaths;
import com.rollanddice.comunidice.exception.DataException;
import com.rollanddice.comunidice.exception.ServiceException;
import com.rollanddice.comunidice.model.Categoria;
import com.rollanddice.comunidice.service.impl.CategoriaServiceImpl;
import com.rollanddice.comunidice.service.spi.CategoriaService;

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
		
		if(Actions.SEARCH_USERS.equalsIgnoreCase(action)) {
			target = ControllerPaths.NO_CONTEXT_USUARIO.concat("?").concat(ParameterNames.ACTION).concat("=").concat(Actions.SEARCH_USERS);
		}
		else if(Actions.SEARCH_PRODUCTS.equalsIgnoreCase(action)) {
			if(url==null) {
				target = ControllerPaths.NO_CONTEXT_PRODUCTO.concat("?").concat(ParameterNames.ACTION).concat("=").concat(Actions.SEARCH_PRODUCTS)
					.concat("&").concat(ParameterNames.DEFAULT).concat("=").concat("false");
			} else {
				target = url;
			}
		} else if(!Actions.SEARCH_USERS.equalsIgnoreCase(action)
				&& !Actions.SEARCH_USERS.equalsIgnoreCase(action)) {
			defaultSearch = ParameterNames.RATING.concat("=").concat("2");
			target = ControllerPaths.NO_CONTEXT_PRODUCTO.concat("?").concat(ParameterNames.ACTION).concat("=").concat(Actions.SEARCH_PRODUCTS)
					.concat("&").concat(ParameterNames.DEFAULT).concat("=").concat("true").concat(defaultSearch);
		}
		RedirectOrForward.send(request, response, false, target, true);
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
