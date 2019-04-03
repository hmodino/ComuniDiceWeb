package com.comunidice.web.servlet;

import java.io.IOException;
import java.util.Date;

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
import com.comunidice.web.util.ParameterNames;
import com.comunidice.web.util.RedirectOrForward;
import com.comunidice.web.util.SelectClass;
import com.comunidice.web.util.ValidationUtils;
import com.rollanddice.comunidice.model.Criteria;
import com.rollanddice.comunidice.model.Juego;
import com.rollanddice.comunidice.model.Producto;
import com.rollanddice.comunidice.model.Results;
import com.rollanddice.comunidice.service.impl.ProductoServiceImpl;
import com.rollanddice.comunidice.service.spi.ProductoService;

@WebServlet("/producto")
public class ProductoServlet extends HttpServlet {
	
	private static Logger logger = LogManager.getLogger(ProductoServlet.class);
	private static ProductoService service = null;

    public ProductoServlet() {
        super();
        service = new ProductoServiceImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Errors errors = new Errors();
		Producto p = null;
		Criteria c = null;
		
		Results<Producto> products = null;
		Results<Juego> games = null;
		
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
		Integer startIndex = null;
		Integer count = null;
		
		String target = null;
		Boolean redirect = null;
		
		if(Actions.SEARCH_PRODUCTS.equalsIgnoreCase(action)) {
			
			Boolean game = null;
			
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
				try {
					games = service.findJuegoByCriteria(c, startIndex, count);
					request.setAttribute(AttributeNames.RESULTS, games);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else {
				try {
					products = service.findByCriteria(c, language, startIndex, count);
					request.setAttribute(AttributeNames.RESULTS, products);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		else if(Actions.DETAIL_VIEW.equalsIgnoreCase(action)) {

			try {
				p = service.findById(id, language);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		RedirectOrForward.send(request, response, redirect, target);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
