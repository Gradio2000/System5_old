<%--
  Created by IntelliJ IDEA.
  User: aleksejlaskin
  Date: 15.06.2022
  Time: 12:32
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
    <jsp:include page="../../../includes/menu.jsp"/>
    <jsp:include page="../../../includes/header.jsp"/>
    <title>Список вопросов</title>
</head>
<body>

<div class="main">
    <table id="color_table" class="quesTable">
        <tbody>
        <tr>
            <th>Удалить</th>
            <th>№ п/п</th>
            <th>Вопрос</th>
            <th>Варианты ответов</th>
            <th>Правильный ответ</th>
        </tr>
        <c:forEach var="question" items="${questionList}" varStatus="count">
            <tr>
            <td rowspan="${question.answers.size() + 1}">
                <input form="del" value="${question.id}" type="checkbox" name="check"/>
            </td>
            <td rowspan="${question.answers.size() + 1}" style="text-align: justify">${count.count}</td>
                <td rowspan="${question.answers.size() + 1}">
                    <form id="editQues${question.id}">
                        <textarea name="questionName" style="width: 100%; height: 60px; border: none" onchange="editQues(${question.id})">${question.questionName}</textarea>
                    </form>
                    <c:forEach var="answer" items="${question.answers}">
                            <tr>
                                <form id="editAnswer${answer.id}">
                                    <td style="width: 40%">
                                        <textarea name="answerName" style="width: 100%; height: 60px; border: none" onchange="editAnswer(${answer.id})">${answer.answerName}</textarea>
                                    </td>
                                    <c:if test="${answer.isRight}">
                                        <td>
                                            <textarea name="isRight" style="width: 100%; height: 60px; border: none" onchange="editAnswer(${answer.id})">Да</textarea>
                                        </td>
                                    </c:if>
                                    <c:if test="${!answer.isRight}">
                                        <td>
                                            <textarea name="isRight" style="width: 100%; height: 60px; border: none" onchange="editAnswer(${answer.id})"></textarea>
                                        </td>
                                    </c:if>
                                </form>
                            </tr>
                    </c:forEach>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br>
    <a style="color: crimson; font: bold italic 110% serif">
        <c:if test="${param.get('error') == 100}">Произошла ошибка при загрузке файла с вопросами. Проверьте его структуру!</c:if>
    </a>
    <form id="load" method="POST" action="/fileUpload" enctype="multipart/form-data">
        <input type="hidden" name="testId" value="${testId}">
        <input name="id" hidden value="${id}"/>
        <input type="file" name="file"><br />
    </form>

    <form id="del" action="/questions/delete" method="post" onsubmit="alert('Внимание! Вы действительно хотите удалить выбранные вопросы?')">
        <input type="hidden" name="testId" value="${testId}">
    </form>

    <button form="load" class="btn" type="submit" value="Upload">Загрузить</button>
    <button form="del" name="delete" type="submit" class="btncancel">Удалить</button>

</div>
</body>
<script>
    function editQues(id){
        const msg = document.getElementById("editQues" + id);
        let d = $(msg).serializeArray();
        console.log(d);
        $.ajax({
            type: 'POST',
            url: '/questions/edit/' + id,
            data: d,
            success: function (data) {

            },
            error: function () {
                alert('Ошибка изменения вопроса! Обратитесь к администратору!');
                console.log(d);
            }
        });
    }

    function editAnswer(id){
        const msg = document.getElementById("editAnswer" + id);
        let d = $(msg).serializeArray();
        $.ajax({
            type: 'POST',
            url: '/answer/edit/' + id,
            data: d,
            success: function (data) {

            },
            error: function () {
                alert('Ошибка изменения ответа! Обратитесь к администратору!');
                console.log(d);
            }
        });
    }
</script>
</html>

