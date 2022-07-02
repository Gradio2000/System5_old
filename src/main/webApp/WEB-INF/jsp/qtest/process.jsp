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

    <c:forEach var="question" items="${questionList}" varStatus="count">
        <div id="wrapper${count.count}" hidden>
            <form id="form${question.question.id}">
                <input name="attemptId" type="hidden" value="${attemptId}">
                <input name="questionId" type="hidden" value="${question.question.id}">
                <table id="color_table" style="width: 100%; table-layout: auto">
                    <tr>
                        <th colspan="2" class="tblsht">Вопрос ${count.count} из ${questionList.size()}</th>
                    </tr>
                    <tr>
                        <td colspan="2" class="tblsht">${question.question.questionName}</td>
                    </tr>
                    <c:forEach var="answer" items="${question.question.answers}">
                        <tr>
                            <td style="width: 10%;"><input name="check" type="checkbox" value="${answer.id}"></td>
                            <td class="tblsht">${answer.answerName}</td>
                        </tr>
                    </c:forEach>
                </table>
            </form>
            <button id="${count.count}" class="btn" onclick="saveUserAnswer(${question.question.id}, this.id, ${questionList.size()})">Ответить</button>
            <button class="buttonch" onclick="skipAnswer(${count.count}, ${questionList.size()})">Пропустить</button>
        </div>
    </c:forEach>

    <div class="minibuttons">
        <c:forEach items="${questionList}" varStatus="count">
            <button id="minibtn${count.count}" class="minibtn" onclick="stepTo(${count.count})"></button>
        </c:forEach>
    </div>

    <div id="lastpage" style="display: none">
        <p style="font-family: 'Courier New',cursive">Спасибо! Вы завершили тест и можете посмотреть результат!</p>
        <button class="btn" onclick="document.location='/getResultTest/${attemptId}'">Результат</button>
    </div >
</div>
</body>
<script>
    document.addEventListener("DOMContentLoaded", ready);
    function ready() {
        let elem = $('#wrapper1')
        elem.show();
        elem.addClass(" visible");
    }

    function stepTo(counter){
        $('.visible').hide();
        let elem = $('#wrapper' + counter);
        elem.show();
        elem.addClass("  visible");
    }

    function skipAnswer(counter, size){
        if (counter < size) {
            $('#wrapper' + counter).hide();
            let newcount = +(counter) + 1;
            $('#wrapper' + newcount).show();
            console.log(counter, + " " + size);
        }
        let elem = $('#minibtn' + counter);
        elem.removeClass('minibtn');
        elem.addClass('skipped');
    }

    function saveUserAnswer(id, counter, size){
        const msg = $('#form' + id).serialize();
            $.ajax({
                type: 'POST',
                url: '/processing/saveUserAnswer',
                data: msg,
                success: function (data) {
                    $('#wrapper' + counter).hide();
                    let newcount = +(counter) + 1;
                    if (counter < size) {
                        $('#wrapper' + newcount).show();
                        let elem = $('#minibtn' + counter);
                        elem.removeClass('minibtn');
                        elem.addClass('right');
                    }
                    else {
                        $('.minibuttons').hide();
                        $('#lastpage').show();
                    }
                },
                error: function () {
                    alert('Выберите ответ!');
                    console.log(msg);
                }
            });
    }
</script>
</html>

