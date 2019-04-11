<%@ page import="com.rollanddice.comunidice.model.*, com.comunidice.web.util.*, com.comunidice.web.model.*" %>
<div id="user-menu">
	
	<c:choose>
		<c:when test="${empty sessionScope['user']}">
			<a href="/ComuniDiceWeb<%=ViewPaths.LOGIN%>">Entrar</a>
			<a href="/ComuniDiceWeb<%=ViewPaths.SIGNUP%>">Registro</a>
		</c:when>
		<c:otherwise>
				<!-- usuario autenticado -->
				<div id="usuario">
					<p>${sessionScope['user'].nombre}</p>
					<a href="<%=ControllerPaths.USUARIO%>?action=<%=Actions.LOGOUT%>">Salir</a>
					<a href="<%=ControllerPaths.USUARIO%>?action=<%=Actions.PROFILE_VIEW %>">Perfil</a>
					<a href="<%=ControllerPaths.USUARIO%>?action=<%=Actions.FIND_FRIENDS %>">Amigos</a>
					<a href="<%=ControllerPaths.USUARIO%>?action=<%=Actions.FIND_MESSAGES %>">Mensajes</a>
				</div>		
		</c:otherwise>
	</c:choose>
</div>