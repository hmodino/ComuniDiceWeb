<%@include file="/html/common/header.jsp"%>
	<div class="content">
		<h1><fmt:message key="compraRealizada" bundle="${messages}"/></h1>
		<c:if test="${not empty urlDownload}">
			<p id="descargas"><fmt:message key="descargas" bundle="${messages}"/>
			<c:forEach items="${urlDownload}" var="r">
				<a href="${r}" target="_blank"><fmt:message key="descarga" bundle="${messages}"/></a>
			</c:forEach>
		</c:if>
		</div>
<%@include file="/html/common/footer.jsp"%>