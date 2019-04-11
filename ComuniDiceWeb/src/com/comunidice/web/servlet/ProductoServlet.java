package com.comunidice.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import com.comunidice.web.util.ConfigurationManager;
import com.comunidice.web.util.ConfigurationParameterNames;
import com.comunidice.web.util.ErrorCodes;
import com.comunidice.web.util.ParameterNames;
import com.comunidice.web.util.ParameterUtils;
import com.comunidice.web.util.RedirectOrForward;
import com.comunidice.web.util.SelectClass;
import com.comunidice.web.util.SessionManager;
import com.comunidice.web.util.SetAttribute;
import com.comunidice.web.util.ValidationUtils;
import com.comunidice.web.util.ViewPaths;
import com.comunidice.web.util.WebUtils;
import com.rollanddice.comunidice.model.Comentario;
import com.rollanddice.comunidice.model.Criteria;
import com.rollanddice.comunidice.model.Juego;
import com.rollanddice.comunidice.model.Producto;
import com.rollanddice.comunidice.model.Results;
import com.rollanddice.comunidice.model.Usuario;
import com.rollanddice.comunidice.service.impl.ComentarioServiceImpl;
import com.rollanddice.comunidice.service.impl.ProductoServiceImpl;
import com.rollanddice.comunidice.service.spi.ComentarioService;
import com.rollanddice.comunidice.service.spi.ProductoService;

@WebServlet("/producto")
public class ProductoServlet extends HttpServlet {
	
	private static int count = Integer.valueOf(
			ConfigurationManager.getInstance().getParameter(
					ConfigurationParameterNames.RESULTS_PAGE_SIZE_DEFAULT)); 

	private static int pagingPageCount = Integer.valueOf(
			ConfigurationManager.getInstance().getParameter(
					ConfigurationParameterNames.RESULTS_PAGING_PAGE_COUNT)); 
	
	private static Logger logger = LogManager.getLogger(ProductoServlet.class);
	private static ProductoService service = null;
	private static ComentarioService commentService = null;

    public ProductoServlet() {
        super();
        service = new ProductoServiceImpl();
        commentService = new ComentarioServiceImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Errors errors = new Errors();
		Producto p = null;
		Juego g = null;
		Criteria c = null;
		Comentario comment = null;
		Usuario u = null;
		
		Results<Producto> products = null;
		Results<Juego> games = null;
		List<Juego> gs = null;
		List<Producto> ps = null;
		
		String action = ValidationUtils.parameterIsEmpty(request, ParameterNames.ACTION);
		
		Integer id = null;
		String language = null;
		Integer categoryId = null;
		String name = null;
		Double minPrice = null;
		Double maxPrice = null;
		Date minDate = null;
		Date maxDate = null;
		Integer favs = null;
		Integer rating = null;
		Integer sellerId = null;
		Integer sellerType = null;
		Date minPublicationYear = null;
		Date maxPublicationYear = null;
		Integer format = null;
		Integer coverType = null;
		Integer page = null;
		Integer startIndex = null;
		Integer totalPages = null;
		Integer firstPagedPage = null;
		Integer lastPagedPage = null;
		String content = null;
		Integer userId = null;
		
		String url = null;
		String target = null;
		Boolean redirect = null;
		Boolean send = true;
		
		if(Actions.SEARCH_PRODUCTS.equalsIgnoreCase(action)) {
			
			Boolean game = null;
			c = new Criteria();
			
			categoryId = ValidationUtils.parseIntParameter(request, ParameterNames.CATEGORY_ID);
			name = ValidationUtils.parameterIsEmpty(request, ParameterNames.NAME);
			minPrice = ValidationUtils.parseDoubleParameter(request, ParameterNames.MIN_PRICE);
			maxPrice = ValidationUtils.parseDoubleParameter(request, ParameterNames.MAX_PRICE);
			minDate = ValidationUtils.parameterDateFormat(request, ParameterNames.MIN_DATE);
			maxDate = ValidationUtils.parameterDateFormat(request, ParameterNames.MAX_DATE);
			favs = ValidationUtils.parseIntParameter(request, ParameterNames.FAVS);
			rating = ValidationUtils.parseIntParameter(request, ParameterNames.RATING);
			sellerId = ValidationUtils.parseIntParameter(request, ParameterNames.SELLER_ID);
			sellerType = ValidationUtils.parseIntParameter(request, ParameterNames.SELLER_TYPE);
			minPublicationYear = ValidationUtils.parameterDateFormat(request, ParameterNames.MIN_PUBLICATION_YEAR);
			maxPublicationYear = ValidationUtils.parameterDateFormat(request, ParameterNames.MAX_PUBLICATION_YEAR);
			format = ValidationUtils.parseIntParameter(request, ParameterNames.FORMAT);
			coverType = ValidationUtils.parseIntParameter(request, ParameterNames.COVER_TYPE);
			
			page = WebUtils.getPage(request, ParameterNames.PAGE);
			startIndex = (page-1)*count+1;

			if(categoryId!=null) {
				c.setIdCategoria(categoryId);
			}
			if(name!=null) {
				c.setNombre(name);
			}
			if(minPrice!=null) {
				c.setPrecioDesde(minPrice);
			}
			if(maxPrice!=null) {
				c.setPrecioHasta(maxPrice);
			}
			if(minDate!=null) {
				c.setFechaMinima(minDate);
			}
			if(maxDate!=null) {
				c.setFechaMaxima(maxDate);
			}
			if(favs!=null) {
				c.setNumeroFavoritos(favs);
			}
			if(rating!=null) {
				c.setValoracion(rating);
			}
			if(sellerId!=null) {
				c.setIdVendedor(sellerId);
			}
			if(sellerType!=null) {
				c.setTipoVendedor(sellerType);
			}
			if(minPublicationYear!=null) {
				c.setAnhoPublicacionMinimo(minPublicationYear);
			}
			if(maxPublicationYear!=null) {
				c.setAnhoPublicacionMaximo(maxPublicationYear);
			}
			if(format!=null) {
				c.setFormato(format);
			}
			if(coverType!=null) {
				c.setTipoTapa(coverType);
			}
			
			game = SelectClass.game(c);

			if(game) {
				gs = new ArrayList<Juego>();
				games = new Results<Juego>(gs, startIndex, count);
				try {
					games = service.findJuegoByCriteria(c, startIndex, count);
					gs = games.getPage();
					totalPages = (int) Math.ceil((double)games.getTotal()/(double)count);
					
				} catch (Exception e) {
					e.printStackTrace();
					errors.add(ParameterNames.GAME, ErrorCodes.NOT_FOUND_OBJECT);
				}
			}else {
				ps = new ArrayList<Producto>();
				products = new Results<Producto>(ps, startIndex, count);
				try {
					products = service.findByCriteria(c, language, startIndex, count);
					ps = products.getPage();
					totalPages = (int) Math.ceil((double)products.getTotal()/(double)count);
				} catch (Exception e) {
					e.printStackTrace();
					errors.add(ParameterNames.PRODUCT, ErrorCodes.NOT_FOUND_OBJECT);
				}
			}
			if(errors.hasErrors()) {
				target = ViewPaths.BUSCADOR;
				redirect = false;
				SetAttribute.setErrors(request, errors);
			}else {
				target = ViewPaths.BUSCADOR;
				redirect = false;
				if(game) {
					SetAttribute.setResult(request, gs);

				}else {
					SetAttribute.setResult(request, ps);
				}
				firstPagedPage = Math.max(1, page-pagingPageCount);
				lastPagedPage = Math.min(totalPages, page+pagingPageCount);
				url = ParameterUtils.criteriaURLBuilder(c);
				SetAttribute.setOthers(request, ParameterNames.PAGE, page);
				SetAttribute.setOthers(request, AttributeNames.TOTAL_PAGES, totalPages);
				SetAttribute.setOthers(request, AttributeNames.FIRST_PAGED_PAGE, firstPagedPage);
				SetAttribute.setOthers(request, AttributeNames.LAST_PAGED_PAGE, lastPagedPage);
				SetAttribute.setOthers(request, AttributeNames.URL, url);
			}
		}
		
		else if(Actions.DETAIL_VIEW.equalsIgnoreCase(action)) {
			
			id = ValidationUtils.parseIntParameter(request, ParameterNames.ID);
			
			if(id!=null) {
				g = new Juego();
				try {
					g = service.findJuegoById(id);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(g == null) {

					p = new Producto();					
					try {
						p = service.findById(id, language);
					} catch (Exception e) {
						e.printStackTrace();
						errors.add(ParameterNames.PRODUCT, ErrorCodes.FINDER_ERROR);
					}
				}
			}else {
				errors.add(ParameterNames.ID, ErrorCodes.MANDATORY_PARAMETER);
			}
			if(errors.hasErrors()) {
				target = ViewPaths.HOME;
				redirect = false;
				SetAttribute.setErrors(request, errors);
			}else {
				if(g!=null) {
					SetAttribute.setResult(request, g);
					target = ViewPaths.PRODUCT_DETAIL;
				}else {
					SetAttribute.setResult(request, p);
					target = ViewPaths.PRODUCT_DETAIL;
				}
				redirect = false;
			}
		}
		
		else if(Actions.CREATE_COMMENT.equalsIgnoreCase(action)) {
			
			u = (Usuario) SessionManager.get(request, AttributeNames.USER);
			
			id = ValidationUtils.parseIntParameter(request, ParameterNames.ID);
			content = ValidationUtils.parameterIsEmpty(request, ParameterNames.CONTENT);
			
			if(u!=null) {
				comment = new Comentario();
				
				if(id!=null && content!=null) {
					comment.setProducto(id);
					comment.setContenido(content);
					comment.setUsuario(u.getIdUsuario());
					try {
						commentService.create(comment);
					} catch (Exception e) {
						e.printStackTrace();
						errors.add(ParameterNames.COMMENT, ErrorCodes.CREATE_COMMENT_ERROR);
					}
				}else {
					if(id==null) {
						errors.add(ParameterNames.ID, ErrorCodes.MANDATORY_PARAMETER);
					}
					if(content==null) {
						errors.add(ParameterNames.CONTENT, ErrorCodes.MANDATORY_PARAMETER);
					}
				}
			}else {
				errors.add(ParameterNames.USER, ErrorCodes.NOT_FOUND_OBJECT);
				target = ViewPaths.HOME;
				redirect = false;
			}
			if(errors.hasErrors()) {
				if(target==null) {
					send = false;
				}else {
					redirect = false;
					SetAttribute.setErrors(request, errors);
				}
			}else {
				/*
				 * Aqui hacer llamada ajax para actualizar automaticamente los comentarios.
				 * Si no hay tiempo mandar recargar la misma pagina.
				 */
				send = false;
			}
		}
		
		else if(Actions.DELETE_COMMENT.equalsIgnoreCase(action)) {
			
			u = new Usuario();
			u = (Usuario) SessionManager.get(request, AttributeNames.USER);
			if(u!=null) {
				userId = ValidationUtils.parseIntParameter(request, ParameterNames.USER_ID);
				if(userId!=null) {
					if(u.getIdUsuario().equals(userId)) {
						id = ValidationUtils.parseIntParameter(request, ParameterNames.ID);
						if(id!=null) {
							try {
								commentService.delete(id);
							} catch (Exception e) {
								e.printStackTrace();
								errors.add(ParameterNames.COMMENT, ErrorCodes.REMOVE_ERROR);
							}
						}else {
							errors.add(ParameterNames.ID, ErrorCodes.MANDATORY_PARAMETER);
						}
					}else {
						errors.add(ParameterNames.USER_ID, ErrorCodes.IDENTITY_ERROR);
					}
				}else {
					errors.add(ParameterNames.USER_ID, ErrorCodes.MANDATORY_PARAMETER);
				}
			}else {
				errors.add(ParameterNames.USER, ErrorCodes.NOT_FOUND_OBJECT);
				target = ViewPaths.HOME;
			}
			if(errors.hasErrors()) {
				if(target==null) {
					send = false;
				}else {
					redirect = false;
					SetAttribute.setErrors(request, errors);
				}
			}else {
				/*
				 * Llamada Ajax
				 */
				send = false;
			}
		}
		RedirectOrForward.send(request, response, redirect, target, send);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
