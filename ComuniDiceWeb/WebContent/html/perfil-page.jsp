<%@include file="/html/common/header.jsp"%>
<div id="home">
	<%Usuario user = (Usuario) request.getAttribute(AttributeNames.USER);
		if(user!=null){
			%>
			<ul>
			<li><%=user%></li></ul><%
		}else{%>
		<p>No se ha encontrado el usuario</p><%
		} %>
	
</div>
<%@include file="/html/common/footer.jsp"%>