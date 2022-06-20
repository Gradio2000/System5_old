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
<script type="text/javascript" src="../../../js/jquery-3.6.0.js"></script>
<html>
<style>
    <%@include file="../../includes/myStyle.css"%>

</style>
<head>
    <jsp:include page="../../includes/menu.jsp"/>
    <jsp:include page="../../includes/header.jsp"/>
    <title>Список вопросов</title>
</head>
<body>

<div class="main">
    <table id="color_table" class="quesTable">
        <tbody>
        <tr>
            <th>№ п/п</th>
            <th>Вопрос</th>
            <th>Варианты ответов</th>
            <th>Правильный ответ</th>
        </tr>
        <c:forEach var="question" items="${questionList}" varStatus="count">
            <tr>
            <td rowspan="${question.answers.size() + 1}" style="text-align: justify">${count.count}</td>
                <td rowspan="${question.answers.size() + 1}">
                    <textarea style="width: 100%; border: none">${question.questionName}</textarea>
                    <c:forEach var="answer" items="${question.answers}">
                            <tr>
<%--                                <td style="text-align: justify">${answer.answerName}</td>--%>
                                <td style="width: 40%">
                                    <textarea style="width: 100%; height: 60px; border: none">${answer.answerName}</textarea>
                                </td>
                                <c:if test="${answer.isRight}">
                                    <td>Да</td>
                                </c:if>
                                <c:if test="${!answer.isRight}">
                                    <td></td>
                                </c:if>
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
    <form method="POST" action="/fileUpload" enctype="multipart/form-data">
        <input type="hidden" name="testId" value="${testId}">
        <input name="id" hidden value="${id}"/>
        <input type="file" name="file"><br />
        <button class="btn" type="submit" value="Upload">Загрузить</button>
    </form>

</div>
</body>
<script>

</script>
</html>

