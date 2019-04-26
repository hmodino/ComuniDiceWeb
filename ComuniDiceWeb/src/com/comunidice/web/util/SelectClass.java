package com.comunidice.web.util;

import com.rollanddice.comunidice.model.Criteria;

public class SelectClass {
	
	public static final Boolean game(Criteria c) {
		
		if(c.getIdVendedor()!=null||c.getTipoVendedor()!=null||c.getAnhoPublicacionMinimo()!=null||c.getAnhoPublicacionMaximo()!=null
				||c.getFormato()!=null||c.getTipoTapa()!=null) {
			System.out.println(c.getIdVendedor()+" "+c.getTipoVendedor()+" "+c.getAnhoPublicacionMaximo()+" "+c.getAnhoPublicacionMinimo()+" "+c.getFormato()+" "+c.getTipoTapa());
			return true;
		}else {
			return false;
		}
	}
}
