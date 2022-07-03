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
    <div class="minibuttons">
        <c:forEach items="${questionList}" varStatus="count">
            <button id="minibtn${count.count}" class="minibtn" onclick="stepTo(${count.count})"></button>
        </c:forEach>
    </div>
    <c:forEach var="question" items="${questionList}" varStatus="count">
        <div id="wrapper${count.count}" style="display: none">
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
                            <td style="width: 10%;"><input name="check" class="check" type="checkbox" value="${answer.id}"></td>
                            <td class="tblsht">${answer.answerName}</td>
                        </tr>
                    </c:forEach>
                </table>
            </form>
            <button id="btn${count.count}" class="btn" onclick="saveUserAnswer(${question.question.id}, ${count.count}, ${questionList.size()})">Ответить</button>
            <button id="buttonch${count.count}" class="buttonch" onclick="skipAnswer(${count.count}, ${questionList.size()})">Пропустить</button>
        </div>
    </c:forEach>

    <div id="lastpage">
        <button class="btn" onclick="finishTest(${attemptId}, ${questionList.size()})">Завершить</button>
    </div >
</div>
</body>
<script>
    document.addEventListener("DOMContentLoaded", ready);
    function ready() {
        $('#wrapper1')
            .show()
            .addClass("visible");
        $('#minibtn1')
            .removeClass('minibtn')
            .addClass('selected');
    }

    function finishTest(attemptId, size){
        if ($('.right').length !== size){
           if (confirm('Есть вопросы с неполученными ответами. Завершение выполнения теста отметит их как неправильные. Вы действительно хотите завершить тест?')){
               document.location='/finishTest/${attemptId}';
           }
        }
        else {
            document.location='/processing/finishTest/${attemptId}'
        }
    }

    function stepTo(counter){
        let elem = $('.selected');
        elem.removeClass('selected');
        if (!elem.hasClass('right')){
            elem.addClass('minibtn');
        }

        $('.visible')
            .hide()
            .removeClass('visible');

        $('#wrapper' + counter)
            .show()
            .addClass('visible');

        $('#minibtn' + counter).addClass("selected");
    }

    function skipAnswer(counter, size){
        if (counter < size) {
            $('#wrapper' + counter)
                .hide()
                .removeClass('visible');

            let newcount = +(counter) + 1;
            $('#wrapper' + newcount)
                .show()
                .addClass('visible');
        }

        $('#minibtn' + counter)
            .removeClass('minibtn')
            .removeClass('selected')
            .addClass('skipped');

        let newcount = +(counter) + 1;
        $('#minibtn' + newcount).click();
    }

    function saveUserAnswer(id, counter, size){
        const msg = $('#form' + id).serialize();
            $.ajax({
                type: 'POST',
                url: '/processing/saveUserAnswer',
                data: msg,
                success: function (data) {
                    let elem = document.getElementById('wrapper' + counter);
                    let inputs = elem.getElementsByClassName("check");
                    for (let i = 0; i < inputs.length; i++) {
                        inputs[i].setAttribute("disabled", "disabled");
                    }

                    $('#minibtn' + counter)
                        .removeClass('skipped')
                        .removeClass('minibtn')
                        .removeClass('selected')
                        .addClass('right');

                    $('#btn' + counter).remove();
                    $('#buttonch' + counter).remove();

                    let newcount = +(counter) + 1;
                    $('#minibtn' + newcount).click();
                },
                error: function () {
                    alert('Выберите ответ!');
                }
            });
    }
</script>
</html>

