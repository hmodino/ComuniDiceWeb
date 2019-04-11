<%@page import="java.util.*" %>
<%@include file="/html/common/header.jsp"%>

<!-- Total de resultados  -->
<p>
	<c:if test="${not empty total}">
		<fmt:message key="encontrados" bundle="${messages}">
			<fmt:param value="${total}"></fmt:param>
		</fmt:message>		
	</c:if>
</p>

<!-- Resultados -->
<c:if test="${not empty results}">
	<!-- Listado -->
	<ul>
		<c:forEach items="${results}" var="r">
			<c:url var="urlDetail" scope="page" value="result">
				<c:param name="<%=ParameterNames.ACTION %>" value="<%=Actions.DETAIL_VIEW%>"/>
				<c:param name="<%=ParameterNames.ID %>" value="${r.id}"/>
			</c:url>			
			<li><a href="${urlDetail}">${r.nombre}</a></li>			
		</c:forEach>
	</ul>

	<!-- Paginacion  -->
	<p><center>

	<!-- A la primera pagina -->
	<c:if test="${page > 1}">
		<a href="${url}&amp;page=1">
			<fmt:message key="Primera" bundle="${messages}"/>
		</a>
		&nbsp;&nbsp;
	</c:if>

	<!-- A la anterior pagina -->
	<c:if test="${page > 1}">
		<a href="${url}&page=${page - 1}">
			<fmt:message key="Anterior" bundle="${messages}"/>
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
			<fmt:message key="Siguiente" bundle="${messages}"/>
		</a>			
	</c:if>	
	<!-- A la ultima página -->
	<c:if test="${page != totalPages}">
		&nbsp;&nbsp;<a href="${url}&page=${totalPages}"><fmt:message key="Ultima" bundle="${messages}"/></a>
	</c:if>		

	</center></p>
	
</c:if>
<%@include file="/html/common/footer.jsp"%>