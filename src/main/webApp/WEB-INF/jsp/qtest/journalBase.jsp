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

    <title>Общий журнал</title>
</head>
<body>
<div class="main">
    <div style="display: flex; justify-content: space-between">
        <div class="pagination">
            <a onclick="pagin(${appointTestDtoPage.previousOrFirstPageable().pageNumber}, ${appointTestDtoPage.size})">«</a>
            <c:forEach begin="1" end="${appointTestDtoPage.totalPages}" varStatus="count">
                <a id="pag${count.count - 1}" onclick="pagin(${count.count - 1}, ${appointTestDtoPage.size})">${count.count}</a>
            </c:forEach>
            <a onclick="pagin(${appointTestDtoPage.nextOrLastPageable().pageNumber}, ${appointTestDtoPage.size})">»</a>
        </div>
        <div class="sort" style="margin-right: 20px; margin-top: 6px">
            <label>Сортировать по:
                <select onchange="changeSort(this.value)">
                    <option value="up">возрастанию даты</option>
                    <option value="down">убыванию даты</option>
                </select>
            </label>
        </div>
        <div style="margin-right: 20px; margin-top: 6px">
            <label>Показывать по:
                <select class="chosen-select" onchange="changeSelect(this.value)">
                    <option data-value="10">10</option>
                    <option data-value="20">20</option>
                    <option data-value="100">100</option>
                    <option data-value="1000">1000</option>
                </select>
            </label>
        </div>
    </div>

    <table id="color_table" style="width: 100%">
        <tbody>
        <tr>
            <th>Дата и время</th>
            <th>Работник</th>
            <th style="width: 40%">Название теста</th>
            <th>Результат</th>
            <th>Протокол</th>
        </tr>
        <c:forEach var="appointTestDto" items="${appointTestDtoPage.content}">
            <tr>
                <td>
                    <fmt:formatDate value="${appointTestDto.attempttestDto.dateTime}" pattern="dd.MM.yyyy  HH:mm"/>
                </td>
                <td>${appointTestDto.userDtoNameOnlyWithPositionDto.name} ${appointTestDto.userDtoNameOnlyWithPositionDto.positionDtoNameOnly.position}</td>
                <td>${appointTestDto.testName}</td>
                <td>${appointTestDto.attempttestDto.testResult}</td>
                <c:if test="${appointTestDto.attempttestDto.testResult != 'Не завершен'}">
                    <td><a href="/qtest/report/${appointTestDto.attempttestDto.id}">Протокол</a></td>
                </c:if>
                <c:if test="${appointTestDto.attempttestDto.testResult == 'Не завершен'}">
                    <td></td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>
</body>
<script>
    document.addEventListener("DOMContentLoaded", ready);
    function ready(){
        $('#pag${appointTestDtoPage.pageable.pageNumber}').addClass("active");
        $('.chosen-select')
            .find(`option[data-value="${appointTestDtoPage.size}"]`)
            .prop('selected', true)
            .end()

        $('.sort')
            .find(`option[value="${sort}"]`)
            .prop('selected', true)
            .end()
    }

    function pagin(pageNumber, size){
        document.location='/exam/journalBase?page=' + pageNumber + '&size=' + size + '&sort=${sort}';
    }

    function changeSelect(size){
        document.location='/exam/journalBase?page=0&size=' + size;
    }

    function changeSort(value){
        let size = ${appointTestDtoPage.size};
        document.location='/exam/journalBase?page=0&size=' + size + '&sort=' + value;
    }
</script>
</html>
