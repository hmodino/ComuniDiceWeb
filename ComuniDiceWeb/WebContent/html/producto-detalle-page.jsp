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
	<div id="vistaDetalleProducto" class="content">
		<p>${result.nombre}</p>
		<p id="descripcion"><fmt:message key="descripcion" bundle="${messages}"/></p>
		<p>${result.descripcion}</p>
		<p id="precio"><fmt:message key="precio" bundle="${messages}"/></p>
		<p>${result.precio}</p>
		<p id="fechaEntrada"><fmt:message key="fechaEntrada" bundle="${messages}"/></p>
		<p>${result.fechaEntrada}</p>
		<p id="valoracion"><fmt:message key="valoracion" bundle="${messages}"/></p>
		<p>${result.valoracion}</p>
		<c:if test="${game}">
			<p id="anhoPublicacion"><fmt:message key="anhoPublicacion" bundle="${messages}"/></p>
			<p>${result.anhoPublicacion}</p>
			<p id="formato"><fmt:message key="formato" bundle="${messages}"/></p>
			<c:if test="${result.formato eq 0}">
				<p><fmt:message key="fisico" bundle="${messages}"/></p>
			</c:if>
			<c:if test="${result.formato eq 1}">
				<p>PDF</p>
			</c:if>
			<p id="paginas"><fmt:message key="paginas" bundle="${messages}"/></p>
			<p>${result.paginas}</p>
			<p id="tipoTapa"><fmt:message key="tipoTapa" bundle="${messages}"/></p>
			<c:if test="${result.tipoTapa eq 0}">
				<p><fmt:message key="tapaBlanda" bundle="${messages}"/></p>
			</c:if>
			<c:if test="${result.tipoTapa eq 1}">
				<p><fmt:message key="tapaDura" bundle="${messages}"/></p>
			</c:if>
		</c:if>
		<a href="${fav}"><fmt:message key="favorito" bundle="${messages}"/></a>
		<a href="${cart}"><fmt:message key="anadirCarrito" bundle="${messages}"/></a>
		<p id="valoracion"><fmt:message key="valora" bundle="${messages}"/></p>
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
		<c:if test="${not empty result.comentarios}">
			<p id="comentarios"><fmt:message key="comentarios" bundle="${messages}"/></p>
			<c:forEach items="${result.comentarios}" var="c">
				<div class="resultadoSinFondo">
					<p>${c.nombreUsuario}</p>
					<p>${c.fecha}</p>
					<p>${c.contenido}</p>
					<c:if test="${c.usuario eq user.idUsuario}">
						<c:url var="deleteComment" scope="page" value="producto">
							<c:param name="<%=ParameterNames.ACTION %>" value="<%=Actions.DELETE_COMMENT%>"/>
							<c:param name="<%=ParameterNames.ID %>" value="${c.idComentario}"/>
						</c:url>
						<a href="${deleteComment}"><fmt:message key="eliminar" bundle="${messages}"/></a>
					</c:if>
				</div>
			</c:forEach>
		</c:if>
		<form id="formComentario" action="<%=ControllerPaths.PRODUCTO %>" method="post">
			<fmt:message key="comenta" bundle="${messages}"/>
			<input type="hidden" name="<%=ParameterNames.ACTION %>" value="<%=Actions.CREATE_COMMENT %>"/>
			<input type="hidden" name="<%=ParameterNames.ID%>" value="${result.idProducto}"/>
			<textarea rows="10" cols="75" name="<%=ParameterNames.CONTENT%>"></textarea>
			<input id="enviarComentario" type="submit" value="<fmt:message key="enviar" bundle="${messages}"/>"/>
		</form>
	</div>
</c:if>
<%@include file="/html/common/footer.jsp"%>