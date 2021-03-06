<%@page import="java.util.*" %>
<%@include file="/html/common/header.jsp"%>	
	<c:if test="${not empty sessionScope['shoppingCart']}">
		<c:if test="${not empty sessionScope['shoppingCart'].line }">
			<div id="carrito" class="content">
				<c:url var="clear" scope="page" value="<%=ControllerPaths.NO_CONTEXT_PRODUCTO %>">
					<c:param name="<%=ParameterNames.ACTION %>" value="<%=Actions.CLEAR_CART%>"/>
				</c:url>	
				<h1><fmt:message key="carrito" bundle="${messages}"/></h1>
				<c:forEach items="${sessionScope['shoppingCart'].line}" var="c" varStatus="loop">
					<div class="resultadoSinFondo">
						<c:url var="remove" scope="page" value="<%=ControllerPaths.NO_CONTEXT_PRODUCTO%>">
							<c:param name="<%=ParameterNames.ACTION %>" value="<%=Actions.REMOVE_FROM_CART%>"/>
							<c:param name="<%=ParameterNames.ID %>" value="${c.product.idProducto}"/>
						</c:url>
						<c:url var="modify" scope="page" value="<%=ControllerPaths.NO_CONTEXT_PRODUCTO %>">
							<c:param name="<%=ParameterNames.ACTION %>" value="<%=Actions.MODIFY_QUANTITY %>"/>
							<c:param name="<%=ParameterNames.INDEX %>" value="${loop.index}"/>
						</c:url>
						<p>${c.product.nombre}</p>
						<p><fmt:message key="precio" bundle="${messages}"/></p>
						<p>${c.product.precio}</p>
						<p><fmt:message key="cantidad" bundle="${messages}"/></p>
						<p>${c.quantity}</p>
						<p><a href="${remove}"><fmt:message key="eliminar" bundle="${messages}"/></a></p>
					</div>
				</c:forEach>
				<p><fmt:message key="costesEnvio" bundle="${messages}"/>${sessionScope['shoppingCart'].shippingCosts}</p>
				<p><fmt:message key="total" bundle="${messages}"/>${sessionScope['shoppingCart'].total}</p>
				<p><a href="/ComuniDiceWeb<%=ViewPaths.BUY%>"><fmt:message key="comprar" bundle="${messages}"/></a></p>
				<p><a href="${clear}"><fmt:message key="vaciar" bundle="${messages}"/></a></p>
			</div>
		</c:if>
	</c:if>
<%@include file="/html/common/footer.jsp"%>