<%@page import="java.util.*" %>
<%@include file="/html/common/header.jsp"%>
	<c:if test="${not empty result}">
	<div id="resultadoUsuarios" class="content">
			<c:url var="urlDetailView" scope="page" value="<%=ControllerPaths.NO_CONTEXT_USUARIO %>">
				<c:param name="<%=ParameterNames.ACTION %>" value="<%=Actions.DETAIL_VIEW%>"/>
				<c:param name="<%=ParameterNames.ID%>" value="${result.idUsuario}"/>
			</c:url>
			<c:url var="urlAdd" scope="page" value="<%=ControllerPaths.NO_CONTEXT_USUARIO %>">
				<c:param name="<%=ParameterNames.ACTION %>" value="<%=Actions.ADD_FRIEND%>"/>
				<c:param name="<%=ParameterNames.ID%>" value="${result.idUsuario}"/>
			</c:url>
			<div class="resultado">
				<a href="${urlDetailView}">${result.nombreUsuario}</a>
				<p>${result.descripcion}</p>
			</div>
		</div>
	</c:if>
<%@include file="/html/common/footer.jsp"%>