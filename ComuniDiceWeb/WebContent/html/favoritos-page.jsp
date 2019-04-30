<%@include file="/html/common/header.jsp"%>
	<div id="favoritoPage" class="content">
		<h1><fmt:message key="favoritos" bundle="${messages}"/></h1>
		<c:if test="${not empty results}">
			<c:forEach items="${results}" var="r">
				<c:url var="productDetail" scope="page" value="producto">
					<c:param name="<%=ParameterNames.ACTION %>" value="<%=Actions.DETAIL_VIEW%>"/>
					<c:param name="<%=ParameterNames.ID %>" value="${r.producto}"/>
				</c:url>
				<div class="resultadoSinFondo">
					<a href="${productDetail}">${r.nombreProducto}</a>
				</div>
			</c:forEach>
		</c:if>
	</div>
<%@include file="/html/common/footer.jsp"%>