<%@include file="/html/common/header.jsp"%>
	<div id="resultadosMensajes" class="content">
		<a href="<%=ControllerPaths.USUARIO%>?action=<%=Actions.FIND_MESSAGES%>&amp;<%=ParameterNames.MESSAGE_TYPE%>=<%=ParameterNames.SENDER%>">
		<h1><fmt:message key="enviados" bundle="${messages}"/></h1></a>
		<a href="<%=ControllerPaths.USUARIO%>?action=<%=Actions.FIND_MESSAGES%>&amp;<%=ParameterNames.MESSAGE_TYPE%>=<%=ParameterNames.RECEIVER%>">
		<h1><fmt:message key="recibidos" bundle="${messages}"/></h1></a>
		<c:if test="${not empty results}">
			<c:forEach items="${results}" var="r">
				<div class="resultadoSinFondo">
					<c:url var="eliminar" scope="page" value="<%=ControllerPaths.NO_CONTEXT_USUARIO %>">
						<c:param name="<%=ParameterNames.ACTION %>" value="<%=Actions.REMOVE_MESSAGE%>"/>
						<c:param name="<%=ParameterNames.ID %>" value="${r.idMensaje}"/>
					</c:url>
					<c:url var="enviar" scope="page" value="<%=ControllerPaths.NO_CONTEXT_USUARIO %>">
						<c:param name="<%=ParameterNames.ACTION %>" value="<%=Actions.PRE_SEND_MESSAGE%>"/>
						<c:param name="<%=ParameterNames.ID %>" value="${r.usuarioEmisor}"/>
					</c:url>
					<p>${r.nombre}</p>
					<p>${r.contenido}</p>
					<a href="${eliminar}"><fmt:message key="eliminar" bundle="${messages}"/></a>
					<c:if test="${r.usuarioEmisor ne sessionScope['user'].idUsuario}">
						<a href="${enviar}"><fmt:message key="responder" bundle="${messages}"/></a>
					</c:if>
				</div>
			</c:forEach>
		</c:if>
		</div>
<%@include file="/html/common/footer.jsp"%>