<%--
  Created by IntelliJ IDEA.
  User: aleksejlaskin
  Date: 23.05.2022
  Time: 17:22
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

    <title>Отчет</title>
</head>
<body>
<jsp:include page="../../includes/header.jsp"/>
<jsp:include page="../../includes/menu.jsp"/>
<div class="main">
    <form id="monsel" method="get" action="/admin/report">
        <select name="month" class="select-css" style="width: max-content;">
            <c:forEach var="month" items="${months}">
                <option>${month}</option>
            </c:forEach>
        </select>
        <button type="submit" class="btn">Сформировать</button>
    </form>
</div>
</body>
</html>
<style>
    <%@include file="../../includes/myStyle.css"%>
</style>