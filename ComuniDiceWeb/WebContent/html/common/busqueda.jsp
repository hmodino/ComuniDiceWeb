<%@ page import="java.util.List" %>
<%@page import="com.comunidice.web.util.*, com.comunidice.web.model.*" %>
<div id="busqueda">
	<form action="<%=ControllerPaths.SEARCH%>" method="post">
			<input type="hidden" name=<%=ParameterNames.DEFAULT%> value="<%=ParameterNames.FALSE%>"/>
			<input type="text" name="<%=ParameterNames.SEARCH_BOX%>" 
				value="<%=ValidationUtils.getParameter(request, ParameterNames.SEARCH_BOX)%>" />
			<p><fmt:message key = "buscarPor" bundle="${messages}"/></p>
			<input type="radio" name=<%=ParameterNames.SEARCH_TYPE %> value="<%=ParameterNames.SEARCH_PRODUCT%>" checked="checked">
				<fmt:message key="producto" bundle="${messages}"/>
			<input type="radio" name=<%=ParameterNames.SEARCH_TYPE %> value="<%=ParameterNames.SEARCH_USER%>">
				<fmt:message key="usuario" bundle="${messages}"/>
			<div id="hiddenUsuario">
				<input type="radio" name="<%=ParameterNames.SEARCH_BY %>" value="<%=ParameterNames.EMAIL%>">
					<fmt:message key="email" bundle="${messages}"/>
				<input type="radio" name="<%=ParameterNames.SEARCH_BY %>" value="<%=ParameterNames.USER_NAME%>" checked="checked">
					<fmt:message key="nombre" bundle="${messages}"/>
			</div>
			<div id="hiddenCriteria">
				<c:if test="${not empty categories}">
				<fmt:message key="categoria" bundle="${messages}"/>
					<select name="<%=ParameterNames.CATEGORY_ID%>">
						<option></option>
						<c:forEach items="${categories}" var="c">
							<option value="${c.id}">${c.nombre}</option>
						</c:forEach>
					</select>
				</c:if>
				<fmt:message key="precioMin" bundle="${messages}"/><input type="text" name="<%=ParameterNames.MIN_PRICE %>">
				<fmt:message key="precioMax" bundle="${messages}"/><input type="text" name="<%=ParameterNames.MAX_PRICE %>">
				<fmt:message key="fechaEntradaMin" bundle="${messages}"/><input type="date" name="<%=ParameterNames.MIN_DATE %>">
				<fmt:message key="fechaEntradaMax" bundle="${messages}"/><input type="date" name="<%=ParameterNames.MAX_DATE %>">
				<fmt:message key="tipoVendedor" bundle="${messages}"/>
				<select name="<%=ParameterNames.SELLER_TYPE %>">
					<option></option>
					<option value="0"><fmt:message key="comunidice" bundle="${messages}"/></option>
					<option value="1"><fmt:message key="particular" bundle="${messages}"/></option>
				</select>
				<fmt:message key="publicacionMin" bundle="${messages}"/><input type="number" name="<%=ParameterNames.MIN_PUBLICATION_YEAR %>">
				<fmt:message key="publicacionMax" bundle="${messages}"/><input type="number" name="<%=ParameterNames.MAX_PUBLICATION_YEAR %>">
				<fmt:message key="formato" bundle="${messages}"/>
				<select name="<%=ParameterNames.FORMAT %>">
					<option></option>
					<option value="0"><fmt:message key="fisico" bundle="${messages}"/></option>
					<option value="1"><fmt:message key="pdf" bundle="${messages}"/></option>
				</select>
				<fmt:message key="tipoTapa" bundle="${messages}"/>
				<select name="<%=ParameterNames.COVER_TYPE %>">
					<option></option>
					<option value="0"><fmt:message key="blanda" bundle="${messages}"/></option>
					<option value="1"><fmt:message key="dura" bundle="${messages}"/></option>
				</select>
			</div>
			<input type="submit" value="<fmt:message key="buscar" bundle="${messages}"/>" />
		</form>
</div>	