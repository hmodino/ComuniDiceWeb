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
	<link rel="stylesheet" type="text/css" media="screen" href="/ComuniDiceWeb/css/style.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/javascript/jquery-3.3.1.min.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/js/ajax.js"></script>
</head>
<body>
	<header>
		<div id="logo">
			<a href="/ComuniDiceWeb<%=ViewPaths.HOME%>">
				<img src="<%=request.getContextPath()%>/imgs/d8.png" width="100" height="100" alt="ComuniDice Logo"/></a>
			<h1>ComuniDice</h1>
		</div>
		<nav>
			<%@include file="/html/common/user-menu.jsp"%>
			<%@include file="/html/common/busqueda.jsp" %>	
		</nav>
	</header>