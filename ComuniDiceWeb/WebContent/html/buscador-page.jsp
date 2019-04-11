<%@page import="java.util.*" %>
<%@include file="/html/common/header.jsp"%>
	<c:if test="${not empty results}">
			<c:url var="urlDetailView" scope="page" value="usuario">
				<c:param name="action" value="<%=Actions.DETAIL_VIEW%>"/>
				<c:param name="<%=ParameterNames.ID%>" value="${results.idUsuario}"/>
			</c:url>
			<c:url var="urlAdd" scope="page" value="usuario">
				<c:param name="action" value="<%=Actions.ADD_FRIEND%>"/>
				<c:param name="<%=ParameterNames.ID%>" value="${results.idUsuario}"/>
			</c:url>
			<a href="${urlDetailView}">${results.nombreUsuario}</a>
			<p>${results.descripcion}</p>
			<a href="${urlAdd}">
				<img src="<%=request.getContextPath()%>/imgs/addFriendButton.jpg" width="30px" height="30px" alt="addFriend"></a>
	</c:if>
<%@include file="/html/common/footer.jsp"%>