<%@ page import="java.util.List" %>
<%@include file="/html/common/header.jsp"%>
<div id="login-form" class="content">
		<h1><fmt:message key="autenticate" bundle="${messages}"/></h1>	
		<form action="<%=ControllerPaths.USUARIO%>" method="post">
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.LOGIN%>"/>
			<input type="email" name="<%=ParameterNames.EMAIL%>" 
				placeholder="<fmt:message key="ejemploEmail" bundle="${messages}"/>"/>
			<input type="password" name="<%=ParameterNames.PASSWORD%>" 
				placeholder="<fmt:message key="contrasena" bundle="${messages}"/>"/>
			<input type="checkbox" name="<%=ParameterNames.REMEMBERME %>" value="<%=ParameterNames.TRUE%>" ${check}>
				<fmt:message key="recuerdame" bundle="${messages}"/>
			<input type="submit" value="<fmt:message key="enviar" bundle="${messages}"/>"/>
		</form>
</div>
<%@include file="/html/common/footer.jsp"%>
