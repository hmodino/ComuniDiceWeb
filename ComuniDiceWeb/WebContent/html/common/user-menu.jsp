<%@ page import="com.rollanddice.comunidice.model.*, com.comunidice.web.util.*, com.comunidice.web.model.*" %>
<div id="user-menu">
	<%
		Usuario u = (Usuario) request.getSession().getAttribute(SessionAttributeNames.USER);
		if (u == null) {
		
			%><a href="/ComuniDiceWeb<%=ViewPaths.LOGIN%>">Entrar</a>
			<a href="/ComuniDiceWeb<%=ViewPaths.SIGNUP%>">Registro</a><%	
		
		} else {
			%>	
			<!-- usuario autenticado -->
			<div id="usuario">
				<p><%=u.getNombre()%></p>
				<a href="/ComuniDiceWeb/usuario?action=<%=Actions.LOGOUT%>">Salir</a>
				<a href="/ComuniDiceWeb/usuario?action=<%=Actions.PROFILE_VIEW %>">Perfil</a>
				<a href="/ComuniDiceWeb/usuario?action=<%=Actions.FIND_FRIENDS %>">Amigos</a>
			</div>		
			<%
		}
	%>	
</div>