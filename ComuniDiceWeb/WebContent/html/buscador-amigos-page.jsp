<%@page import="java.util.*" %>
<%@include file="/html/common/header.jsp"%>
	<%
		List<String>parameterErrors = errors.getErrors(ParameterNames.USER_NAME);
		for (String error: parameterErrors) {
			%><li><%=error%></li><%
		}
	%>
	<%	
		List<Usuario> users = (List<Usuario>) request.getAttribute(AttributeNames.RESULTS);
		if (users!=null) {
	%>
	<div>
	<form action="<%=ControllerPaths.USUARIO%>" method="post">
	
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.FIND_FRIENDS_BY%>"/>
			<input type="text" name="<%=ParameterNames.SEARCH_BOX%>" 
				value="<%=ValidationUtils.getParameter(request, ParameterNames.SEARCH_BOX)%>" />
				<p>buscar por:</p>
				<select>
					<option name="<%=ParameterNames.SEARCH_BY%>"value="<%=ParameterNames.EMAIL%>">Email</option>
					<option name="<%=ParameterNames.SEARCH_BY%>"value="<%=ParameterNames.USER_NAME%>">Nombre</option>
				</select>
			<input type="submit" value="Buscar" />
		</form>
</div>	
	<ul><%for(Usuario user:users){ %>
		<li><%=user%></li>
		<li><a href="<%=ControllerPaths.USUARIO%>?action=<%=Actions.REMOVE_FRIEND%>&amp;<%=ParameterNames.ID%>=<%=user.getIdUsuario()%>">
		Eliminar</a></li>
		<li><a href="<%=ControllerPaths.USUARIO%>?action=<%=Actions.PRE_SEND_MESSAGE%>&amp;<%=ParameterNames.ID%>=<%=user.getIdUsuario()%>">
		Enviar mensaje</a></li>
		<%} %>
	</ul><%
			}
		else{
		%><p>No existen coincidencias</p><%
			}
		%>

<%@include file="/html/common/footer.jsp"%>