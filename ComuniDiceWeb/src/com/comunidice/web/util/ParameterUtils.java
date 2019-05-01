package com.comunidice.web.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.rollanddice.comunidice.model.Criteria;

public class ParameterUtils {
	
	public static final String criteriaURLBuilder(Criteria c, Boolean defaultSearch, HttpServletRequest request) {
		
		StringBuilder criteria = null;
		String s = null;
		Map<String, String> urlMap = new HashMap<String, String>();
		
		urlMap.put(ParameterNames.ACTION, Actions.SEARCH_PRODUCTS);
		urlMap.put(ParameterNames.DEFAULT, defaultSearch.toString());
	
		if(c.getIdCategoria()!=null) {
			s = ValidationUtils.isEmpty(c.getIdCategoria().toString());
			urlMap.put(ParameterNames.CATEGORY_ID, s);
		}
		if(c.getNombre()!=null) {
			s = ValidationUtils.isEmpty(c.getNombre());
			urlMap.put(ParameterNames.NAME, s);
		}
		if(c.getPrecioDesde()!=null) {
			s = ValidationUtils.isEmpty(c.getPrecioDesde().toString());
			urlMap.put(ParameterNames.MIN_PRICE, s);
		}
		if(c.getPrecioHasta()!=null) {
			s = ValidationUtils.isEmpty(c.getPrecioHasta().toString());
			urlMap.put(ParameterNames.MAX_PRICE, s);
		}
		if(c.getFechaMinima()!=null) {
			s = ValidationUtils.isEmpty(ValidationUtils.dateToString(c.getFechaMinima()));
			urlMap.put(ParameterNames.MIN_DATE, s);
		}
		if(c.getFechaMaxima()!=null) {
			s = ValidationUtils.isEmpty(ValidationUtils.dateToString(c.getFechaMaxima()));
			urlMap.put(ParameterNames.MAX_DATE, s);
		}
		if(c.getNumeroFavoritos()!=null) {
			s = ValidationUtils.isEmpty(c.getNumeroFavoritos().toString());
			urlMap.put(ParameterNames.FAVS, s);
		}
		if(c.getValoracion()!=null) {
			s = ValidationUtils.isEmpty(c.getValoracion().toString());
			urlMap.put(ParameterNames.RATING, s);
		}
		if(c.getIdVendedor()!=null) {
			s = ValidationUtils.isEmpty(c.getIdVendedor().toString());
			urlMap.put(ParameterNames.SELLER_ID, s);
		}
		if(c.getTipoVendedor()!=null) {
			s = ValidationUtils.isEmpty(c.getTipoVendedor().toString());
			urlMap.put(ParameterNames.SELLER_TYPE, s);
		}
		if(c.getAnhoPublicacionMinimo()!=null) {
			s = ValidationUtils.isEmpty(c.getAnhoPublicacionMinimo().toString());
			urlMap.put(ParameterNames.MIN_PUBLICATION_YEAR, s);
		}
		if(c.getAnhoPublicacionMaximo()!=null) {
			s = ValidationUtils.isEmpty(c.getAnhoPublicacionMaximo().toString());
			urlMap.put(ParameterNames.MAX_PUBLICATION_YEAR, s);
		}
		if(c.getFormato()!=null) {
			s = ValidationUtils.isEmpty(c.getFormato().toString());
			urlMap.put(ParameterNames.FORMAT, s);
		}
		if(c.getTipoTapa()!=null) {
			s = ValidationUtils.isEmpty(c.getTipoTapa().toString());
			urlMap.put(ParameterNames.COVER_TYPE, s);
		}
		
		s = ParameterUtils.URLBuilder(ControllerPaths.PRODUCTO, urlMap);
		return s;
	}
	
	public static String URLBuilder (String context, Map<String, String> params) {
		Integer cont = 1;
		StringBuilder url = new StringBuilder();
		url.append(context);
		for(String mapKey: params.keySet()) {
			if(cont == 1) {
				url.append("?");
			}
			url.append(ValidationUtils.isEmpty(mapKey)).append("=")
				.append(ValidationUtils.isEmpty(params.get(mapKey)));
			if(cont != params.size()) {
				url.append("&");
			}
			cont++;
		}
		return url.toString();
	}

}
