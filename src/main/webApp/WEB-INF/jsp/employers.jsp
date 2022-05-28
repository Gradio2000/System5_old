<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: aleksejlaskin
  Date: 23.05.2022
  Time: 12:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c"   %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"       prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql"       prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml"       prefix="x"   %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"  %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"  %>
<html>
<head>
    <title>Работники</title>
</head>
<body>
<jsp:include page="../includes/header.jsp"/>
<jsp:include page="../includes/menu.jsp"/>
<div class="main">
    <p>Оцените, пожалуйста, работу сотрудников:</p>
    <c:forEach items="${positionList}" var="position">
        <a href="/list/${position.user.userId}">${position.position} ${position.user.name}</a>
        <br/>
    </c:forEach>
</div>

</body>
</html>
<style>
    <%@include file="../includes/myStyle.css"%>
</style>