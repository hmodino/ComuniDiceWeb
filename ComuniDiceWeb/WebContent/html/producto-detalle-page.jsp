<%@page import="java.util.*" %>
<%@include file="/html/common/header.jsp"%>

<c:if test="${not empty result}" var="r">
	<div>
		<ul>
		<li>${r.nombre}</li>
		<li>${r.descripcion}</li>
		<li>${r.precio}</li>
		<li>${r.fechaEntrada}</li>
		<li>${r.stock}</li>
		<li>${r.valoracion}</li>
		<c:forEach items="${r.comentarios}" var="c">
			<li>${c.fecha}</li>
			<li>${c.contenido}</li>
		</c:forEach>
		</ul>
	</div>
</c:if>