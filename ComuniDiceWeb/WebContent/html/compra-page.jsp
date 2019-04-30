<%@page import="java.util.*" %>
<%@include file="/html/common/header.jsp"%>
	<div id="Compra" class="content">
		<form action="<%=ControllerPaths.PRODUCTO%>" method="post">
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.PRE_BUY%>">
			<fmt:message key="tarjeta" bundle="${messages}"/><input type="text" name="<%=ParameterNames.CARD_NUMBER%>">
			<fmt:message key="caducidad" bundle="${messages}"/><input type="date" name="<%=ParameterNames.EXPIRE_DATE%>">
			<input type="submit" value="<fmt:message key="comprar" bundle="${messages}"/>">
		</form>	 
	</div>
<%@include file="/html/common/footer.jsp"%>