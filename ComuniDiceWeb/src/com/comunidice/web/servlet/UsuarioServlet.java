package com.comunidice.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.comunidice.web.model.Errors;
import com.comunidice.web.util.Actions;
import com.comunidice.web.util.AttributeNames;
import com.comunidice.web.util.ErrorCodes;
import com.comunidice.web.util.ParameterNames;
import com.comunidice.web.util.ParamsUtils;
import com.comunidice.web.util.SessionAttributeNames;
import com.comunidice.web.util.SessionManager;
import com.comunidice.web.util.ValidationUtils;
import com.comunidice.web.util.ViewPaths;
import com.mysql.cj.util.StringUtils;
import com.rollanddice.comunidice.model.Direccion;
import com.rollanddice.comunidice.model.Usuario;
import com.rollanddice.comunidice.service.impl.UsuarioServiceImpl;
import com.rollanddice.comunidice.service.spi.UsuarioService;

@WebServlet("/usuario")
public class UsuarioServlet extends HttpServlet {

	private static Logger logger = LogManager.getLogger(UsuarioServlet.class);
	private UsuarioService service = null;
	
    public UsuarioServlet() {
    	super();
    	service = new UsuarioServiceImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		String action = ParamsUtils.getParameter(request, ParameterNames.ACTION);
		Errors errors = new Errors();
		Usuario u = new Usuario();
		Direccion d = new Direccion();
		String target = null;
		String email = null;
		String password = null;
		String userName = null;
		String searchBy = null;
		String name = null;
		String surname1 = null;
		String surname2 = null;
		String description = null;
		String phone = null;
		String municipality = null;
		String locality = null;
		String cp = null;
		String street = null;
		Integer number = null;
		String portal = null;
		Integer floor = null;
		String other = null;

		
		if (Actions.LOGIN.equalsIgnoreCase(action)) {
			
			email = ParamsUtils.getParameter(request, ParameterNames.EMAIL);
			password = ParamsUtils.getParameter(request, ParameterNames.PASSWORD);

			if (StringUtils.isEmptyOrWhitespaceOnly(email)) {
				errors.add(ParameterNames.EMAIL,ErrorCodes.MANDATORY_PARAMETER);
			}
			if(StringUtils.isEmptyOrWhitespaceOnly(password)) {
				errors.add(ParameterNames.PASSWORD, ErrorCodes.MANDATORY_PARAMETER);
			}

				try {
					u = service.logIn(email, password);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			
			if (errors.hasErrors() || u==null) {	
				if (logger.isDebugEnabled()) {
					logger.debug("Autenticacion fallida: {}", errors);
				}
				
				errors.add(ParameterNames.ACTION,ErrorCodes.AUTHENTICATION_ERROR);				
				request.setAttribute(AttributeNames.ERRORS, errors);				
				target = ViewPaths.LOGIN;
			} else {
				
				SessionManager.set(request, SessionAttributeNames.USER, u);		
				target = ViewPaths.HOME;	
			}
		}
		
		else if(Actions.LOGOUT.equalsIgnoreCase(action)) {
			
			SessionManager.set(request, SessionAttributeNames.USER, null);
			target = ViewPaths.HOME;
		}
		
		else if(Actions.SEARCH_USERS.equalsIgnoreCase(action)) {
			
			searchBy = ParamsUtils.getParameter(request, ParameterNames.SEARCH_BY);
			
			if(searchBy.equalsIgnoreCase(ParameterNames.EMAIL)) {
				email = ParamsUtils.getParameter(request, ParameterNames.SEARCH_BOX);
				if(StringUtils.isEmptyOrWhitespaceOnly(email)) {
					errors.add(ParameterNames.EMAIL,ErrorCodes.MANDATORY_PARAMETER);
				}
			}else if(searchBy.equalsIgnoreCase(ParameterNames.USER_NAME)){
				userName = ParamsUtils.getParameter(request, ParameterNames.USER_NAME);
				if(StringUtils.isEmptyOrWhitespaceOnly(userName)) {
					errors.add(ParameterNames.EMAIL,ErrorCodes.MANDATORY_PARAMETER);
				}
			}
			
			if(!errors.hasErrors()) {
				try {
					if(email != null) {
						u = service.findByEmail(email);
					}
					if(userName != null) {
						u = service.findByNombre(userName);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			if(errors.hasErrors() || u==null) {
				if (logger.isDebugEnabled()) {
					logger.debug("El usuario que buscas no existe: {}", errors);
				}
				errors.add(ParameterNames.ACTION,ErrorCodes.NOT_FOUND_OBJECT);				
				request.setAttribute(AttributeNames.ERRORS, errors);
				target = ViewPaths.BUSCADOR;
			}else {
				target = ViewPaths.BUSCADOR;
				request.setAttribute(AttributeNames.RESULTADOS, u);
			}
		}
		
		else if(Actions.SIGN_UP.equalsIgnoreCase(action)) {
			
			name = ParamsUtils.getParameter(request, ParameterNames.NAME);
			password = ParamsUtils.getParameter(request, ParameterNames.PASSWORD);
			email = ParamsUtils.getParameter(request, ParameterNames.EMAIL);
			userName = ParamsUtils.getParameter(request, ParameterNames.USER_NAME);
			surname1 = ParamsUtils.getParameter(request, ParameterNames.SURNAME);
			surname2 = ParamsUtils.getParameter(request, ParameterNames.SURNAME2);
			description = ParamsUtils.getParameter(request, ParameterNames.DESCRIPTION);
			phone = ParamsUtils.getParameter(request, ParameterNames.PHONE);
			municipality = ParamsUtils.getParameter(request, ParameterNames.MUNICIPALITY);
			locality = ParamsUtils.getParameter(request, ParameterNames.LOCALITY);
			cp = ParamsUtils.getParameter(request, ParameterNames.CP);
			street = ParamsUtils.getParameter(request, ParameterNames.STREET);
			number = ValidationUtils.parseInt(ParamsUtils.getParameter(request, ParameterNames.PORTAL_NUMBER));
			portal = ParamsUtils.getParameter(request, ParameterNames.PORTAL);
			floor = ValidationUtils.parseInt(ParamsUtils.getParameter(request, ParameterNames.FLOOR));
			other = ParamsUtils.getParameter(request, ParameterNames.DIRECTION_OTHERS);
			
			if(!StringUtils.isEmptyOrWhitespaceOnly(name)) {
				u.setNombre(name);
			}else {
				errors.add(ParameterNames.NAME, ErrorCodes.MANDATORY_PARAMETER);
			}
			if(!StringUtils.isEmptyOrWhitespaceOnly(password)) {
				u.setContrasenha(password);
			}else {
				errors.add(ParameterNames.PASSWORD, ErrorCodes.MANDATORY_PARAMETER);
			}
			if(!StringUtils.isEmptyOrWhitespaceOnly(email)) {
				u.setEmail(email);
			}else {
				errors.add(ParameterNames.EMAIL, ErrorCodes.MANDATORY_PARAMETER);
			}
			if(!StringUtils.isEmptyOrWhitespaceOnly(userName)) {
				u.setNombreUsuario(userName);
			}else {
				errors.add(ParameterNames.USER_NAME, ErrorCodes.MANDATORY_PARAMETER);
			}
			if(!StringUtils.isEmptyOrWhitespaceOnly(surname1)) {
				u.setApellido1(surname1);
			}else {
				errors.add(ParameterNames.SURNAME, ErrorCodes.MANDATORY_PARAMETER);
			}
			if(!StringUtils.isEmptyOrWhitespaceOnly(surname2)) {
				u.setApellido2(surname2);
			}
			if(!StringUtils.isEmptyOrWhitespaceOnly(description)) {
				u.setDescripcion(description);
			}
			if(!StringUtils.isEmptyOrWhitespaceOnly(phone)) {
				u.setTelefono(phone);
			}
		}
		request.getRequestDispatcher(target).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}

}
