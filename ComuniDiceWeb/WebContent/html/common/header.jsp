<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.comunidice.web.util.*" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope['user-locale']}" scope="session"/>
<fmt:setBundle basename="resources.messages" var="messages" scope="session"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<header>
		<div id="logo">
			<a href="/ComuniDiceWeb<%=ViewPaths.HOME%>">
				<img src="<%=request.getContextPath()%>/imgs/d8.png" width="100" height="100" alt="ComuniDice Logo"/></a>
		</div>
			<c:url var="en" scope="page" value="<%=ControllerPaths.NO_CONTEXT_USUARIO %>">
				<c:param name="<%=ParameterNames.ACTION %>" value="<%=Actions.CHANGE_LOCALE%>"/>
				<c:param name="<%=ParameterNames.LANGUAGE%>" value="en_GB"/>
				<c:if test="${not empty url}">
					<c:param name="<%=AttributeNames.URL%>" value="${url}"/>
				</c:if>
			</c:url>
			<c:url var="es" scope="page" value="<%=ControllerPaths.NO_CONTEXT_USUARIO %>">
				<c:param name="<%=ParameterNames.ACTION %>" value="<%=Actions.CHANGE_LOCALE%>"/>
				<c:param name="<%=ParameterNames.LANGUAGE%>" value="es_ES"/>
			</c:url>
		<div id="language">	
			<a href="${en}">EN</a>
			<a href="${es}">ES</a>
		</div>
	</header>
	<nav>
		<%@include file="/html/common/user-menu.jsp"%>
		<%@include file="/html/common/busqueda.jsp" %>	
	</nav>