<%@include file="/html/common/header.jsp"%>
<div id="home">
	
	<form action="<%=ControllerPaths.USUARIO%>" method="post">
		<h4><fmt:message key="datosPersonales" bundle="${messages}"/></h4>
		
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.EDIT%>"/>
			
			<input type="text" name="<%=ParameterNames.NAME%>" 
				value="${sessionScope['user'].nombre}" placeholder="<fmt:message key="nombre" bundle="${messages}"/>"/>
				
			<input type="text" name="<%=ParameterNames.SURNAME%>" 
				value="${sessionScope['user'].apellido1}" placeholder="<fmt:message key="apellido" bundle="${messages}"/>"/>
				
			<input type="text" name="<%=ParameterNames.SURNAME2%>" 
				value="${sessionScope['user'].apellido2}" placeholder="<fmt:message key="apellido" bundle="${messages}"/>">
				
			<input type="text" name="<%=ParameterNames.USER_NAME%>" 
				value="${sessionScope['user'].nombreUsuario}" placeholder="<fmt:message key="nombreUsuario" bundle="${messages}"/>">
				
			<input type="password" name="<%=ParameterNames.PASSWORD%>" placeholder="<fmt:message key="contrasena" bundle="${messages}"/>">
				
			<input type="text" name="<%=ParameterNames.DESCRIPTION%>" 
				value="${sessionScope['user'].descripcion}" placeholder="<fmt:message key="descripcion" bundle="${messages}"/>">
				
			<input type="text" name="<%=ParameterNames.PHONE%>" 
				value="${sessionScope['user'].telefono}" placeholder="<fmt:message key="telefono" bundle="${messages}"/>">
			
			<h4><fmt:message key="direccion" bundle="${messages}"/></h4>
			
			<input type="text" name="<%=ParameterNames.MUNICIPALITY%>" placeholder="<fmt:message key="municipio" bundle="${messages}"/>">
				
			<input type="text" name="<%=ParameterNames.LOCALITY%>" placeholder="<fmt:message key="localidad" bundle="${messages}"/>">
				
			<input type=text name="<%=ParameterNames.CP%>" placeholder="<fmt:message key="cp" bundle="${messages}"/>">
				
			<input type="text" name="<%=ParameterNames.STREET%>" placeholder="<fmt:message key="calle" bundle="${messages}"/>">
				
			<input type="text" name="<%=ParameterNames.PORTAL_NUMBER%>" placeholder="<fmt:message key="numero" bundle="${messages}"/>">
				
			<input type="text" name="<%=ParameterNames.PORTAL%>" placeholder="<fmt:message key="portal" bundle="${messages}"/>">
				
			<input type="text" name="<%=ParameterNames.FLOOR%>" placeholder="<fmt:message key="piso" bundle="${messages}"/>">
				
			<input type="text" name="<%=ParameterNames.DIRECTION_OTHERS%>" placeholder="<fmt:message key="otros" bundle="${messages}"/>">
							
			<input type="submit" value="<fmt:message key="guardar" bundle="${messages}"/>" />
	</form>
	
</div>
<%@include file="/html/common/footer.jsp"%>