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
<p>Оценка сотрудников полевого учреждения Банка России № 42667 за ${month}</p>
<table class="table">
    <tbody>
    <tr>
        <th rowspan="2">ФИО</th>
        <th colspan="5">Самооценка</th>
        <th rowspan="2">Итоговая оценка</th>
        <th colspan="5">Оценка руководителя</th>
        <th rowspan="2">Итоговая оценка</th>
    </tr>
    <tr>
        <th>Личная результативность</th>
        <th>Инициативность</th>
        <th>Совершенствование профессиональных знаний</th>
        <th>Клиентоориентированность</th>
        <th>Работа в команде</th>
        <th>Личная результативность</th>
        <th>Инициативность</th>
        <th>Совершенствование профессиональных знаний</th>
        <th>Клиентоориентированность</th>
        <th>Работа в команде</th>
    </tr>
    <tr>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
    </tr>
    </tbody>
</table>
</body>
</html>
<style>
    <%@include file="myStyle.css"%>
</style>
