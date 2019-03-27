<%@page import="java.util.*" %>
<%@include file="/html/common/header.jsp"%>
	<%
		List<String>parameterErrors = errors.getErrors(ParameterNames.USER_NAME);
		for (String error: parameterErrors) {
			%><li><%=error%></li><%
		}
	%>
	<%	
		Usuario user = (Usuario) request.getAttribute(AttributeNames.RESULTS);
		if (user!=null) {
	%>
		<a href="/ComuniDiceWeb/usuario?action=<%=Actions.DETAIL_VIEW%>&amp;<%=ParameterNames.ID%>=<%=user.getIdUsuario()%>">
			<%=user.getNombreUsuario()%></a>
			<p><%=user.getDescripcion() %></p>
			<a href="<%=ControllerPaths.USUARIO%>?action=<%=Actions.ADD_FRIEND%>&amp;<%=ParameterNames.ID%>=<%=user.getIdUsuario()%>">
			<img ser="<%=request.getContextPath()%>/imgs/addFriendButton.jpg" width="30px" height="30px" alt="addFriend"></a>
	<%
			}
		else{
		%><p>No existen coincidencias</p><%
			}
		%>

<%@include file="/html/common/footer.jsp"%>