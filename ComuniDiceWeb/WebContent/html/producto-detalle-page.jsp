<%@page import="java.util.*" %>
<%@include file="/html/common/header.jsp"%>

<c:if test="${not empty result}">
	<c:url var="fav" scope="page" value="producto">
		<c:param name="<%=ParameterNames.ACTION %>" value="<%=Actions.FAVOURITE%>"/>
		<c:param name="<%=ParameterNames.ID %>" value="${result.idProducto}"/>
		<c:param name="<%=ParameterNames.FAVOURITE %>" value="<%=ParameterNames.TRUE %>"></c:param>
	</c:url>
	<c:url var="cart" scope="page" value="producto">
		<c:param name="<%=ParameterNames.ACTION %>" value="<%=Actions.ADD_TO_CART %>"/>
		<c:param name="<%=ParameterNames.ID %>" value="${result.idProducto}"/>
	</c:url>
	<div>
		<ul>
		<li>${result.nombre}</li>
		<li>${result.descripcion}</li>
		<li>${result.precio}</li>
		<li>${result.fechaEntrada}</li>
		<li>${result.stock}</li>
		<li>${result.valoracion}</li>
		<c:if test="${not empty result.comentarios}">
			<c:forEach items="${result.comentarios}" var="c">
				<li>${c.fecha}</li>
				<li>${c.contenido}</li>
			</c:forEach>
		</c:if>
		<li><a href="${fav}"><fmt:message key="favorito" bundle="${messages}"/></a></li>
		<li><a href="${cart}"><fmt:message key="anadirCarrito" bundle="${messages}"/></a></li>
		</ul>
		<p><fmt:message key="valoracion" bundle="${messages}"/></p>
		<form action="<%=ControllerPaths.PRODUCTO %>" method="post">
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.FAVOURITE%>"/>
			<input type="hidden" name="<%=ParameterNames.ID%>" value="${result.idProducto}"/>
			<select name="<%=ParameterNames.RATE %>" onchange="this.form.submit()">
				<option value="0.0">0</option>
				<option value="0.5">0.5</option>
				<option value="1.0">1</option>
				<option value="1.5">1.5</option>
				<option value="2.0">2</option>
				<option value="2.5">2.5</option>
				<option value="3.0">3</option>
				<option value="3.5">3.5</option>
				<option value="4.0">4</option>
				<option value="4.5">4.5</option>
				<option value="5.0">5</option>
			</select>
		</form>
	</div>
</c:if>