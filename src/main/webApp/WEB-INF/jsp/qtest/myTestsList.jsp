<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c"   %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"       prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql"       prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml"       prefix="x"   %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"  %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"  %>
<script type="text/javascript" src="../../../js/jquery-3.6.0.js"></script>
<html>
<style>
    <%@include file="../../includes/myStyle.css"%>

</style>
<head>
    <jsp:include page="../../includes/header.jsp"/>
    <jsp:include page="../../includes/menu.jsp"/>

    <title>Мои тесты</title>
</head>
<body>
<div class="main">
    <table id="color_table" style="width: 100%">
        <tbody>
            <tr>
                <th style="width: 10%">№ п/п</th>
                <th>Дата и время</th>
                <th style="width: 40%">Название теста</th>
                <th>Результат</th>
                <th>Протокол</th>
            </tr>
        <c:forEach var="attempt" items="${attemptsList}" varStatus="count">
            <tr>
                <td>${count.count}</td>
                <td>
                    <fmt:formatDate value="${attempt.dateTime}" pattern="dd.MM.yyyy  HH:mm"/>
                </td>
                <td>${attempt.test.testName}</td>
                <td>${attempt.testResult}</td>
                <td><a>Протокол</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
