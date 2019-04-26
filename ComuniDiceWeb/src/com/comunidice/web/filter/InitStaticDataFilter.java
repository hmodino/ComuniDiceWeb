package com.comunidice.web.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.comunidice.web.util.SetAttribute;
import com.rollanddice.comunidice.model.Categoria;
import com.rollanddice.comunidice.service.impl.CategoriaServiceImpl;
import com.rollanddice.comunidice.service.spi.CategoriaService;

public class InitStaticDataFilter implements Filter {

	private CategoriaService service = null;
    public InitStaticDataFilter() {
    	service = new CategoriaServiceImpl();
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = ((HttpServletRequest) request);
		
		List<Categoria> cs = null;
		try {
			cs = service.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		SetAttribute.setCatgories(httpRequest, cs);

		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
