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
    <div id="printableArea">
        <table>
            <tr>
                <td>Работник</td>
                <td>${user.name}</td>
            </tr>
            <tr>
                <td>Должность</td>
                <td>${user.position.position}</td>
            </tr>
            <tr>
                <td>Дата и время</td>
                <td>
                    <fmt:formatDate value="${attempt.dateTime}" pattern="dd.MM.yyyy  HH:mm"/>
                </td>
            </tr>
            <tr>
                <td>Тест</td>
                <td>${attempt.test.testName}</td>
            </tr>
            <tr>
                <td>Количество заданных вопросов</td>
                <td>${attempt.amountQues}</td>
            </tr>
            <tr>
                <td>Количество правильных ответов</td>
                <td>${attempt.amountTrueAnswers}</td>
            </tr>
            <tr>
                <td>Количество неверных ответов</td>
                <td>${attempt.amountFalseAnswers}</td>
            </tr>
            <tr>
                <td>Критерий прохождения теста</td>
                <td>${attempt.criteria}</td>
            </tr>
            <tr>
                <td>Результат, %</td>
                <td>${attempt.result} %</td>
            </tr>
            <tr>
                <td>Итого</td>
                <td>${attempt.testResult}</td>
            </tr>
        </table>
        <table>
            <tr>
                <th style="width: 5%">№п/п</th>
                <th>Вопрос</th>
                <th>Ответы</th>
                <th style="width: 10%">Правильный ответ</th>
                <th style="width: 10%">Ответ тестируемого</th>
            </tr>
            <c:forEach var="ques" items="${quesList}" varStatus="count">
                <tr>
                <c:if test="${falseUserAnswers.contains(ques.id)}">
                    <td style="background: #e79696" rowspan="${ques.answers.size() + 1}">${count.count}</td>
                </c:if>
                <c:if test="${!falseUserAnswers.contains(ques.id)}">
                    <td style="background: lightgreen" rowspan="${ques.answers.size() + 1}">${count.count}</td>
                </c:if>

                <td rowspan="${ques.answers.size() + 1}" style="text-align: justify">${ques.questionName}</td>
                <c:forEach var="answer" items="${ques.answers}">
                    <c:set var="id" value="${answer.id}"/>
                    <tr>
                        <td style="text-align: justify">${answer.answerName}</td>
                        <td>
                            <c:if test="${answer.isRight}">
                                V
                            </c:if>
                        </td>
                        <c:if test="${listOfUsersAnswers.contains(id)}">
                            <td>V</td>
                        </c:if>
                        <c:if test="${!listOfUsersAnswers.contains(id)}">
                            <td></td>
                        </c:if>
                    </tr>
                </c:forEach>
                </tr>
            </c:forEach>
        </table>
    </div>

    <button class="btn" onclick="history.back()">Назад</button>
    <input type="button" onclick="printDiv('printableArea')" value="Печать" class="btn"/>
</div>
</body>
<script>
    function printDiv(divName) {
        const printContents = document.getElementById(divName).innerHTML;
        const originalContents = document.body.innerHTML;

        document.body.innerHTML = printContents;

        window.print();

        document.body.innerHTML = originalContents;
    }
</script>
</html>
