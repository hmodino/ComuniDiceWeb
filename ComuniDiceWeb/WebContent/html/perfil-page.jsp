<%@include file="/html/common/header.jsp"%>
<div id="vistaDetalleUsuario" class="content">
	<c:if test="${not empty user}">
		<c:url var="agregar" scope="page" value="<%=ControllerPaths.NO_CONTEXT_USUARIO %>">
			<c:param name="<%=ParameterNames.ACTION %>" value="<%=Actions.ADD_FRIEND%>"/>
			<c:param name="<%=ParameterNames.ID %>" value="${user.idUsuario}"/>
		</c:url>
		<c:url var="enviarMensaje" scope="page" value="<%=ControllerPaths.NO_CONTEXT_USUARIO %>">
			<c:param name="<%=ParameterNames.ACTION %>" value="<%=Actions.PRE_SEND_MESSAGE%>"/>
			<c:param name="<%=ParameterNames.ID %>" value="${user.idUsuario}"/>
		</c:url>
		<p>${user.nombreUsuario}</p>
		<p>${user.descripcion}</p>
		<p>${user.fechaAlta}</p>
		<a href="${agregar}"><fmt:message key="agregar" bundle="${messages}"/></a>
		<a href="${enviarMensaje}"><fmt:message key="enviarMensaje" bundle="${messages}"/></a>
	</c:if>
</div>
<%@include file="/html/common/footer.jsp"%>