package com.comunidice.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.comunidice.web.util.Actions;
import com.comunidice.web.util.ControllerPaths;
import com.comunidice.web.util.ParameterNames;
import com.comunidice.web.util.RedirectOrForward;

public class DefaultSearchFilter implements Filter {

    public DefaultSearchFilter() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = ((HttpServletRequest) request);
		HttpServletResponse httpResponse = ((HttpServletResponse) response);
		String target = ControllerPaths.NO_CONTEXT_SEARCH.concat("?").concat(ParameterNames.SEARCH_TYPE).concat("=")
					.concat(ParameterNames.SEARCH_PRODUCT).concat("&").concat(ParameterNames.DEFAULT)
					.concat("=").concat(ParameterNames.TRUE).concat("&").concat(ParameterNames.RATING)
					.concat("=").concat("2");
		RedirectOrForward.send(httpRequest, httpResponse, true, target, true);
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}
