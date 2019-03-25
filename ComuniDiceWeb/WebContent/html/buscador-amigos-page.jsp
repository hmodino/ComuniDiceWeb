<%@page import="java.util.*" %>
<%@include file="/html/common/header.jsp"%>
	<%
		List<String>parameterErrors = errors.getErrors(ParameterNames.USER_NAME);
		for (String error: parameterErrors) {
			%><li><%=error%></li><%
		}
	%>
	<%	
		Amigo amigo = (Amigo) request.getAttribute(AttributeNames.RESULTS);
		if (amigo!=null) {
	%>
	<ul>
		<li><%=amigo%></li>
	</ul><%
			}
		else{
		%><p>No existen coincidencias</p><%
			}
		%>

<%@include file="/html/common/footer.jsp"%>