<%--
  Created by IntelliJ IDEA.
  User: aleksejlaskin
  Date: 19.05.2022
  Time: 16:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c"   %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"       prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql"       prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml"       prefix="x"   %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"  %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"  %>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<html>
<head>
    <title>Отчет</title>
</head>
<body>
<%--<c:set var="month" value="${month}"/>--%>
<div style="text-align: center">
    <p>Форма оценка работников полевого учреждения Банка России № 42667 за ${month}</p>
</div>


<div>
    <table class="table" style="width: 75%; margin-left: auto; margin-right: auto">
        <tbody>
        <tr>
            <th rowspan="2">ФИО</th>
            <th rowspan="2">Должность</th>
            <th colspan="5" style="width: 25%">Оценки по критериям</th>
            <th rowspan="2" style="width: 10%">Итоговая оценка</th>
        </tr>
        <tr>
            <th style="writing-mode: vertical-rl; width: 5%">Личная результативность</th>
            <th style="writing-mode: vertical-rl; width: 5%">Инициативность</th>
            <th style="writing-mode: vertical-rl; width: 5%">Совершенствование профессиональных знаний</th>
            <th style="writing-mode: vertical-rl; width: 5%">Клиентоориентированность</th>
            <th style="writing-mode: vertical-rl; width: 5%">Работа в команде</th>
        </tr>
        <c:forEach var="system5" items="${system5List}">
            <tr>
                <td>${system5.user.name}</td>
                <td>${system5.user.position.position}</td>
                <td>${system5.system5empl.resempl1}</td>
                <td>${system5.system5empl.resempl2}</td>
                <td>${system5.system5empl.resempl3}</td>
                <td>${system5.system5empl.resempl4}</td>
                <td>${system5.system5empl.resempl5}</td>
                <td></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>
</body>
</html>
<style>
    <%@include file="myStyle.css"%>
</style>
