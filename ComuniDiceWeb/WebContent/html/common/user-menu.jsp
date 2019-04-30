<%@ page import="com.rollanddice.comunidice.model.*, com.comunidice.web.util.*, com.comunidice.web.model.*" %>
<div id="user-menu">
	
	<c:choose>
		<c:when test="${empty sessionScope['user']}">
			<div id="usuario">
				<a href="/ComuniDiceWeb<%=ViewPaths.LOGIN%>"><fmt:message key="entrar" bundle="${messages}"/></a>
				<a href="<%=ControllerPaths.USUARIO%>?<%=ParameterNames.ACTION%>=<%=Actions.PRE_SIGN_UP%>"><fmt:message key="registro" bundle="${messages}"/></a>
			</div>
		</c:when>
		<c:otherwise>
		
				<div id="usuario">
				<div class="menudes">
                    <p onclick="desplegarMenu()" class="dropbtn">${sessionScope['user'].nombreUsuario}</p>
                    <div id="meumenudes" class="menudes-contido">
                       <a href="<%=ControllerPaths.USUARIO%>?action=<%=Actions.PROFILE_VIEW %>">
						<fmt:message key="perfil" bundle="${messages}"/></a>   
					<a href="<%=ControllerPaths.USUARIO%>?action=<%=Actions.FIND_FRIENDS %>">
						<fmt:message key="amigos" bundle="${messages}"/></a>
					<a href="<%=ControllerPaths.USUARIO%>?action=<%=Actions.FIND_MESSAGES %>">
						<fmt:message key="mensajes" bundle="${messages}"/></a>
					<a href="/ComuniDiceWeb<%=ViewPaths.CART%>"><fmt:message key="carrito" bundle="${messages}"/></a>
					<a href="<%=ControllerPaths.USUARIO%>?action=<%=Actions.FAVOURITE_DETAILS%>">
						<fmt:message key="favoritos" bundle="${messages}"/></a>
					<a href="<%=ControllerPaths.USUARIO%>?action=<%=Actions.LOGOUT%>">
						<fmt:message key="salir" bundle="${messages}"/></a>
                    </div>
        
        
                </div>
				</div>		
		</c:otherwise>
	</c:choose>
	<c:url var="en" scope="page" value="<%=ControllerPaths.NO_CONTEXT_USUARIO %>">
				<c:param name="<%=ParameterNames.ACTION %>" value="<%=Actions.CHANGE_LOCALE%>"/>
				<c:param name="<%=ParameterNames.LANGUAGE%>" value="en_GB"/>
				<c:if test="${not empty url}">
					<c:param name="<%=AttributeNames.URL%>" value="${url}"/>
				</c:if>
			</c:url>
			<c:url var="es" scope="page" value="<%=ControllerPaths.NO_CONTEXT_USUARIO %>">
				<c:param name="<%=ParameterNames.ACTION %>" value="<%=Actions.CHANGE_LOCALE%>"/>
				<c:param name="<%=ParameterNames.LANGUAGE%>" value="es_ES"/>
			</c:url>
		<div id="language">	
			<a href="${en}">EN</a>
			<a href="${es}">ES</a>
		</div>
</div>