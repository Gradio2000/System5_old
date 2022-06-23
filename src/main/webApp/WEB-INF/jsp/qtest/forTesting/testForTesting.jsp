<%--
  Created by IntelliJ IDEA.
  User: aleksejlaskin
  Date: 21.06.2022
  Time: 23:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c"   %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"       prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql"       prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml"       prefix="x"   %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"  %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"  %>
<script type="text/javascript" src="../../../../js/jquery-3.6.0.js"></script>
<html>
<style>
    <%@include file="../../../includes/myStyle.css"%>

</style>
<head>
    <jsp:include page="../../../includes/header.jsp"/>
    <jsp:include page="../../../includes/menu.jsp"/>

    <title>Старт тестирования</title>
</head>
<body>
<div class="main">
    <table id="color_table" style="width: 100%; table-layout: auto">
        <tr>
            <th class="tblsht">Tест: </th>
            <td class="tblsht">${test.testName}</td>
        <tr>
            <th class="tblsht">Время теста, мин.</th>
            <td class="tblsht">${test.time}</td>
        </tr>
        <tr>
            <th class="tblsht">Критерий для сдачи, %</th>
            <td class="tblsht">${test.criteria}</td>
        </tr>
        <tr>
            <th class="tblsht">Количество вопросов</th>
            <td class="tblsht">${test.quesAmount}</td>
        </tr>
    </table>
    <form action="/processing/start" method="post">
        <input type="hidden" name="testId" value="${test.testId}">
        <button type="submit" class="btn">Начать</button>
    </form>

</div>
</body>
</html>
