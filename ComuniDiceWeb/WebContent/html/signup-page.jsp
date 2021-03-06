<%@include file="/html/common/header.jsp"%>
<div id="signup" class="content">
	<h2><fmt:message key="registro" bundle="${messages}"/></h2>
	<form action="<%=ControllerPaths.USUARIO%>" method="post">
		<div id="datosPersonales">
			<h3><fmt:message key="datosPersonales" bundle="${messages}"/></h3>
		
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.SIGN_UP%>"/>
			
			<input type="text" name="<%=ParameterNames.NAME%>" placeholder="<fmt:message key="nombre" bundle="${messages}"/>"/>
				
			<input type="text" name="<%=ParameterNames.SURNAME%>" placeholder="<fmt:message key="apellido" bundle="${messages}"/>"/>
				
			<input type="text" name="<%=ParameterNames.SURNAME2%>" placeholder="<fmt:message key="apellido" bundle="${messages}"/>">
				
			<input type="text" name="<%=ParameterNames.USER_NAME%>" placeholder="<fmt:message key="nombreUsuario" bundle="${messages}"/>">
			
			<input type="text" name="<%=ParameterNames.EMAIL%>" placeholder="<fmt:message key="email" bundle="${messages}"/>">
				
			<input type="password" name="<%=ParameterNames.PASSWORD%>" placeholder="<fmt:message key="contrasena" bundle="${messages}"/>">
				
			<input type="text" name="<%=ParameterNames.DESCRIPTION%>" placeholder="<fmt:message key="descripcion" bundle="${messages}"/>">
				
			<input type="text" name="<%=ParameterNames.PHONE%>" placeholder="<fmt:message key="telefono" bundle="${messages}"/>">
		</div>
		<div id="direccion">
			<h3><fmt:message key="direccion" bundle="${messages}"/></h3>
			
			<select name="countries" id="countries">
				<c:if test="${not empty results}">
					<option value=""><fmt:message key="seleccionPais" bundle="${messages}"/></option>
					<c:forEach items="${results}" var="r">
						<option value="${r.getIdPais()}">${r.getNombre()}</option>
					</c:forEach>
				</c:if>
			</select>
		<select name="regions" id="regions">
		<option value=""><fmt:message key="seleccionRegion" bundle="${messages}"/></option>
		<c:forEach items="${regions}" var="rg" >
			<option value="${rg.getIdRegion()}">${rg.getNombre()}</option>
		</c:forEach>
		</select>
			<input type="text" name="<%=ParameterNames.MUNICIPALITY%>" placeholder="<fmt:message key="municipio" bundle="${messages}"/>">
				
			<input type="text" name="<%=ParameterNames.LOCALITY%>" placeholder="<fmt:message key="localidad" bundle="${messages}"/>">
				
			<input type=text name="<%=ParameterNames.CP%>" placeholder="<fmt:message key="cp" bundle="${messages}"/>">
				
			<input type="text" name="<%=ParameterNames.STREET%>" placeholder="<fmt:message key="calle" bundle="${messages}"/>">
				
			<input type="number" name="<%=ParameterNames.PORTAL_NUMBER%>" placeholder="<fmt:message key="numero" bundle="${messages}"/>">
				
			<input type="text" name="<%=ParameterNames.PORTAL%>" placeholder="<fmt:message key="portal" bundle="${messages}"/>">
				
			<input type="number" name="<%=ParameterNames.FLOOR%>" placeholder="<fmt:message key="piso" bundle="${messages}"/>">
				
			<input type="text" name="<%=ParameterNames.DIRECTION_OTHERS%>" placeholder="<fmt:message key="otros" bundle="${messages}"/>">
							
			<input type="submit" value="<fmt:message key="registro" bundle="${messages}"/>" />
		</div>
	</form>
	
</div>
<%@include file="/html/common/footer.jsp"%>