package com.comunidice.web.util;

import com.rollanddice.comunidice.model.Criteria;

public class SelectClass {
	
	public static final Boolean game(Criteria c) {
		Integer game = 3;
		if(c.getIdVendedor()!=null||c.getTipoVendedor()!=null||c.getAnhoPublicacionMinimo()!=null||c.getAnhoPublicacionMaximo()!=null
				||c.getFormato()!=null||c.getTipoTapa()!=null || game.equals(c.getIdCategoria())) {
			return true;
		}else {
			return false;
		}
	}
}
