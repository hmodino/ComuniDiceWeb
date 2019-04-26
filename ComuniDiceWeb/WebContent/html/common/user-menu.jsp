<%@ page import="com.rollanddice.comunidice.model.*, com.comunidice.web.util.*, com.comunidice.web.model.*" %>
<div id="user-menu">
	
	<c:choose>
		<c:when test="${empty sessionScope['user']}">
			<div id="usuario">
				<a href="/ComuniDiceWeb<%=ViewPaths.LOGIN%>"><fmt:message key="entrar" bundle="${messages}"/></a>
				<a href="/ComuniDiceWeb<%=ViewPaths.SIGNUP%>"><fmt:message key="registro" bundle="${messages}"/></a>
			</div>
		</c:when>
		<c:otherwise>
				<!-- usuario autenticado -->
				<div id="usuario">
					<p>${sessionScope['user'].nombre}</p>
					<a href="<%=ControllerPaths.USUARIO%>?action=<%=Actions.LOGOUT%>">
						<fmt:message key="salir" bundle="${messages}"/></a>
					<a href="<%=ControllerPaths.USUARIO%>?action=<%=Actions.PROFILE_VIEW %>">
						<fmt:message key="perfil" bundle="${messages}"/></a>
					<a href="<%=ControllerPaths.USUARIO%>?action=<%=Actions.FIND_FRIENDS %>">
						<fmt:message key="amigos" bundle="${messages}"/></a>
					<a href="<%=ControllerPaths.USUARIO%>?action=<%=Actions.FIND_MESSAGES %>">
						<fmt:message key="mensajes" bundle="${messages}"/></a>
					<a href="/ComuniDiceWeb<%=ViewPaths.CART%>"><fmt:message key="carrito" bundle="${messages}"/></a>
				</div>		
		</c:otherwise>
	</c:choose>
</div>