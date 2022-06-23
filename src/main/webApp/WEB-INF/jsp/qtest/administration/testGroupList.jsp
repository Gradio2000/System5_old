<%--
  Created by IntelliJ IDEA.
  User: aleksejlaskin
  Date: 14.06.2022
  Time: 10:13
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
    <title>Группы тестов</title>
</head>
<body>

    <div class="main">
        <table id="color_table" style="width: 100%; table-layout: auto" >
            <tbody>
            <tr>
                <th colspan="4" class="tblsht">Группы тестов</th>
            </tr>
            <tr>
                <th>Удалить</th>
                <th>Тесты группы</th>
                <th>Название группы</th>
                <th>Сохранить изменения</th>
            </tr>
            <c:forEach var="groupTest" items="${groupTests}">
                <form id="editGroup${groupTest.grouptestId}">
                    <tr>
                        <td style="width: 10%;">
                            <input form="del" value="${groupTest.grouptestId}" type="checkbox" name="check"/>
                        </td>
                        <td class="tblsht" style="width: 10%;">
                            <a href="/tests/list/${groupTest.grouptestId}">Список тестов</a>
                        </td>
                        <td>
                            <input type="text" class="myinput" name="name" value="${groupTest.name}" onchange="changeData(${groupTest.grouptestId})" style="margin-top: 0; padding: 0"/>
                        </td>
                        <td>
                            <button id="btnch${groupTest.grouptestId}" type="button" class="btnch buttonch" style="margin-top: 0; padding: 0; width: 30px" onclick="changeGroupName(${groupTest.grouptestId})"> V </button>
                        </td>
                    </tr>
                </form>
            </c:forEach>
            <div>
                <a style="color: crimson; font: bold italic 110% serif">
                    <c:if test="${param.get('error') == 100}">Не выбрана группа тестов!</c:if>
                    <c:if test="${param.get('error') == 200}">Введите название группы тестов!</c:if>
                </a>
            </div>

            </tbody>
        </table>

        <form id="add" action="/testGroup/add" method="post">
            <input name="name" class="myinput" type="text" placeholder="Введите название группы тестов"/>
        </form>

        <form id="del" action="/testGroup/delete" method="post"></form>

        <button form="add" name="addDiv" id="mybtn" type="submit" class="btn">Добавить</button>
        <button form="del" name="delete" type="submit" class="btncancel">Удалить</button>

    </div>
</body>
<script>

    function changeData(id){
        let el =  document.getElementById('btnch' + id);
        $(el).show();
    }

    function changeGroupName(id){
        const msg = document.getElementById("editGroup" + id);
        let d = $(msg).serializeArray();
        $.ajax({
            type: 'POST',
            url: '/testGroup/edit/' + id,
            data: d,
            success: function (data) {
                let el =  document.getElementById('btnch' + id);
                $(el).hide();
                console.log(d);
            },
            error: function () {
                alert('Ошибка изменения теста! Обратитесь к администратору!');
                console.log(d);
            }
        });
    }
</script>
</html>
