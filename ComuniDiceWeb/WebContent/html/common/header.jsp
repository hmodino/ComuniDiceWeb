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
	<title>ComuniDice</title>
	<link rel="icon" href="/ComuniDiceWeb/imgs/icono.ico">
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="https://fonts.googleapis.com/css?family=Cinzel" rel="stylesheet">
	<link rel="stylesheet" type="text/css" media="screen" href="/ComuniDiceWeb/css/style.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.3.1.min.js"></script> 
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/menu.js"></script> 
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/ajax.js"></script>
</head>
<body>
	<header>
		<div id="logo">
			<a href="/ComuniDiceWeb<%=ViewPaths.HOME%>">
				<img src="<%=request.getContextPath()%>/imgs/comunidice1.png" width="180" height="180" alt="ComuniDice Logo"/></a>
		</div>
		<nav>
			<%@include file="/html/common/user-menu.jsp"%>
			<%@include file="/html/common/busqueda.jsp" %>	
		</nav>
	</header>