<%@include file="/html/common/header.jsp"%>

	<a href="<%=ControllerPaths.USUARIO%>?action=<%=Actions.FIND_MESSAGES%>&amp;<%=ParameterNames.MESSAGE_TYPE%>=<%=ParameterNames.SENDER%>">
	<fmt:message key="enviados" bundle="${messages}"/></a>
	<a href="<%=ControllerPaths.USUARIO%>?action=<%=Actions.FIND_MESSAGES%>&amp;<%=ParameterNames.MESSAGE_TYPE%>=<%=ParameterNames.RECEIVER%>">
	<fmt:message key="recibidos" bundle="${messages}"/></a>
	<c:if test="${not empty results}">
		<c:forEach items="${results}" var="r">
			<c:url var="eliminar" scope="page" value="<%=ControllerPaths.NO_CONTEXT_USUARIO %>">
				<c:param name="<%=ParameterNames.ACTION %>" value="<%=Actions.REMOVE_MESSAGE%>"/>
				<c:param name="<%=ParameterNames.ID %>" value="${result.idMensaje}"/>
			</c:url>
			<c:url var="enviar" scope="page" value="<%=ControllerPaths.NO_CONTEXT_USUARIO %>">
				<c:param name="<%=ParameterNames.ACTION %>" value="<%=Actions.PRE_SEND_MESSAGE%>"/>
				<c:param name="<%=ParameterNames.ID %>" value="${result.usuarioEmisor}"/>
			</c:url>
			<p>${r.contenido}</p>
			<a href="${eliminar}"><fmt:message key="eliminar" bundle="${messages}"/></a>
			<c:if test="${r.usuarioEmisor ne sessionScope['user'].idUsuario}">
				<a href="${enviar}"><fmt:message key="responder" bundle="${messages}"/></a>
			</c:if>
		</c:forEach>
	</c:if>
<%@include file="/html/common/footer.jsp"%>