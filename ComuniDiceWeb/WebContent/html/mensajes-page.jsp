<%@page import="java.util.*" %>
<%@include file="/html/common/header.jsp"%>
	<%
		List<String>parameterErrors = errors.getErrors(ParameterNames.USER_NAME);
		for (String error: parameterErrors) {
			%><li><%=error%></li><%
		}
	%>
	<a href="<%=ControllerPaths.USUARIO%>?action=<%=Actions.FIND_MESSAGES%>&amp;<%=ParameterNames.MESSAGE_TYPE%>=<%=ParameterNames.SENDER%>">
	Enviados</a>
	<a href="<%=ControllerPaths.USUARIO%>?action=<%=Actions.FIND_MESSAGES%>&amp;<%=ParameterNames.MESSAGE_TYPE%>=<%=ParameterNames.RECEIVER%>">
	Recibidos</a>
	<%	
		List<Mensaje> m = (List<Mensaje>) request.getAttribute(AttributeNames.RESULTS);
		if (m!=null) {
	%>
	<div>
	<ul>
	<%for(Mensaje message:m){ %>
		<li><%=message%></li>
		<li><a href="<%=ControllerPaths.USUARIO%>?action=<%=Actions.REMOVE_MESSAGE%>&amp;<%=ParameterNames.ID%>=<%=message.getIdMensaje()%>">
		Eliminar</a></li>
		<%if(message.getUsuarioEmisor()!=u.getIdUsuario()){ %>
		<li><a href="<%=ControllerPaths.USUARIO%>?action<%=Actions.PRE_SEND_MESSAGE%>&amp;<%=ParameterNames.ID%>=<%=message.getUsuarioEmisor()%>">
		Responder</a></li>
		<%} 
		}%>
	</ul><%
			}
		else{
		%><p>No existen coincidencias</p><%
			}
		%></div>

<%@include file="/html/common/footer.jsp"%>