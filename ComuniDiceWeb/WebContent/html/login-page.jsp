<%@ page import="java.util.List" %>

<%@include file="/html/common/header.jsp"%>
<div id="login-form">
		<h3>Autenticate:</h3>	
		<form action="<%=ControllerPaths.USUARIO%>" method="post">
			<%
				List<String> parameterErrors = errors.getErrors(ParameterNames.ACTION);
				for (String error: parameterErrors) {
					%><li><%=error%></li><%
				}
			%>
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.LOGIN%>"/>
			<input type="email" name="<%=ParameterNames.EMAIL%>" 
				placeholder="usuario@ejemplo.com"
				value="<%=ParamsUtils.getParameter(request, ParameterNames.EMAIL)%>" />
				<%
					parameterErrors = errors.getErrors(ParameterNames.EMAIL);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
				%>			
			<input type="password" name="<%=ParameterNames.PASSWORD%>"/>
			<%
					parameterErrors = errors.getErrors(ParameterNames.PASSWORD);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
				%>
			<input type="submit" value="Entrar" />
		</form>
</div>

<%@include file="/html/common/footer.jsp"%>
