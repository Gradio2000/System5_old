<%--
  Created by IntelliJ IDEA.
  User: aleksejlaskin
  Date: 27.07.2022
  Time: 21:16
  To change this template use File | Settings | File Templates.
--%>
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

    <title>Зачет</title>
</head>
<body>
<div class="main">
    <div>
        <c:if test="${appointTestList.size() == 0}">
            <a style="font-family: 'Arial Unicode MS',cursive; color: #dc4242">Вам не назначены зачёты</a>
        </c:if>
    </div>
<c:forEach var="test" items="${appointTestList}">
    <div>
        <table style="width: 100%; table-layout: auto">
            <tr>
                <th style="width: 20%">Зачёт: </th>
                <td style="text-align: left">${test.testName}</td>
            <tr>
                <th style="width: 20%">Критерий для сдачи, %</th>
                <td style="text-align: left">${test.criteria}</td>
            </tr>
            <tr>
                <th style="width: 20%">Количество вопросов</th>
                <td style="text-align: left">${test.amountQues}</td>
            </tr>
    </table>
    </div>
    <div>
        <form action="/processing/startExam" method="get">
            <input type="hidden" name="appointTestId" value="${test.id}">
            <button type="submit" class="btn" STYLE="margin-top: 1em">Начать</button>
        </form>
    </div>
</c:forEach>
</div>
</body>
</html>
