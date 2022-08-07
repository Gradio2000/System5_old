<%--
  Created by IntelliJ IDEA.
  User: aleksejlaskin
  Date: 05.08.2022
  Time: 17:16
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

    <title>Журнал</title>
</head>
<body>
<div class="main">
    <div id="printableArea">
    <table id="color_table" style="width: 100%">
        <tbody>
            <tr>
                <th>Дата и номер распорядительного акта</th>
                <th>Дата и форма проведения зачета</th>
                <th>Фамилия, инициалы и должность лица, сдавшего зачет</th>
                <th>Сумма набранных баллов</th>
                <th>Оценка комиссии</th>
            </tr>
        <c:forEach var="appointTestDto" items="${appointTestDtoList}">
            <tr>
                <td>${appointTestDto.base}</td>
                <td>
                    <fmt:formatDate value="${appointTestDto.attempttestDto.dateTime}" pattern="dd.MM.yyyy"/>
                    тестирование
                </td>
                <td>${appointTestDto.userDto.name} ${appointTestDto.userDto.position.position}</td>
                <td>${appointTestDto.attempttestDto.amountTrueAnswers} из ${appointTestDto.attempttestDto.amountQues}</td>
                <td>${appointTestDto.attempttestDto.testResult}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </div>
    <div>
        <input type="button" onclick="printDiv('printableArea')" value="Печать" class="btn"/>
    </div>
</div>

</body>
</html>
<script>
    function printDiv(divName) {
        const printContents = document.getElementById(divName).innerHTML;
        const originalContents = document.body.innerHTML;

        document.body.innerHTML = printContents;

        window.print();

        document.body.innerHTML = originalContents;
    }
</script>

