<%--
  Created by IntelliJ IDEA.
  User: aleksejlaskin
  Date: 04.06.2022
  Time: 09:48
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
    <title>Отчет</title>
</head>
<body>
<div class="main">
    <div id="printableArea" style="text-align: center">
        <p>Оценка работников полевого учреждения Банка России № 42667 за ${half} полугодие ${selectedYear} года</p>
        <table class="table">
            <tbody>
                <tr>
                    <th>Работник</th>
                    <th>Должность</th>
                    <c:if test="${half == 1}">
                        <th>ЯНВАРЬ</th>
                        <th>ФЕВРАЛЬ</th>
                        <th>МАРТ</th>
                        <th>АПРЕЛЬ</th>
                        <th>МАЙ</th>
                        <th>ИЮНЬ</th>
                    </c:if>
                    <c:if test="${half == 2}">
                        <th>ИЮЛЬ</th>
                        <th>АВГУСТ</th>
                        <th>СЕНТЯБРЬ</th>
                        <th>ОКТЯБРЬ</th>
                        <th>НОЯБРЬ</th>
                        <th>ДЕКАБРЬ</th>
                    </c:if>
                    <th>ИТОГО</th>
                </tr>
                <c:forEach var="userDtoList" items="${modelMap}" varStatus="count">
                    <tr>
                        <td>${userDtoList.key.name}</td>
                        <td>${userDtoList.key.position.position}</td>
                            <c:forEach var="total" items="${userDtoList.value}">
                                <td>${total}</td>
                            </c:forEach>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <div>
        <button type="button" class="btn" onclick="document.location='/admin/prepareHalfYearReport'">Назад</button>
        <input type="button" onclick="printDiv('printableArea')" value="Печать" class="btn"/>
    </div>
</div>
</body>
</html>
<script>
    function printDiv(divName) {
        var printContents = document.getElementById(divName).innerHTML;
        var originalContents = document.body.innerHTML;

        document.body.innerHTML = printContents;

        window.print();

        document.body.innerHTML = originalContents;
    }
</script>