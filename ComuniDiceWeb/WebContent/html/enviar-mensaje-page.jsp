<%@page import="java.util.*" %>
<%@include file="/html/common/header.jsp"%>

	
	<%Integer id = (Integer)request.getAttribute(AttributeNames.ID);
	Usuario user = (Usuario)request.getAttribute(AttributeNames.USER);
	List<Usuario> friends = (List<Usuario>) request.getAttribute(AttributeNames.RESULTS);%>
	
	<form action="<%=ControllerPaths.USUARIO%>" method="post">
		<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.SEND_MESSAGE%>"/>
		<%if(friends!=null) {%>
			<select>
				<%for(Usuario f:friends){ %>
					<option name="<%= ParameterNames.ID%>" value="<%=f.getIdUsuario()%>"><%=f.getNombreUsuario()%></option>
				<%} %>
			</select>
		<%}if(id!=null){%>
			<input type="hidden" name="<%=ParameterNames.ID%>" value="<%=id%>" readonly>
		<%} %>
		<textarea rows="25" cols="100" name="<%=ParameterNames.MESSAGE_CONTENT%>"></textarea>
		<input type="submit" value="Enviar" />
	</form>
	
<%@include file="/html/common/footer.jsp"%>