<%@include file="/html/common/header.jsp"%>
<div id="home">
	
	<%Usuario user = (Usuario) request.getAttribute(AttributeNames.USER);%>
	<form action="<%=ControllerPaths.USUARIO%>" method="post">
		<h4>Datos Personales</h4>
		
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.EDIT%>"/>
			
			<input type="text" name="<%=ParameterNames.NAME%>" 
				value="<%=ValidationUtils.getParameter(request, ParameterNames.NAME)%>" placeholder="Nombre"/>
				
			<input type="text" name="<%=ParameterNames.SURNAME%>" 
				value="<%=ValidationUtils.getParameter(request, ParameterNames.SURNAME)%>" placeholder="Apellido 1"/>
				
			<input type="text" name="<%=ParameterNames.SURNAME2%>" 
				value="<%=ValidationUtils.getParameter(request, ParameterNames.SURNAME2)%>" placeholder="Apellido 2">
				
			<input type="text" name="<%=ParameterNames.USER_NAME%>" 
				value="<%=ValidationUtils.getParameter(request, ParameterNames.USER_NAME)%>" placeholder="Nombre de Usuario">
				
			<input type="password" name="<%=ParameterNames.PASSWORD%>" 
				value="<%=ValidationUtils.getParameter(request, ParameterNames.PASSWORD)%>" placeholder="Contraseña">
				
			<input type="text" name="<%=ParameterNames.DESCRIPTION%>" 
				value="<%=ValidationUtils.getParameter(request, ParameterNames.DESCRIPTION)%>" placeholder="Descripción">
				
			<input type="text" name="<%=ParameterNames.PHONE%>" 
				value="<%=ValidationUtils.getParameter(request, ParameterNames.PHONE)%>" placeholder="Teléfono">
			
			<h4>Dirección</h4>
			
			<input type="text" name="<%=ParameterNames.MUNICIPALITY%>" 
				value="<%=ValidationUtils.getParameter(request, ParameterNames.MUNICIPALITY)%>" placeholder="Municipio">
				
			<input type="text" name="<%=ParameterNames.LOCALITY%>" 
				value="<%=ValidationUtils.getParameter(request, ParameterNames.LOCALITY)%>" placeholder="Localidad">
				
			<input type=text name="<%=ParameterNames.CP%>" 
				value="<%=ValidationUtils.getParameter(request, ParameterNames.CP)%>" placeholder="Código Postal">
				
			<input type="text" name="<%=ParameterNames.STREET%>" 
				value="<%=ValidationUtils.getParameter(request, ParameterNames.STREET)%>" placeholder="Calle">
				
			<input type="text" name="<%=ParameterNames.PORTAL_NUMBER%>" 
				value="<%=ValidationUtils.getParameter(request, ParameterNames.PORTAL_NUMBER)%>" placeholder="Número">
				
			<input type="text" name="<%=ParameterNames.PORTAL%>" 
				value="<%=ValidationUtils.getParameter(request, ParameterNames.PORTAL)%>" placeholder="Portal">
				
			<input type="text" name="<%=ParameterNames.FLOOR%>" 
				value="<%=ValidationUtils.getParameter(request, ParameterNames.FLOOR)%>" placeholder="Piso">
				
			<input type="text" name="<%=ParameterNames.DIRECTION_OTHERS%>" 
				value="<%=ValidationUtils.getParameter(request, ParameterNames.DIRECTION_OTHERS)%>" placeholder="Otros">
							
			<input type="submit" value="Guardar" />
	</form>
	
</div>
<%@include file="/html/common/footer.jsp"%>