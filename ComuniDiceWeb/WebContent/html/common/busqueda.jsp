<%@ page import="java.util.List" %>
<%@page import="com.comunidice.web.util.*, com.comunidice.web.model.*" %>
<div>
	<form action="<%=ControllerPaths.SEARCH%>" method="post">
	
			<input type="text" name="<%=ParameterNames.SEARCH_BOX%>" 
				value="<%=ValidationUtils.getParameter(request, ParameterNames.SEARCH_BOX)%>" />
			<p><fmt:message key = "buscarPor" bundle="${messages}"/></p>
			<input type="radio" name=<%=ParameterNames.SEARCH_TYPE %> value="<%=ParameterNames.SEARCH_PRODUCT%>" checked="checked">
				<fmt:message key="producto" bundle="${messages}"/>
			<input type="radio" name=<%=ParameterNames.SEARCH_TYPE %> value="<%=ParameterNames.SEARCH_USER%>">
				<fmt:message key="usuario" bundle="${messages}"/>
			<input type="radio" name="<%=ParameterNames.SEARCH_BY %>" value="<%=ParameterNames.EMAIL%>">
				<fmt:message key="email" bundle="${messages}"/>
			<input type="radio" name="<%=ParameterNames.SEARCH_BY %>" value="<%=ParameterNames.USER_NAME%>" checked="checked">
				<fmt:message key="nombre" bundle="${messages}"/>
				<input type="text" name="<%=ParameterNames.CATEGORY_ID %>"><fmt:message key="categoria" bundle="${messages}"/>
				<input type="text" name="<%=ParameterNames.MIN_PRICE %>"><fmt:message key="precioMin" bundle="${messages}"/>
				<input type="text" name="<%=ParameterNames.MAX_PRICE %>"><fmt:message key="precioMax" bundle="${messages}"/>
				<input type="text" name="<%=ParameterNames.MIN_DATE %>"><fmt:message key="fechaEntradaMin" bundle="${messages}"/>
				<input type="text" name="<%=ParameterNames.MAX_DATE %>"><fmt:message key="fechaEntradaMax" bundle="${messages}"/>
				<input type="text" name="<%=ParameterNames.FAVS %>"><fmt:message key="favoritos" bundle="${messages}"/>
				<input type="text" name="<%=ParameterNames.RATING %>"><fmt:message key="puntiacion" bundle="${messages}"/>
				<input type="text" name="<%=ParameterNames.SELLER_TYPE %>"><fmt:message key="tipoVendedor" bundle="${messages}"/>
				<input type="text" name="<%=ParameterNames.MIN_PUBLICATION_YEAR %>"><fmt:message key="publicacionMin" bundle="${messages}"/>
				<input type="text" name="<%=ParameterNames.MAX_PUBLICATION_YEAR %>"><fmt:message key="publicacionMax" bundle="${messages}"/>
				<input type="text" name="<%=ParameterNames.FORMAT %>"><fmt:message key="formato" bundle="${messages}"/>
				<input type="text" name="<%=ParameterNames.COVER_TYPE %>"><fmt:message key="tipoTapa" bundle="${messages}"/>
			<input type="submit" value="<fmt:message key="buscar" bundle="${messages}"/>" />
		</form>
</div>	