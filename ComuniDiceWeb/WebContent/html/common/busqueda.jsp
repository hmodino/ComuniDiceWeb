<%@ page import="java.util.List" %>
<%@page import="com.comunidice.web.util.*, com.comunidice.web.model.*" %>
<div>
	<form action="<%=ControllerPaths.USUARIO%>" method="post">
	
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.SEARCH_USERS%>"/>
			<input type="text" name="<%=ParameterNames.SEARCH_BOX%>" 
				value="<%=ValidationUtils.getParameter(request, ParameterNames.SEARCH_BOX)%>" />
			<p><fmt:message key = "buscar-por" bundle="${messages}"/></p>
			<input type="radio" name="<%=ParameterNames.SEARCH_BY %>" value="<%=ParameterNames.EMAIL%>"><fmt:message key="email" bundle="${messages}"/>
			<input type="radio" name="<%=ParameterNames.SEARCH_BY %>" value="<%=ParameterNames.USER_NAME%>"><fmt:message key="nombre" bundle="${messages}"/>
			<input type="submit" value="Buscar" />
		</form>
</div>	