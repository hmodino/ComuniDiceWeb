<%@include file="/html/common/header.jsp"%>
	<div id="busquedaAmigos" class="content">
		<form action="<%=ControllerPaths.USUARIO%>" method="post">
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.FIND_FRIENDS_BY%>"/>
			<input type="text" name="<%=ParameterNames.SEARCH_BOX%>"/>
				<p><fmt:message key = "buscarPor" bundle="${messages}"/></p>
				<select name="<%=ParameterNames.SEARCH_BY%>">
					<option value="<%=ParameterNames.EMAIL%>">
						<fmt:message key="email" bundle="${messages}"/></option>
					<option value="<%=ParameterNames.USER_NAME%>">
						<fmt:message key="nombre" bundle="${messages}"/></option>
				</select>
			<input type="submit" value="<fmt:message key="enviar" bundle="${messages}"/>" />
		</form>
	</div>	
	<div id="resultadosAmigos" class="content">
		<h1><fmt:message key="amigos" bundle="${messages}"/></h1>
		<c:if test="${not empty results}">
			<c:forEach items="${results}" var="r">
				<div class="resultadoSinFondo">
					<p>${r.nombreUsuario}</p>
					<p>${r.descripcion}</p>
					<c:url var="urlEliminar" scope="page" value="<%=ControllerPaths.NO_CONTEXT_USUARIO %>">
						<c:param name="<%=ParameterNames.ACTION %>" value="<%=Actions.REMOVE_FRIEND%>"/>
						<c:param name="<%=ParameterNames.ID%>" value="${r.idUsuario}"/>
					</c:url>
					<c:url var="urlMensaje" scope="page" value="<%=ControllerPaths.NO_CONTEXT_USUARIO %>">
						<c:param name="<%=ParameterNames.ACTION %>" value="<%=Actions.PRE_SEND_MESSAGE%>"/>
						<c:param name="<%=ParameterNames.ID%>" value="${r.idUsuario}"/>
					</c:url>
					<a href="${urlEliminar}"><fmt:message key="eliminar" bundle="${messages}"/></a>
					<a href="${urlMensaje}"><fmt:message key="enviarMensaje" bundle="${messages}"/></a>
				</div>
			</c:forEach>
		</c:if>
	</div>
<%@include file="/html/common/footer.jsp"%>