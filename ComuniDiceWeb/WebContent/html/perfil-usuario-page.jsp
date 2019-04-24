<%@include file="/html/common/header.jsp"%>
<div id="home">
	<c:if test="${not empty sessionScope['user']}">
		<a href="/ComuniDiceWeb<%=ViewPaths.EDIT_PROFILE%>"><fmt:message key="editarPerfil" bundle="${messages}"/></a>
		<p>${sessionScope['user'].nombreUsuario}</p>
		<p>${sessionScope['user'].descripcion}</p>
		<p>${sessionScope['user'].fechaAlta}</p>
		<p>${sessionScope['user'].nombre}</p>
		<p>${sessionScope['user'].apellido1}</p>
		<p>${sessionScope['user'].apellido2}</p>
		<p>${sessionScope['user'].email}</p>
		<p>${sessionScope['user'].telefono}</p>
	</c:if>
</div>
<%@include file="/html/common/footer.jsp"%>