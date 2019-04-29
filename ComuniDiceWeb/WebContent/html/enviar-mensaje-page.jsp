<%@page import="java.util.*" %>
<%@include file="/html/common/header.jsp"%>
	<div id="enviarMensaje" class="content">
		<form action="<%=ControllerPaths.USUARIO%>" method="post">
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.SEND_MESSAGE%>"/>
			<c:if test="${not empty results}">
				<select>
					<c:forEach items="${results}" var="r">
						<option name="<%= ParameterNames.ID%>" value="${r.idUsuario}">${r.nombreUsuario}</option>
					</c:forEach>
				</select>
			</c:if>
			<c:if test="${not empty id}">
				<input type="hidden" name="<%=ParameterNames.ID%>" value="${id}">
			</c:if>
			<textarea rows="25" cols="100" name="<%=ParameterNames.CONTENT%>"></textarea>
			<input type="submit" value="<fmt:message key="enviar" bundle="${messages}"/>"/>
		</form>
	</div>
<%@include file="/html/common/footer.jsp"%>