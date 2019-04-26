package com.comunidice.web.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.rollanddice.comunidice.model.Criteria;

public class ParameterUtils {
	
	public static final String criteriaURLBuilder(Criteria c, Boolean defaultSearch, HttpServletRequest request) {
		
		StringBuilder criteria = null;
		String s = null;
		
		criteria = new StringBuilder();
		
		criteria.append(ControllerPaths.PRODUCTO).append("?").append(ParameterNames.ACTION).append("=")
			.append(Actions.SEARCH_PRODUCTS).append("&").append(ParameterNames.DEFAULT).append("=")
			.append(defaultSearch.toString());
	
		if(c.getIdCategoria()!=null) {
			criteria.append("&");
			s = ValidationUtils.isEmpty(c.getIdCategoria().toString());
			criteria.append(ParameterNames.CATEGORY_ID).append("=").append(s);
		}
		if(c.getNombre()!=null) {
			criteria.append("&");
			s = ValidationUtils.isEmpty(c.getNombre());
			criteria.append(ParameterNames.NAME).append("=").append(s);
		}
		if(c.getPrecioDesde()!=null) {
			criteria.append("&");
			s = ValidationUtils.isEmpty(c.getPrecioDesde().toString());
			criteria.append(ParameterNames.MIN_PRICE).append("=").append(s);
		}
		if(c.getPrecioHasta()!=null) {
			criteria.append("&");
			s = ValidationUtils.isEmpty(c.getPrecioHasta().toString());
			criteria.append(ParameterNames.MAX_PRICE).append("=").append(s);
		}
		if(c.getFechaMinima()!=null) {
			criteria.append("&");
			s = ValidationUtils.isEmpty(ValidationUtils.dateToString(c.getFechaMinima()));
			criteria.append(ParameterNames.MIN_DATE).append("=").append(s);
		}
		if(c.getFechaMaxima()!=null) {
			criteria.append("&");
			s = ValidationUtils.isEmpty(ValidationUtils.dateToString(c.getFechaMaxima()));
			criteria.append(ParameterNames.MAX_DATE).append("=").append(s);
		}
		if(c.getNumeroFavoritos()!=null) {
			criteria.append("&");
			s = ValidationUtils.isEmpty(c.getNumeroFavoritos().toString());
			criteria.append(ParameterNames.FAVS).append("=").append(s);
		}
		if(c.getValoracion()!=null) {
			criteria.append("&");
			s = ValidationUtils.isEmpty(c.getValoracion().toString());
			criteria.append(ParameterNames.RATING).append("=").append(s);
		}
		if(c.getIdVendedor()!=null) {
			criteria.append("&");
			s = ValidationUtils.isEmpty(c.getIdVendedor().toString());
			criteria.append(ParameterNames.SELLER_ID).append("=").append(s);
		}
		if(c.getTipoVendedor()!=null) {
			criteria.append("&");
			s = ValidationUtils.isEmpty(c.getTipoVendedor().toString());
			criteria.append(ParameterNames.SELLER_TYPE).append("=").append(s);
		}
		if(c.getAnhoPublicacionMinimo()!=null) {
			criteria.append("&");
			s = ValidationUtils.isEmpty(ValidationUtils.dateToString(c.getAnhoPublicacionMinimo()));
			criteria.append(ParameterNames.MIN_PUBLICATION_YEAR).append("=").append(s);
		}
		if(c.getAnhoPublicacionMaximo()!=null) {
			criteria.append("&");
			s = ValidationUtils.isEmpty(ValidationUtils.dateToString(c.getAnhoPublicacionMaximo()));
			criteria.append(ParameterNames.MAX_PUBLICATION_YEAR).append("=").append(s);
		}
		if(c.getFormato()!=null) {
			criteria.append("&");
			s = ValidationUtils.isEmpty(c.getFormato().toString());
			criteria.append(ParameterNames.FORMAT).append("=").append(s);
		}
		if(c.getTipoTapa()!=null) {
			criteria.append("&");
			s = ValidationUtils.isEmpty(c.getTipoTapa().toString());
			criteria.append(ParameterNames.COVER_TYPE).append("=").append(s);
		}
		
		s = criteria.toString();
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
