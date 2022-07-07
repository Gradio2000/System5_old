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
    <div class="pagination">
        <a onclick="pagin(${attemptsList.previousOrFirstPageable().pageNumber})">«</a>
        <c:forEach begin="1" end="${attemptsList.totalPages}" varStatus="count">
            <a id="pag${count.count - 1}" onclick="pagin(${count.count - 1})">${count.count}</a>
        </c:forEach>
        <a onclick="pagin(${attemptsList.nextOrLastPageable().pageNumber})">»</a>
    </div>
    <table id="color_table" style="width: 100%">
        <tbody>
            <tr>
                <th>Дата и время</th>
                <th style="width: 40%">Название теста</th>
                <th>Результат</th>
                <th>Протокол</th>
            </tr>
        <c:forEach var="attempt" items="${attemptsList.content}">
            <tr>
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
<script>
    document.addEventListener("DOMContentLoaded", ready);
    function ready(){
        $('#pag${attemptsList.pageable.pageNumber}').addClass("active");
    }

    function pagin(pageNumber){
        document.location='/tests/mytests/' + pageNumber;
    }

    function paginPrevious(){

    }
</script>
</html>
