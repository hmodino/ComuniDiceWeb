<%@include file="/html/common/header.jsp"%>
	<div>
	<form action="<%=ControllerPaths.USUARIO%>" method="post">
	
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.FIND_FRIENDS_BY%>"/>
			<input type="text" name="<%=ParameterNames.SEARCH_BOX%>" 
				value="<%=ValidationUtils.getParameter(request, ParameterNames.SEARCH_BOX)%>" />
				<p><fmt:message key = "buscar-por" bundle="${messages}"/></p>
				<select>
					<option name="<%=ParameterNames.SEARCH_BY%>"value="<%=ParameterNames.EMAIL%>">Email</option>
					<option name="<%=ParameterNames.SEARCH_BY%>"value="<%=ParameterNames.USER_NAME%>">Nombre</option>
				</select>
			<input type="submit" value="Buscar" />
		</form>
</div>	
	<ul>
	<c:if test="${not empty results}">
	
	<c:forEach items="${results}" var="r">
		<li>${r.nombreUsuario}</li>
		<li>
		<c:url var="urlEliminar" scope="page" value="usuario">
		<c:param name="action" value="<%=Actions.REMOVE_FRIEND%>"/>
		<c:param name="<%=ParameterNames.ID%>" value="${r.idUsuario}"/>
		</c:url>
		<c:url var="urlMensaje" scope="page" value="usuario">
		<c:param name="action" value="<%=Actions.PRE_SEND_MESSAGE%>"/>
		<c:param name="<%=ParameterNames.ID%>" value="${r.idUsuario}"/>
		</c:url>
		<a href="${urlEliminar}">
		Eliminar</a>
		<a href="${urlMensaje}">Enviar Mensaje</a>
	</c:forEach>
	</c:if>
	</ul>

<%@include file="/html/common/footer.jsp"%>