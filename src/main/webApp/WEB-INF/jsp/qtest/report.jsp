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

    <title>Отчет</title>
</head>
<body>
<div class="main">
    <table>
        <tr>
            <td>Работник</td>
            <td>${user.name}</td>
        </tr>
        <tr>
            <td>Дата и время</td>
            <td>${attempt.dateTime}</td>
        </tr>
        <tr>
            <td>Тест</td>
            <td>${attempt.test.testName}</td>
        </tr>
        <tr>
            <td>Количество заданных вопросов</td>
            <td>${attempt.amountQues}</td>
        </tr>
        <tr>
            <td>Количество правильных ответов</td>
            <td>${attempt.amountTrueAnswers}</td>
        </tr>
        <tr>
            <td>Количество неверных ответов</td>
            <td>${attempt.amountFalseAnswers}</td>
        </tr>
        <tr>
            <td>Затраченное время</td>
            <td>${attempt.timeAttempt}</td>
        </tr>
    </table>
    <button class="btn" onclick="document.location='/tests/mytests/0'">Назад</button>
</div>
</body>
</html>
