<%--
  Created by IntelliJ IDEA.
  User: aleksejlaskin
  Date: 22.06.2022
  Time: 17:11
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

    <title>Тестирование</title>
</head>
<body>
<div class="main">
    <table id="color_table" style="width: 100%; table-layout: auto">
        <c:forEach var="question" items="${test.questions}" varStatus="count">
            <tr>
                <th colspan="2" class="tblsht">Вопрос ${count.count} из ${test.questions.size()}</th>
            </tr>
            <tr>
                <td colspan="2" class="tblsht">${question.questionName}</td>
            </tr>
            <c:forEach var="answer" items="${question.answers}">
                <tr>
                    <td style="width: 10%;"><input type="checkbox"></td>
                    <td class="tblsht">${answer.answerName}</td>
                </tr>
            </c:forEach>
        </c:forEach>
    </table>
</div>
</body>
</html>

