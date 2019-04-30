<%@ page import="java.util.List" %>
<%@page import="com.comunidice.web.util.*, com.comunidice.web.model.*" %>
<div id="busqueda">
<div id="busqueda-content">
	<form action="<%=ControllerPaths.SEARCH%>" method="post">
			<input type="hidden" name=<%=ParameterNames.DEFAULT%> value="<%=ParameterNames.FALSE%>"/>
			<input type="text" name="<%=ParameterNames.SEARCH_BOX%>" 
				value="<%=ValidationUtils.getParameter(request, ParameterNames.SEARCH_BOX)%>" />
			<p><fmt:message key = "buscarPor" bundle="${messages}"/></p>
			<input type="radio"  id="productoCheck"name=<%=ParameterNames.ACTION%> value="<%=Actions.SEARCH_PRODUCTS%>" checked="checked">
				<p><fmt:message key="producto" bundle="${messages}"/></p>
			<input type="radio" id="usuarioCheck" name=<%=ParameterNames.ACTION%> value="<%=Actions.SEARCH_USERS%>">
				<p><fmt:message key="usuario" bundle="${messages}"/></p>
			<div id="hiddenUsuario"><br>
				<input type="radio" name="<%=ParameterNames.SEARCH_BY %>" value="<%=ParameterNames.EMAIL%>">
					<p><fmt:message key="email" bundle="${messages}"/></p>
				<input type="radio" name="<%=ParameterNames.SEARCH_BY %>" value="<%=ParameterNames.USER_NAME%>" checked="checked">
					<p><fmt:message key="nombre" bundle="${messages}"/></p>
			</div>
			<div id="hiddenCriteria">
				<c:if test="${not empty categories}">
				<p><fmt:message key="categoria" bundle="${messages}"/></p>
					<select name="<%=ParameterNames.CATEGORY_ID%>">
						<option></option>
						<c:forEach items="${categories}" var="c">
							<option value="${c.id}"><p>${c.nombre}</p></option>
						</c:forEach>
					</select>
				</c:if>
				<p><fmt:message key="precioMin" bundle="${messages}"/></p><input type="text" name="<%=ParameterNames.MIN_PRICE %>">
				<p><fmt:message key="precioMax" bundle="${messages}"/></p><input type="text" name="<%=ParameterNames.MAX_PRICE %>">
				<p><fmt:message key="fechaEntradaMin" bundle="${messages}"/></p>
					<input type="date" name="<%=ParameterNames.MIN_DATE %>">
				<p><fmt:message key="fechaEntradaMax" bundle="${messages}"/></p>
					<input type="date" name="<%=ParameterNames.MAX_DATE %>"><br>
				<p><fmt:message key="tipoVendedor" bundle="${messages}"/></p>
				<select name="<%=ParameterNames.SELLER_TYPE %>">
					<option></option>
					<option value="0"><p><fmt:message key="comunidice" bundle="${messages}"/></p></option>
					<option value="1"><p><fmt:message key="particular" bundle="${messages}"/></p></option>
				</select>
				<p><fmt:message key="publicacionMin" bundle="${messages}"/></p>
					<input type="number" name="<%=ParameterNames.MIN_PUBLICATION_YEAR %>"><br>
				<p><fmt:message key="publicacionMax" bundle="${messages}"/></p>
					<input type="number" name="<%=ParameterNames.MAX_PUBLICATION_YEAR %>">
				<p><fmt:message key="formato" bundle="${messages}"/></p>
				<select name="<%=ParameterNames.FORMAT %>">
					<option></option>
					<option value="0"><p><fmt:message key="fisico" bundle="${messages}"/></p></option>
					<option value="1"><p><fmt:message key="pdf" bundle="${messages}"/></p></option>
				</select><br>
				<p><fmt:message key="tipoTapa" bundle="${messages}"/></p>
				<select name="<%=ParameterNames.COVER_TYPE %>">
					<option></option>
					<option value="0"><p><fmt:message key="blanda" bundle="${messages}"/></p></option>
					<option value="1"><p><fmt:message key="dura" bundle="${messages}"/></p></option>
				</select>
			</div>
			<input type="submit" value="<fmt:message key="buscar" bundle="${messages}"/>" />
		</form>
		</div>
</div>	