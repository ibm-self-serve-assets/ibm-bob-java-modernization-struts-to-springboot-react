<%@taglib uri="http://jakarta.apache.org/struts/tags-tiles"
	prefix="tiles"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-gb" dir="ltr">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />

<link href="/pages/template/css/favicon.ico" rel="shortcut icon"
	type="image/x-icon" />

<link type="text/css" rel="stylesheet"
	href="pages/template/css/system.css" />
<link type="text/css" rel="stylesheet"
	href="pages/template/css/login.css" />
<link type="text/css" rel="stylesheet"
	href="pages/template/css/rounded.css" />
<link type="text/css" rel="stylesheet"
	href="pages/template/css/general.css" />
<link type="text/css" rel="stylesheet"
	href="pages/template/css/template.css" />
<link type="text/css" rel="stylesheet"
	href="pages/template/css/icon.css" />
<!-- <script src="pages/template/js/angular.min.js"></script> -->
<title><tiles:getAsString name="title" /></title>
</head>
<body>
	<tiles:insert attribute="header" />
	<tiles:insert attribute="body" />
	<tiles:insert attribute="footer" />
</body>
</html>