<%@ page import="java.util.List" %>
<%@page import="com.comunidice.web.util.*, com.comunidice.web.model.*" %>
<div>
	<form action="<%=ControllerPaths.USUARIO%>" method="post">
	
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.SEARCH_USERS%>"/>
			<input type="text" name="<%=ParameterNames.SEARCH_BOX%>" 
				value="<%=ParamsUtils.getParameter(request, ParameterNames.SEARCH_BOX)%>" />
				<p>buscar por:</p>
			<input type="radio" name="<%=ParameterNames.SEARCH_BY %>" value="<%=ParameterNames.EMAIL%>">Email
			<input type="radio" name="<%=ParameterNames.SEARCH_BY %>" value="<%=ParameterNames.USER_NAME%>">Nombre
			<input type="submit" value="Buscar" />
		</form>
</div>	