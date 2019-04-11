package com.comunidice.web.util;

import com.rollanddice.comunidice.model.Criteria;

public class ParameterUtils {
	
	public static final String criteriaURLBuilder(Criteria c) {
		
		StringBuilder criteria = null;
		boolean first = true;
		String s = null;
		
		if(c.getIdCategoria()!=null) {
			if(first) {
				criteria = new StringBuilder();
				criteria.append("?");
				first = false;
			}else {
				criteria.append("&amp;");
			}
			s = ValidationUtils.isEmpty(c.getIdCategoria().toString());
			criteria.append(ParameterNames.CATEGORY_ID).append("=").append(s);
		}
		if(c.getNombre()!=null) {
			if(first) {
				criteria = new StringBuilder();
				criteria.append("?");
				first = false;
			}else {
				criteria.append("&amp;");
			}
			s = ValidationUtils.isEmpty(c.getNombre());
			criteria.append(ParameterNames.NAME).append("=").append(s);
		}
		if(c.getPrecioDesde()!=null) {
			if(first) {
				criteria = new StringBuilder();
				criteria.append("?");
				first = false;
			}else {
				criteria.append("&amp;");
			}
			s = ValidationUtils.isEmpty(c.getPrecioDesde().toString());
			criteria.append(ParameterNames.MIN_PRICE).append("=").append(s);
		}
		if(c.getPrecioHasta()!=null) {
			if(first) {
				criteria = new StringBuilder();
				criteria.append("?");
				first = false;
			}else {
				criteria.append("&amp;");
			}
			s = ValidationUtils.isEmpty(c.getPrecioHasta().toString());
			criteria.append(ParameterNames.MAX_PRICE).append("=").append(s);
		}
		if(c.getFechaMinima()!=null) {
			if(first) {
				criteria = new StringBuilder();
				criteria.append("?");
				first = false;
			}else {
				criteria.append("&amp;");
			}
			s = ValidationUtils.isEmpty(ValidationUtils.dateToString(c.getFechaMinima()));
			criteria.append(ParameterNames.MIN_DATE).append("=").append(s);
		}
		if(c.getFechaMaxima()!=null) {
			if(first) {
				criteria = new StringBuilder();
				criteria.append("?");
				first = false;
			}else {
				criteria.append("&amp;");
			}
			s = ValidationUtils.isEmpty(ValidationUtils.dateToString(c.getFechaMaxima()));
			criteria.append(ParameterNames.MAX_DATE).append("=").append(s);
		}
		if(c.getNumeroFavoritos()!=null) {
			if(first) {
				criteria = new StringBuilder();
				criteria.append("?");
				first = false;
			}else {
				criteria.append("&amp;");
			}
			s = ValidationUtils.isEmpty(c.getNumeroFavoritos().toString());
			criteria.append(ParameterNames.FAVS).append("=").append(s);
		}
		if(c.getValoracion()!=null) {
			if(first) {
				criteria = new StringBuilder();
				criteria.append("?");
				first = false;
			}else {
				criteria.append("&amp;");
			}
			s = ValidationUtils.isEmpty(c.getValoracion().toString());
			criteria.append(ParameterNames.RATING).append("=").append(s);
		}
		if(c.getIdVendedor()!=null) {
			if(first) {
				criteria = new StringBuilder();
				criteria.append("?");
				first = false;
			}else {
				criteria.append("&amp;");
			}
			s = ValidationUtils.isEmpty(c.getIdVendedor().toString());
			criteria.append(ParameterNames.SELLER_ID).append("=").append(s);
		}
		if(c.getTipoVendedor()!=null) {
			if(first) {
				criteria = new StringBuilder();
				criteria.append("?");
				first = false;
			}else {
				criteria.append("&amp;");
			}
			s = ValidationUtils.isEmpty(c.getTipoVendedor().toString());
			criteria.append(ParameterNames.SELLER_TYPE).append("=").append(s);
		}
		if(c.getAnhoPublicacionMinimo()!=null) {
			if(first) {
				criteria = new StringBuilder();
				criteria.append("?");
				first = false;
			}else {
				criteria.append("&amp;");
			}
			s = ValidationUtils.isEmpty(ValidationUtils.dateToString(c.getAnhoPublicacionMinimo()));
			criteria.append(ParameterNames.MIN_PUBLICATION_YEAR).append("=").append(s);
		}
		if(c.getAnhoPublicacionMaximo()!=null) {
			if(first) {
				criteria = new StringBuilder();
				criteria.append("?");
				first = false;
			}else {
				criteria.append("&amp;");
			}
			s = ValidationUtils.isEmpty(ValidationUtils.dateToString(c.getAnhoPublicacionMaximo()));
			criteria.append(ParameterNames.MAX_PUBLICATION_YEAR).append("=").append(s);
		}
		if(c.getFormato()!=null) {
			if(first) {
				criteria = new StringBuilder();
				criteria.append("?");
				first = false;
			}else {
				criteria.append("&amp;");
			}
			s = ValidationUtils.isEmpty(c.getFormato().toString());
			criteria.append(ParameterNames.FORMAT).append("=").append(s);
		}
		if(c.getTipoTapa()!=null) {
			if(first) {
				criteria = new StringBuilder();
				criteria.append("?");
				first = false;
			}else {
				criteria.append("&amp;");
			}
			s = ValidationUtils.isEmpty(c.getTipoTapa().toString());
			criteria.append(ParameterNames.COVER_TYPE).append("=").append(s);
		}
		
		s = criteria.toString();
		return s;
	}

}
