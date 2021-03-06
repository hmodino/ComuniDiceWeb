package com.comunidice.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.comunidice.web.model.Errors;
import com.comunidice.web.model.ShoppingCart;
import com.comunidice.web.util.Actions;
import com.comunidice.web.util.AttributeNames;
import com.comunidice.web.util.ControllerPaths;
import com.comunidice.web.util.CookieManager;
import com.comunidice.web.util.ErrorCodes;
import com.comunidice.web.util.LocaleManager;
import com.comunidice.web.util.ParameterNames;
import com.comunidice.web.util.ParameterUtils;
import com.comunidice.web.util.RedirectOrForward;
import com.comunidice.web.util.SessionAttributeNames;
import com.comunidice.web.util.SessionManager;
import com.comunidice.web.util.SetAttribute;
import com.comunidice.web.util.ValidationUtils;
import com.comunidice.web.util.ViewPaths;
import com.comunidice.web.util.WebConstants;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rollanddice.comunidice.exception.DataException;
import com.rollanddice.comunidice.model.Direccion;
import com.rollanddice.comunidice.model.Favorito;
import com.rollanddice.comunidice.model.Mensaje;
import com.rollanddice.comunidice.model.Pais;
import com.rollanddice.comunidice.model.Region;
import com.rollanddice.comunidice.model.Usuario;
import com.rollanddice.comunidice.service.impl.AmigoServiceImpl;
import com.rollanddice.comunidice.service.impl.DireccionServiceImpl;
import com.rollanddice.comunidice.service.impl.FavoritoServiceImpl;
import com.rollanddice.comunidice.service.impl.MensajeServiceImpl;
import com.rollanddice.comunidice.service.impl.UsuarioServiceImpl;
import com.rollanddice.comunidice.service.spi.AmigoService;
import com.rollanddice.comunidice.service.spi.DireccionService;
import com.rollanddice.comunidice.service.spi.FavoritoService;
import com.rollanddice.comunidice.service.spi.MensajeService;
import com.rollanddice.comunidice.service.spi.UsuarioService;

@WebServlet("/usuario")
public class UsuarioServlet extends HttpServlet {

	private static Logger logger = LogManager.getLogger(UsuarioServlet.class);

	private UsuarioService service = null;
	private AmigoService amigoService = null;
	private MensajeService mensajeService = null;
	private DireccionService direccionService = null;
	private FavoritoService favoritoService = null;
	
	public UsuarioServlet() {
		super();
		service = new UsuarioServiceImpl();
		amigoService = new AmigoServiceImpl();
		mensajeService = new MensajeServiceImpl();
		direccionService = new DireccionServiceImpl();
		favoritoService = new FavoritoServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		Errors errors = new Errors();
		Usuario u = null;
		Direccion d = null;;
		Region r = null;
		Pais p = null;
		Mensaje m = null;
		ShoppingCart cart = null;
		Locale locale = null;

		List<Mensaje> messages = null;
		List<Usuario> friends = null;
		List<Locale> locales = null;
		List<Favorito> favorito = null;
		List<Region> regions = null;
		List<Pais> countries = null;
		
		Map<String, String> urlMap = null;
		
		String action = ValidationUtils.parameterIsEmpty(request, ParameterNames.ACTION);

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
		String language = null;

		Boolean redirect = false;
		Boolean send = true;

		if (Actions.LOGIN.equalsIgnoreCase(action)) {

			email = ValidationUtils.parameterIsEmpty(request, ParameterNames.EMAIL);
			password = ValidationUtils.parameterIsEmpty(request, ParameterNames.PASSWORD);

			if (email == null) {
				errors.add(ParameterNames.EMAIL,ErrorCodes.MANDATORY_PARAMETER);
			}
			if(password == null) {
				errors.add(ParameterNames.PASSWORD, ErrorCodes.MANDATORY_PARAMETER);
			}
			if(!errors.hasErrors()) {
				try {
					u = service.logIn(email, password);
					logger.debug(u.getIdUsuario());
				} catch (Exception ex) {
					errors.add(ParameterNames.USER, ErrorCodes.LOGIN_ERROR);
					ex.printStackTrace();
				}
			}
			if (errors.hasErrors() || u==null) {	
				if (logger.isDebugEnabled()) {
					logger.debug("Autenticacion fallida: {}", errors);
					target = ViewPaths.HOME;
					redirect = true;
				}

				errors.add(ParameterNames.ACTION,ErrorCodes.AUTHENTICATION_ERROR);				
				SetAttribute.setErrors(request, errors);
				target = ViewPaths.LOGIN;
			} else {
				cart = new ShoppingCart();
				SessionManager.set(request, SessionAttributeNames.USER, u);
				SessionManager.set(request, AttributeNames.SHOPPING_CART, cart);
				target = ViewPaths.HOME;
				redirect = true;
			}
			System.out.println(request.getContextPath());
		}

		else if(Actions.LOGOUT.equalsIgnoreCase(action)) {

			SessionManager.set(request, SessionAttributeNames.USER, null);
			SessionManager.set(request, AttributeNames.SHOPPING_CART, null);
			target = ViewPaths.HOME;
			redirect = true;
		}

		else if(Actions.SEARCH_USERS.equalsIgnoreCase(action)) {

			searchBy = ValidationUtils.parameterIsEmpty(request, ParameterNames.SEARCH_BY);

			if(searchBy.equalsIgnoreCase(ParameterNames.EMAIL)) {
				email = ValidationUtils.parameterIsEmpty(request, ParameterNames.SEARCH_BOX);
				if(email == null) {
					errors.add(ParameterNames.EMAIL,ErrorCodes.MANDATORY_PARAMETER);
				}
			}else if(searchBy.equalsIgnoreCase(ParameterNames.USER_NAME)){
				userName = ValidationUtils.parameterIsEmpty(request, ParameterNames.SEARCH_BOX);
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
				SetAttribute.setErrors(request, errors);
			}else {
				target = ViewPaths.BUSCADOR;
				redirect = false;
				SetAttribute.setResult(request, u);
			}
		}

		else if (Actions.PRE_SIGN_UP.equalsIgnoreCase(action)){

			id = ValidationUtils.parseIntParameter(request, ParameterNames.ID);

			try {
				countries = direccionService.findAll();

				if(id!=null) {
					regions = direccionService.findByPais(id);
					JsonObject region = null;
					JsonArray array = new JsonArray();
					for (Region rg: regions) {
						region = new JsonObject();
						region.addProperty("id", rg.getIdRegion());
						region.addProperty("nombre", rg.getNombre());
						array.add(region);
					}	
					response.setContentType("application/json;charset=ISO-8859-1");
					response.getOutputStream().write(array.toString().getBytes());
					return;
				}
				else {
					SetAttribute.setResults(request, countries);
					target = ViewPaths.SIGNUP;
				}

			} catch (DataException e) {
				logger.warn(e.getMessage(),e);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if(Actions.SIGN_UP.equalsIgnoreCase(action)) {

			u = new Usuario();

			name = ValidationUtils.parameterIsEmpty(request, ParameterNames.NAME);
			password = ValidationUtils.parameterIsEmpty(request, ParameterNames.PASSWORD);
			email = ValidationUtils.validateEmail(request, ParameterNames.EMAIL);
			userName = ValidationUtils.parameterIsEmpty(request, ParameterNames.USER_NAME);
			surname1 = ValidationUtils.parameterIsEmpty(request, ParameterNames.SURNAME);
			surname2 = ValidationUtils.parameterIsEmpty(request, ParameterNames.SURNAME2);
			description = ValidationUtils.parameterIsEmpty(request, ParameterNames.DESCRIPTION);
			phone = ValidationUtils.isNumber(request, ParameterNames.PHONE);
			municipality = ValidationUtils.parameterIsEmpty(request, ParameterNames.MUNICIPALITY);
			locality = ValidationUtils.parameterIsEmpty(request, ParameterNames.LOCALITY);
			cp = ValidationUtils.parameterIsEmpty(request, ParameterNames.CP);
			street = ValidationUtils.parameterIsEmpty(request, ParameterNames.STREET);
			number = ValidationUtils.parseIntParameter(request, ParameterNames.PORTAL_NUMBER);
			portal = ValidationUtils.parameterIsEmpty(request, ParameterNames.PORTAL);
			floor = ValidationUtils.parseIntParameter(request, ParameterNames.FLOOR);
			other = ValidationUtils.parameterIsEmpty(request, ParameterNames.DIRECTION_OTHERS);
			
			urlMap = new HashMap<String,String>();
			urlMap.put(ParameterNames.ACTION, Actions.PRE_SIGN_UP);

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
				target = ParameterUtils.URLBuilder(ControllerPaths.NO_CONTEXT_USUARIO, urlMap);
				redirect = true;
				SetAttribute.setErrors(request, errors);
			}else {
				try {
					service.signUp(u, d);
					SessionManager.set(request, SessionAttributeNames.USER, u);	
					target = ViewPaths.HOME;
					redirect = true;
				} catch (Exception e) {
					errors.add(ParameterNames.USER, ErrorCodes.SIGN_UP_ERROR);
					target = ParameterUtils.URLBuilder(ControllerPaths.NO_CONTEXT_USUARIO, urlMap);
					redirect = false;
					SetAttribute.setErrors(request, errors);
				}
			}
		}

		else if(Actions.EDIT.equalsIgnoreCase(action)) {

			u = (Usuario) SessionManager.get(request, AttributeNames.USER);

			name = ValidationUtils.parameterIsEmpty(request, ParameterNames.NAME);
			password = ValidationUtils.parameterIsEmpty(request, ParameterNames.PASSWORD);
			email = ValidationUtils.validateEmail(request, ParameterNames.EMAIL);
			userName = ValidationUtils.parameterIsEmpty(request, ParameterNames.USER_NAME);
			surname1 = ValidationUtils.parameterIsEmpty(request, ParameterNames.SURNAME);
			surname2 = ValidationUtils.parameterIsEmpty(request, ParameterNames.SURNAME2);
			description = ValidationUtils.parameterIsEmpty(request, ParameterNames.DESCRIPTION);
			phone = ValidationUtils.isNumber(request, ParameterNames.PHONE);
			municipality = ValidationUtils.parameterIsEmpty(request, ParameterNames.MUNICIPALITY);
			locality = ValidationUtils.parameterIsEmpty(request, ParameterNames.LOCALITY);
			cp = ValidationUtils.parameterIsEmpty(request, ParameterNames.CP);
			street = ValidationUtils.parameterIsEmpty(request, ParameterNames.STREET);
			number = ValidationUtils.parseIntParameter(request, ParameterNames.PORTAL_NUMBER);
			portal = ValidationUtils.parameterIsEmpty(request, ParameterNames.PORTAL);
			floor = ValidationUtils.parseIntParameter(request, ParameterNames.FLOOR);
			other = ValidationUtils.parameterIsEmpty(request, ParameterNames.DIRECTION_OTHERS);

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
				SetAttribute.setErrors(request, errors);
			}else {
				if(u!=null) {
					try {
						service.editar(u, d);	
						SetAttribute.setResult(request, u);
						SessionManager.set(request, AttributeNames.USER, u);
						target = ViewPaths.USER_PROFILE;
						redirect = false;
					} catch (Exception e) {
						errors.add(ParameterNames.USER, ErrorCodes.UPDATE_ERROR);
						target = ViewPaths.USER_PROFILE;
						redirect = false;
						SetAttribute.setErrors(request, errors);
					}
				} if(u==null && d!=null) {
					try {
						direccionService.update(d);	
						SetAttribute.setResult(request, u);
						SessionManager.set(request, AttributeNames.USER, u);
						target = ViewPaths.USER_PROFILE;
						redirect = false;
					} catch (Exception e) {
						errors.add(ParameterNames.USER, ErrorCodes.UPDATE_ERROR);
						target = ViewPaths.USER_PROFILE;
						redirect = false;
						SetAttribute.setErrors(request, errors);
					}
				}
			}
		}

		else if(Actions.DETAIL_VIEW.equalsIgnoreCase(action)) {

			id = ValidationUtils.parseIntParameter(request, ParameterNames.ID);

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
				SetAttribute.setResult(request, u);
			}
			else{
				errors.add(ParameterNames.USER, ErrorCodes.NOT_FOUND_OBJECT);
			}
			if(errors.hasErrors()) {
				target = ViewPaths.HOME;
				redirect = false;
				SetAttribute.setErrors(request, errors);

			}
		}

		else if(Actions.PROFILE_VIEW.equalsIgnoreCase(action)) {

			u = (Usuario) SessionManager.get(request, AttributeNames.USER);

			if(u==null) {
				errors.add(ParameterNames.USER, ErrorCodes.NOT_FOUND_OBJECT);
				target = ViewPaths.HOME;
				redirect = false;
				SetAttribute.setErrors(request, errors);
			}else {
				target = ViewPaths.USER_PROFILE;
				redirect = false;
				SetAttribute.setResult(request, u);
			}
		} 

		else if(Actions.FIND_FRIENDS.equalsIgnoreCase(action)) {

			u = (Usuario)SessionManager.get(request, AttributeNames.USER);

			if(u != null) {
				friends = new ArrayList<Usuario>();
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
				SetAttribute.setErrors(request, errors);

			}else {
				target = ViewPaths.FRIENDS_FINDER;
				redirect = false;
				SetAttribute.setResults(request, friends);
			}
		}

		else if(Actions.FIND_FRIENDS_BY.equalsIgnoreCase(action)) {

			u = (Usuario) SessionManager.get(request, AttributeNames.USER);
			searchBy = ValidationUtils.parameterIsEmpty(request, ParameterNames.SEARCH_BY);

			if(u == null) {
				errors.add(ParameterNames.USER, ErrorCodes.NOT_FOUND_OBJECT);
				target = ViewPaths.HOME;
			}else {
				if(searchBy.equalsIgnoreCase(ParameterNames.EMAIL)) {
					email = ValidationUtils.parameterIsEmpty(request, ParameterNames.SEARCH_BOX);
					if(email == null) {
						errors.add(ParameterNames.EMAIL,ErrorCodes.MANDATORY_PARAMETER);
					}
				}else if(searchBy.equalsIgnoreCase(ParameterNames.USER_NAME)){
					userName = ValidationUtils.parameterIsEmpty(request, ParameterNames.SEARCH_BOX);
					if(userName == null) {
						errors.add(ParameterNames.EMAIL,ErrorCodes.MANDATORY_PARAMETER);
					}
				}
			}
			if(!errors.hasErrors()) {
				try {
					if(email != null) {
						u = amigoService.findByEmailAmigo(email, u.getIdUsuario());
					}
					if(userName != null) {
						u = amigoService.findByNombreAmigo(userName, u.getIdUsuario());
					}
				} catch (Exception ex) {
					errors.add(ParameterNames.USER, ErrorCodes.FINDER_ERROR);
					ex.printStackTrace();
				}
			}
			if(errors.hasErrors()) {
				if(target == null) {
					target = ViewPaths.USER_PROFILE;
				}
				redirect = false;
				SetAttribute.setErrors(request, errors);
			}else {
				friends = new ArrayList<Usuario>();
				friends.add(u);
				target = ViewPaths.FRIENDS_FINDER;
				redirect = false;
				SetAttribute.setResults(request, friends);
			}
		}

		else if(Actions.ADD_FRIEND.equalsIgnoreCase(action)) {

			u = (Usuario) SessionManager.get(request, AttributeNames.USER);

			if(u != null) {
				id = ValidationUtils.parseIntParameter(request, ParameterNames.ID);

				if(id == null) {
					errors.add(ParameterNames.ID, ErrorCodes.MANDATORY_PARAMETER);
				}
				if(id==u.getIdUsuario()) {
					errors.add(ParameterNames.USER, ErrorCodes.ADD_ERROR);
				}
			}else {
				target = ViewPaths.HOME;
				redirect = true;
				errors.add(ParameterNames.USER, ErrorCodes.NOT_FOUND_OBJECT);
			}

			if(!errors.hasErrors()) {
				try {
					amigoService.create(u.getIdUsuario(), id);
				}catch (Exception e) {
					e.printStackTrace();
					errors.add(ParameterNames.FRIEND, ErrorCodes.ADD_ERROR);
				}
			}
			if(errors.hasErrors()) {
				if(target==null) {
					target = ViewPaths.USER_PROFILE;
					redirect = false;
					SetAttribute.setUser(request, u);
				}
			}else {
				target = ViewPaths.USER_PROFILE;
				redirect = false;
				SetAttribute.setUser(request, u);
			}
		}

		else if(Actions.REMOVE_FRIEND.equalsIgnoreCase(action)) {

			u = (Usuario) SessionManager.get(request, AttributeNames.USER);

			if(u != null) {
				id = ValidationUtils.parseIntParameter(request, ParameterNames.ID);

				if(id == null) {
					errors.add(ParameterNames.ID, ErrorCodes.MANDATORY_PARAMETER);
				}
			}else {
				target = ViewPaths.HOME;
				errors.add(ParameterNames.USER, ErrorCodes.NOT_FOUND_OBJECT);
			}

			if(!errors.hasErrors()) {
				try {
					amigoService.delete(u.getIdUsuario(), id);
				}catch (Exception e) {
					e.printStackTrace();
					errors.add(ParameterNames.FRIEND, ErrorCodes.REMOVE_ERROR);
				}
			}
			if(errors.hasErrors()) {
				if(target==null) {
					target = ViewPaths.USER_PROFILE;
					redirect = false;
					SetAttribute.setUser(request, u);
				}
			}else {
				target = ViewPaths.USER_PROFILE;
				redirect = false;
				SetAttribute.setUser(request, u);
			}
		}

		else if(Actions.FIND_MESSAGES.equalsIgnoreCase(action)) {

			u = (Usuario) SessionManager.get(request, AttributeNames.USER);

			if(u !=null) {
				messages = new ArrayList<Mensaje>();
				messageType = ValidationUtils.parameterIsEmpty(request, ParameterNames.MESSAGE_TYPE);
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

			if(errors.hasErrors()) {
				if(target == null) {
					target = ViewPaths.USER_PROFILE; 
				}
				redirect = false;
				SetAttribute.setErrors(request, errors);
			}else{
				target = ViewPaths.MESSAGES;
				redirect = false;
				SetAttribute.setResults(request, messages);
			}
		}


		else if(Actions.PRE_SEND_MESSAGE.equalsIgnoreCase(action)) {

			u = (Usuario) SessionManager.get(request, AttributeNames.USER);

			if(u != null) {
				id = ValidationUtils.parseIntParameter(request, ParameterNames.ID);
				if(id==null) {
					friends = new ArrayList<Usuario>();
					try {
						friends = amigoService.findAmigos(u.getIdUsuario());
					} catch (Exception e) {
						errors.add(ParameterNames.FRIEND, ErrorCodes.NOT_FOUND_OBJECT);
						e.printStackTrace();
					}
				}
				if(id!=null && friends!=null) {
					errors.add(ParameterNames.ID, ErrorCodes.EXCLUSIVE_PARAMETERS);
					errors.add(ParameterNames.FRIEND, ErrorCodes.EXCLUSIVE_PARAMETERS);
				}
				if(id==null && errors.hasErrors()) {
					errors.add(ParameterNames.USER, ErrorCodes.MANDATORY_PARAMETER);
				}
			}else {
				errors.add(ParameterNames.USER, ErrorCodes.NOT_FOUND_OBJECT);
				target = ViewPaths.LOGIN;
			}

			if(errors.hasErrors()) {
				if(target == null) {
					target = ViewPaths.USER_PROFILE;
				}
				redirect = false;
				SetAttribute.setErrors(request, errors);
			}else {
				target = ViewPaths.SEND_MESSAGE;
				redirect = false;
				//				if(userName!=null) {
				//					SetAttribute.setOthers(request, AttributeNames.USER, userName);
				//				}
				if(id!=null) {
					SetAttribute.setOthers(request, AttributeNames.ID, id);
				}
				if(friends!=null) {
					SetAttribute.setResults(request, friends);
				}
			}
		}

		else if(Actions.SEND_MESSAGE.equalsIgnoreCase(action)) {

			m = new Mensaje();
			u = (Usuario) SessionManager.get(request, AttributeNames.USER);

			if(u!=null) {
				messageContent = ValidationUtils.parameterIsEmpty(request, ParameterNames.CONTENT);
				id = ValidationUtils.parseIntParameter(request, ParameterNames.ID);
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
				}catch(Exception e) {
					errors.add(ParameterNames.MESSAGE, ErrorCodes.SEND_MESSAGE_ERROR);
				}
			}
			if(errors.hasErrors()) {
				if(target == null) {
					SetAttribute.setUser(request, u);
					target = ViewPaths.USER_PROFILE;
				}
				redirect = false;
				SetAttribute.setErrors(request, errors);
			}else {
				target = ViewPaths.USER_PROFILE;
				redirect = false;
				SetAttribute.setUser(request, u);
			}
		}

		else if(Actions.REMOVE_MESSAGE.equalsIgnoreCase(action)) {

			u = (Usuario) SessionManager.get(request, AttributeNames.USER);

			if(u!=null) {
				id = ValidationUtils.parseIntParameter(request, ParameterNames.ID);
				if(id==null) {
					errors.add(ParameterNames.ID, ErrorCodes.MANDATORY_PARAMETER);
				}
			}else {
				errors.add(ParameterNames.USER, ErrorCodes.NOT_FOUND_OBJECT);
				target = ViewPaths.HOME;
			}
			if(!errors.hasErrors()){
				try {
					mensajeService.delete(id);
				}catch(Exception e) {
				}
			}
			if(errors.hasErrors()) {
				if(target == null) {
					SetAttribute.setUser(request, u);
					target = ViewPaths.USER_PROFILE;
				}
				redirect = false;
				SetAttribute.setErrors(request, errors);
			}else {
				target = ViewPaths.USER_PROFILE;
				redirect = false;
				SetAttribute.setUser(request, u);
			}
		}

		else if(Actions.CHANGE_LOCALE.equalsIgnoreCase(action)) {

			language = ValidationUtils.parameterIsEmpty(request, ParameterNames.LANGUAGE);
			locales = LocaleManager.getMatchedLocales(language);
			if(locales.size()>0) {
				locale = locales.get(0);
				SessionManager.set(request, WebConstants.USER_LOCALE, locale);
				CookieManager.addCookie(response, WebConstants.USER_LOCALE, locale.toString(), "/", 365*24*60*60);
			}
				String url = ValidationUtils.parameterIsEmpty(request, ParameterNames.URL);
				if(url!=null) {
					url = url.split("ComuniDiceWeb")[1];
					target = url;
					redirect = true;
					logger.debug(url); 
				} else {
					send = false;
				}
		}
		
		else if(Actions.FAVOURITE_DETAILS.equalsIgnoreCase(action)) {
			
			u = (Usuario) SessionManager.get(request, AttributeNames.USER);
			language = SessionManager.get(request, WebConstants.USER_LOCALE).toString();
			
			if(u!=null) {
				try {
					favorito = favoritoService.findByUsuario(u.getIdUsuario(), language);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
//				errors.add(parameter, errorCode);
			}
			if(!errors.hasErrors()) {
				SetAttribute.setResults(request, favorito);
				target = ViewPaths.FAVOURITES;
			}
		}
		RedirectOrForward.send(request, response, redirect, target, send);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}

}
