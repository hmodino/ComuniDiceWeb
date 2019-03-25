package com.comunidice.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import com.comunidice.web.util.RedirectOrForward;
import com.comunidice.web.util.SessionAttributeNames;
import com.comunidice.web.util.SessionManager;
import com.comunidice.web.util.ValidationUtils;
import com.comunidice.web.util.ViewPaths;
import com.rollanddice.comunidice.model.Amigo;
import com.rollanddice.comunidice.model.Direccion;
import com.rollanddice.comunidice.model.Mensaje;
import com.rollanddice.comunidice.model.Pais;
import com.rollanddice.comunidice.model.Region;
import com.rollanddice.comunidice.model.Usuario;
import com.rollanddice.comunidice.service.impl.AmigoServiceImpl;
import com.rollanddice.comunidice.service.impl.MensajeServiceImpl;
import com.rollanddice.comunidice.service.impl.UsuarioServiceImpl;
import com.rollanddice.comunidice.service.spi.AmigoService;
import com.rollanddice.comunidice.service.spi.MensajeService;
import com.rollanddice.comunidice.service.spi.UsuarioService;

@WebServlet("/usuario")
public class UsuarioServlet extends HttpServlet {

	private static Logger logger = LogManager.getLogger(UsuarioServlet.class);
	
	private UsuarioService service = null;
	private AmigoService amigoService = null;
	private MensajeService mensajeService = null;
	
	
	
    public UsuarioServlet() {
    	super();
    	service = new UsuarioServiceImpl();
    	amigoService = new AmigoServiceImpl();
    	mensajeService = new MensajeServiceImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		Errors errors = new Errors();
		Usuario u = null;
		Direccion d = null;;
		Region r = null;
		Pais p = null;
		Amigo a = null;
		Mensaje m = null;
		
		List<Mensaje> messages = null;
		List<Amigo> friends = null;
		

		String action = ValidationUtils.isEmpty(ParamsUtils.getParameter(request, ParameterNames.ACTION));
		
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
		Integer id = null;
		String messageType = null;
		String messageContent = null;

		Boolean redirect = null;
		
		if (Actions.LOGIN.equalsIgnoreCase(action)) {
			
			email = ValidationUtils.isEmpty(ParamsUtils.getParameter(request, ParameterNames.EMAIL));
			password = ValidationUtils.isEmpty(ParamsUtils.getParameter(request, ParameterNames.PASSWORD));

			if (email == null) {
				errors.add(ParameterNames.EMAIL,ErrorCodes.MANDATORY_PARAMETER);
			}
			if(password == null) {
				errors.add(ParameterNames.PASSWORD, ErrorCodes.MANDATORY_PARAMETER);
			}
			if(!errors.hasErrors()) {
				try {
					u = service.logIn(email, password);
				} catch (Exception ex) {
					errors.add(ParameterNames.USER, ErrorCodes.LOGIN_ERROR);
					ex.printStackTrace();
				}
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
				redirect = true;
			}
		}
		
		else if(Actions.LOGOUT.equalsIgnoreCase(action)) {
			
			SessionManager.set(request, SessionAttributeNames.USER, null);
			target = ViewPaths.HOME;
			redirect = true;
		}
		
		else if(Actions.SEARCH_USERS.equalsIgnoreCase(action)) {
			
			searchBy = ParamsUtils.getParameter(request, ParameterNames.SEARCH_BY);
			
			if(searchBy.equalsIgnoreCase(ParameterNames.EMAIL)) {
				email = ValidationUtils.isEmpty(ParamsUtils.getParameter(request, ParameterNames.SEARCH_BOX));
				if(email == null) {
					errors.add(ParameterNames.EMAIL,ErrorCodes.MANDATORY_PARAMETER);
				}
			}else if(searchBy.equalsIgnoreCase(ParameterNames.USER_NAME)){
				userName = ValidationUtils.isEmpty(ParamsUtils.getParameter(request, ParameterNames.SEARCH_BOX));
				if(userName == null) {
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
					errors.add(ParameterNames.USER, ErrorCodes.FINDER_ERROR);
					ex.printStackTrace();
				}
			}
			if(errors.hasErrors() || u==null) {
				if (logger.isDebugEnabled()) {
					logger.debug("El usuario que buscas no existe: {}", errors);
				}
				errors.add(ParameterNames.ACTION,ErrorCodes.NOT_FOUND_OBJECT);
				target = ViewPaths.BUSCADOR;
				redirect = false;
				request.setAttribute(AttributeNames.ERRORS, errors);
			}else {
				target = ViewPaths.BUSCADOR;
				redirect = false;
				request.setAttribute(AttributeNames.RESULTS, u);
			}
		}
		
		else if(Actions.SIGN_UP.equalsIgnoreCase(action)) {
			
			u = new Usuario();
			
			name = ValidationUtils.isEmpty(ParamsUtils.getParameter(request, ParameterNames.NAME));
			password = ValidationUtils.isEmpty(ParamsUtils.getParameter(request, ParameterNames.PASSWORD));
			email = ValidationUtils.isEmpty(ParamsUtils.getParameter(request, ParameterNames.EMAIL));
			userName = ValidationUtils.isEmpty(ParamsUtils.getParameter(request, ParameterNames.USER_NAME));
			surname1 = ValidationUtils.isEmpty(ParamsUtils.getParameter(request, ParameterNames.SURNAME));
			surname2 = ValidationUtils.isEmpty(ParamsUtils.getParameter(request, ParameterNames.SURNAME2));
			description = ValidationUtils.isEmpty(ParamsUtils.getParameter(request, ParameterNames.DESCRIPTION));
			phone = ValidationUtils.isEmpty(ParamsUtils.getParameter(request, ParameterNames.PHONE));
			municipality = ValidationUtils.isEmpty(ParamsUtils.getParameter(request, ParameterNames.MUNICIPALITY));
			locality = ValidationUtils.isEmpty(ParamsUtils.getParameter(request, ParameterNames.LOCALITY));
			cp = ValidationUtils.isEmpty(ParamsUtils.getParameter(request, ParameterNames.CP));
			street = ValidationUtils.isEmpty(ParamsUtils.getParameter(request, ParameterNames.STREET));
			number = ValidationUtils.parseInt(ParamsUtils.getParameter(request, ParameterNames.PORTAL_NUMBER));
			portal = ValidationUtils.isEmpty(ParamsUtils.getParameter(request, ParameterNames.PORTAL));
			floor = ValidationUtils.parseInt(ParamsUtils.getParameter(request, ParameterNames.FLOOR));
			other = ValidationUtils.isEmpty(ParamsUtils.getParameter(request, ParameterNames.DIRECTION_OTHERS));
			
			if(name != null) {
				u.setNombre(name);
			}else {
				errors.add(ParameterNames.NAME, ErrorCodes.MANDATORY_PARAMETER);
			}
			if(password != null) {
				u.setContrasenha(password);
			}else {
				errors.add(ParameterNames.PASSWORD, ErrorCodes.MANDATORY_PARAMETER);
			}
			if(email != null) {
				u.setEmail(email);
			}else {
				errors.add(ParameterNames.EMAIL, ErrorCodes.MANDATORY_PARAMETER);
			}
			if(userName != null) {
				u.setNombreUsuario(userName);
			}else {
				errors.add(ParameterNames.USER_NAME, ErrorCodes.MANDATORY_PARAMETER);
			}
			if(surname1 != null) {
				u.setApellido1(surname1);
			}else {
				errors.add(ParameterNames.SURNAME, ErrorCodes.MANDATORY_PARAMETER);
			}
			if(surname2 != null) {
				u.setApellido2(surname2);
			}
			if(description != null) {
				u.setDescripcion(description);
			}
			if(phone != null) {
				u.setTelefono(phone);
			}

			if(municipality != null && cp != null) {
				d= new Direccion();
				d.setRegion(27);
				d.setMunicipio(municipality);
				d.setCp(cp);
				if(other != null) {
					d.setOtros(other);
				}
				if(locality != null) {
					d.setLocalidad(locality);
				}
				if(street != null) {
					d.setCalle(street);
					if(number == null) {
						errors.add(ParameterNames.PORTAL_NUMBER, ErrorCodes.MANDATORY_PARAMETER);
					}
				}
				if(number != null) {
					d.setNumero(number);
				}
				if(portal != null) {
					d.setPortal(portal);
				}
				if(floor != null) {
					d.setPiso(floor);
				}
				if(street == null && other == null) {
					errors.add(ParameterNames.STREET, ErrorCodes.ALTERNATIVE_MANDATORY_PARAMETERS);
					errors.add(ParameterNames.DIRECTION_OTHERS, ErrorCodes.ALTERNATIVE_MANDATORY_PARAMETERS);
				}
			}

			if(u == null || errors.hasErrors()) {
				
				if (logger.isDebugEnabled()) {
					logger.debug("El usuario que buscas no existe: {}", errors);
				}				
				target = ViewPaths.SIGNUP;
				redirect = false;
				request.setAttribute(AttributeNames.ERRORS, errors);
			}else {
				try {
					service.signUp(u, d);
					SessionManager.set(request, SessionAttributeNames.USER, u);	
					target = ViewPaths.HOME;
					redirect = true;
				} catch (Exception e) {
					errors.add(ParameterNames.USER, ErrorCodes.SIGN_UP_ERROR);
					target = ViewPaths.SIGNUP;
					redirect = false;
					request.setAttribute(AttributeNames.ERRORS, errors);
				}
			}
		}
		
		else if(Actions.EDIT.equalsIgnoreCase(action)) {

			u = new Usuario();
			u = (Usuario) SessionManager.get(request, AttributeNames.USER);
			
			name = ValidationUtils.isEmpty(ParamsUtils.getParameter(request, ParameterNames.NAME));
			password = ValidationUtils.isEmpty(ParamsUtils.getParameter(request, ParameterNames.PASSWORD));
			email = ValidationUtils.isEmpty(ParamsUtils.getParameter(request, ParameterNames.EMAIL));
			userName = ValidationUtils.isEmpty(ParamsUtils.getParameter(request, ParameterNames.USER_NAME));
			surname1 = ValidationUtils.isEmpty(ParamsUtils.getParameter(request, ParameterNames.SURNAME));
			surname2 = ValidationUtils.isEmpty(ParamsUtils.getParameter(request, ParameterNames.SURNAME2));
			description = ValidationUtils.isEmpty(ParamsUtils.getParameter(request, ParameterNames.DESCRIPTION));
			phone = ValidationUtils.isEmpty(ParamsUtils.getParameter(request, ParameterNames.PHONE));
			municipality = ValidationUtils.isEmpty(ParamsUtils.getParameter(request, ParameterNames.MUNICIPALITY));
			locality = ValidationUtils.isEmpty(ParamsUtils.getParameter(request, ParameterNames.LOCALITY));
			cp = ValidationUtils.isEmpty(ParamsUtils.getParameter(request, ParameterNames.CP));
			street = ValidationUtils.isEmpty(ParamsUtils.getParameter(request, ParameterNames.STREET));
			number = ValidationUtils.parseInt(ParamsUtils.getParameter(request, ParameterNames.PORTAL_NUMBER));
			portal = ValidationUtils.isEmpty(ParamsUtils.getParameter(request, ParameterNames.PORTAL));
			floor = ValidationUtils.parseInt(ParamsUtils.getParameter(request, ParameterNames.FLOOR));
			other = ValidationUtils.isEmpty(ParamsUtils.getParameter(request, ParameterNames.DIRECTION_OTHERS));

			if(name != null) {
				u.setNombre(name);
			}
			if(password != null) {
				u.setContrasenha(password);
			}
			if(email != null) {
				u.setEmail(email);
			}
			if(userName != null) {
				u.setNombreUsuario(userName);
			}
			if(surname1 != null) {
				u.setApellido1(surname1);
			}
			if(surname2 != null) {
				u.setApellido2(surname2);
			}
			if(description != null) {
				u.setDescripcion(description);
			}
			if(phone != null) {
				u.setTelefono(phone);
			}
			if(municipality != null && cp != null) {
				d = new Direccion();
				d.setMunicipio(municipality);
				d.setCp(cp);
				if(other != null) {
					d.setOtros(other);
				}
				if(locality != null) {
					d.setLocalidad(locality);
				}
				if(street != null) {
					d.setCalle(street);
					if(number == null) {
						errors.add(ParameterNames.PORTAL_NUMBER, ErrorCodes.MANDATORY_PARAMETER);
					}
				}
				if(number != null) {
					d.setNumero(number);
				}
				if(portal != null) {
					d.setPortal(portal);
				}
				if(floor != null) {
					d.setPiso(floor);
				}
				if(street == null && other == null) {
					errors.add(ParameterNames.STREET, ErrorCodes.ALTERNATIVE_MANDATORY_PARAMETERS);
					errors.add(ParameterNames.DIRECTION_OTHERS, ErrorCodes.ALTERNATIVE_MANDATORY_PARAMETERS);
				}
			}

			if(errors.hasErrors()) {
				
				if (logger.isDebugEnabled()) {
					logger.debug("El usuario que buscas no existe: {}", errors);
				}				
				target = ViewPaths.USER_PROFILE;
				redirect = false;
				request.setAttribute(AttributeNames.ERRORS, errors);
			}else {
				try {
					service.editar(u, d);	
					request.setAttribute(AttributeNames.USER, u);
					target = ViewPaths.USER_PROFILE;
					redirect = false;
				} catch (Exception e) {
					errors.add(ParameterNames.USER, ErrorCodes.UPDATE_ERROR);
					target = ViewPaths.USER_PROFILE;
					redirect = false;
					request.setAttribute(AttributeNames.ERRORS, errors);
				}
			}
		}
		
		else if(Actions.DETAIL_VIEW.equalsIgnoreCase(action)) {
			
			u = new Usuario();
			id = ValidationUtils.parseInt(ParamsUtils.getParameter(request, ParameterNames.ID));
			
			if(id != null) {
				try {
					u = service.findById(id);
				}
				catch(Exception e){
					errors.add(ParameterNames.USER, ErrorCodes.FINDER_ERROR);
				}
			}else {
				errors.add(ParameterNames.ID, ErrorCodes.URL_ERROR);
	
			}
			if(u!=null) {
			request.setAttribute(AttributeNames.USER, u);
			target = ViewPaths.PROFILE;
			redirect = false;
			request.setAttribute(AttributeNames.USER, u);
			}
			else{
				errors.add(ParameterNames.USER, ErrorCodes.NOT_FOUND_OBJECT);
			}
			if(errors.hasErrors()) {
				target = ViewPaths.HOME;
				redirect = false;
				request.setAttribute(AttributeNames.ERRORS, errors);
				
			}
		}
		
		else if(Actions.PROFILE_VIEW.equalsIgnoreCase(action)) {
			
			u = new Usuario();
			u = (Usuario) SessionManager.get(request, AttributeNames.USER);
			
			if(u==null) {
				errors.add(ParameterNames.USER, ErrorCodes.NOT_FOUND_OBJECT);
				target = ViewPaths.HOME;
				redirect = false;
				request.setAttribute(AttributeNames.ERRORS, errors);
			}else {
				target = ViewPaths.USER_PROFILE;
				redirect = false;
				request.setAttribute(AttributeNames.USER, u);
			}
		} 
		
		else if(Actions.FIND_FRIENDS.equalsIgnoreCase(action)) {
			
			u = new Usuario();
			u = (Usuario)SessionManager.get(request, AttributeNames.USER);
			
			if(u != null) {
				friends = new ArrayList<Amigo>();
				try{
					friends = amigoService.findAmigos(u.getIdUsuario());
				}catch(Exception ex) {
					errors.add(ParameterNames.FRIEND, ErrorCodes.FINDER_ERROR);
					ex.printStackTrace();
				}
			}else {
				errors.add(ParameterNames.USER, ErrorCodes.NOT_FOUND_OBJECT);
				target = ViewPaths.HOME;
			}
			if(u == null || errors.hasErrors()) {
				errors.add(ParameterNames.FRIEND, ErrorCodes.NOT_FOUND_OBJECT);
				if(target == null) {
					target = ViewPaths.USER_PROFILE;
				}
				redirect = false;
				request.setAttribute(AttributeNames.ERRORS, errors);
				
			}else {
				target = ViewPaths.FRIENDS_FINDER;
				redirect = false;
				request.setAttribute(AttributeNames.RESULTS, friends);
			}
		}
		
		else if(Actions.FIND_MESSAGES.equalsIgnoreCase(action)) {
			
			u = new Usuario();
			u = (Usuario) SessionManager.get(request, AttributeNames.USER);
			
			if(u !=null) {
				messages = new ArrayList<Mensaje>();
				messageType = request.getParameter(ParameterNames.MESSAGE_TYPE);
				try {
					if(ParameterNames.SENDER.equalsIgnoreCase(messageType)) {
						messages = mensajeService.findByEmisor(u.getIdUsuario());
					}
					if(ParameterNames.RECEIVER.equalsIgnoreCase(messageType) ||
							messageType == null) {
						messages = mensajeService.findByReceptor(u.getIdUsuario());
					}
				}catch(Exception e) {
					errors.add(ParameterNames.MESSAGE, ErrorCodes.FINDER_ERROR);
				}
			}else {
				errors.add(ParameterNames.USER, ErrorCodes.NOT_FOUND_OBJECT);
				target = ViewPaths.HOME;
			}
			if(u!=null && !errors.hasErrors()) {
				target = ViewPaths.MESSAGES;
				redirect = false;
				request.setAttribute(AttributeNames.RESULTS, messages);
			}if(errors.hasErrors()) {
				if(target == null) {
					target = ViewPaths.USER_PROFILE; 
				}
				redirect = false;
				request.setAttribute(AttributeNames.ERRORS, errors);
			}
		}
		
		else if(Actions.SEND_MESSAGE.equalsIgnoreCase(action)) {
			
			u = new Usuario();
			m = new Mensaje();
			
			u = (Usuario) SessionManager.get(request, AttributeNames.USER);
			
			if(u!=null) {
				messageContent = ValidationUtils.isEmpty(request.getParameter(ParameterNames.MESSAGE_CONTENT));
				id = ValidationUtils.parseInt(request.getParameter(ParameterNames.ID));
				if(messageContent==null) {
					errors.add(ParameterNames.MESSAGE, ErrorCodes.MANDATORY_PARAMETER);
				}
				if(id==null) {
					errors.add(ParameterNames.ID, ErrorCodes.MANDATORY_PARAMETER);
				}
			}else {
					errors.add(ParameterNames.USER, ErrorCodes.NOT_FOUND_OBJECT);
					target = ViewPaths.HOME;
			}
			if(!errors.hasErrors()){
				try {
					m.setContenido(messageContent);
					m.setUsuarioEmisor(u.getIdUsuario());
					m.setUsuarioReceptor(id);
					mensajeService.create(m);
					
					target = ViewPaths.HOME;
					redirect = true;
				}catch(Exception e) {
					errors.add(ParameterNames.MESSAGE, ErrorCodes.SEND_MESSAGE_ERROR);
				}
			}
			if(errors.hasErrors()) {
				if(target == null) {
					target = ViewPaths.PROFILE;
				}
				redirect = false;
				request.setAttribute(AttributeNames.ERRORS, errors);
			}
		}
		RedirectOrForward.send(request, response, redirect, target);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}

}
