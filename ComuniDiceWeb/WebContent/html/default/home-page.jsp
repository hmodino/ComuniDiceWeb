<%@include file="/html/common/header.jsp"%>
<div id="home">
	<h1 id="bienvenida"><fmt:message key = "bienvenida" bundle="${messages}"/></h1>
	<div id="busquedaPorDefecto" class="resultados">
		<p>
			<c:if test="${not empty total}">
				<fmt:message key="encontrados" bundle="${messages}">
					<fmt:param value="${total}"/>
				</fmt:message>		
			</c:if>
		</p>
		
		<!-- Resultados -->
		<c:if test="${not empty results}">
			<!-- Listado -->
				<c:forEach items="${results}" var="r">
					<div class="resultadoProductos">
						<c:url var="urlDetail" scope="page" value="producto">
							<c:param name="<%=ParameterNames.ACTION %>" value="<%=Actions.DETAIL_VIEW%>"/>
							<c:param name="<%=ParameterNames.ID %>" value="${r.idProducto}"/>
						</c:url>			
						<a href="${urlDetail}">${r.nombre}</a>	
						<p>${r.descripcion}</p>		
					</div>
				</c:forEach>
		
			<!-- Paginacion  -->
			<p><center>
		
			<!-- A la primera pagina -->
			<c:if test="${page > 1}">
				<a href="${url}&amp;page=1">
					<fmt:message key="primera" bundle="${messages}"/>
				</a>
				&nbsp;&nbsp;
			</c:if>
		
			<!-- A la anterior pagina -->
			<c:if test="${page > 1}">
				<a href="${url}&page=${page - 1}">
					<fmt:message key="anterior" bundle="${messages}"/>
				</a>
				&nbsp;&nbsp;
			</c:if>
		
			<c:if test="${totalPages > 1}">	
			
				<c:forEach begin="${firstPagedPage}" end="${lastPagedPage}" var="i">
					<c:choose>
					  <c:when test="${page != i}">
							&nbsp;<a href="${url}&amp;page=${i}"><b>${i}</b></a>&nbsp;
					  </c:when>
					  <c:otherwise>
							&nbsp;<b>${i}</b>&nbsp;
					  </c:otherwise>
					</c:choose>
				</c:forEach>
		
			</c:if>
		
			<!-- A la siguiente página -->	
			<c:if test="${page < totalPages}">
				&nbsp;&nbsp;		
				<a href="${url}&page=${page + 1}">
					<fmt:message key="siguiente" bundle="${messages}"/>
				</a>			
			</c:if>	
			<!-- A la ultima página -->
			<c:if test="${page != totalPages}">
				&nbsp;&nbsp;<a href="${url}&page=${totalPages}"><fmt:message key="ultima" bundle="${messages}"/></a>
			</c:if>		
		
			</center></p>
			
		</c:if>
	</div>
	</div>
<%@include file="/html/common/footer.jsp"%>

