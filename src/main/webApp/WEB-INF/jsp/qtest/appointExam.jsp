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

    <title>Назначение зачетов</title>
</head>
<body>
<div class="main">
    <form id="selectUser">

            <select class="select-css" id="userIdSelect" name="userId" onchange="getUsersAppoints()"
                    style="width: max-content;">
                <option value="" disabled selected>Выберите работника</option>
                <c:forEach var="user" items="${userDtoList}">
                    <option value="${user.userId}">${user.name}</option>
                </c:forEach>
            </select>

    </form>
    <table id="color_table" style="width: 100%">
        <tbody>
            <tr>
                <th>Название теста</th>
                <th>Назначить</th>
            </tr>
            <c:forEach var="groupTest" items="${groupTestDtoList}">
                <tr>
                    <th colspan="2">группа: ${groupTest.name}</th>
                </tr>
                <c:forEach var="test" items="${groupTest.testDtoList}">
                    <tr>
                        <td>${test.testName}</td>
                        <td><input type="checkbox" class="appointCheck" id="${test.testId}" disabled
                                   onchange="appointExam(${test.testId})"/></td>
                    </tr>
                </c:forEach>
            </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
<script>
    function appointExam(testId) {
        let userId = document.getElementById("userIdSelect").value;
        $.ajax({
            type: 'POST',
            url: '/exam/appointExam',
            data: {"testId": testId,  "userId": userId},
            success: function (data) {
                // запустится при успешном выполнении запроса и в data будет ответ скрипта
            },
            error: function () {
                alert('Ошибка!');
            }
        });

    }

    function getUsersAppoints(){
        let elem = $("#selectUser").serialize();
        $.ajax({
            type: 'GET',
            url: '/exam/getUsersAppoints',
            data: elem,
            success: function (data) {
               let elem = document.getElementsByClassName("appointCheck");
                for (let i = 0; i < elem.length; i++) {
                    elem[i].removeAttribute("disabled");
                    elem[i].checked = false;
                }

                for (let i = 0; i < data.length; i++) {
                    let el = document.getElementById(data[i]);
                    el.checked = true;
                }
            },
            error: function () {
                alert('Ошибка!');
            }
        });
    }

</script>
