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
            <th>Количество вопросов</th>
        </tr>
        <c:forEach var="groupTest" items="${groupTestDtoList}">
            <tr>
                <th colspan="3">группа: ${groupTest.name}</th>
            </tr>
            <c:forEach var="test" items="${groupTest.testDtoList}">
                <tr>
                    <td>${test.testName}</td>
                    <td><input type="checkbox" class="appointCheck" id="${test.testId}"
                               value="${test.quesAmount}" disabled onchange="getModalView(${test.testId})"/>
                    </td>
                    <td><input type="number" id="checkQuesAmount${test.testId}" class="myinput" value="${test.quesAmount}"
                               onchange="check(${test.quesAmount}, this.value, ${test.testId})"
                               style="padding: 0; margin: 0" disabled/>
                    </td>
                </tr>
            </c:forEach>
        </c:forEach>
        </tbody>
    </table>
    <div style="margin-top: 20px;">
        <a>Всего выбрано: <span id="totalQuesAmount"></span> вопросов</a>
    </div>
    <div style="margin-top: 20px; display: none">
        <label>Введите наименование сводного теста</label>
        <input id="testNameTr" class="myinput" form="examAppointForm" type="text" name="base" style="height: 0; margin-top: 0"/>
    </div>
    <div style="margin-top: 20px">
        <label>Введите номер и дату распорядительного документа</label>
        <input class="myinput" form="examAppointForm" type="text" name="base" style="height: 0; margin-top: 0"/>
    </div>
    <div style="margin-top: 20px">
        <input form="examAppointForm" type="checkbox" name="eko" style="margin-left: 10px"/>
        <a> проверка знания порядка ведения ЭКО</a>
    </div>
    <div>
        <button class="btn">Назначить</button>
    </div>
</div>



<form id="examAppointForm"></form>
</body>
</html>
<script>
    const elems = document.body.querySelectorAll('.appointCheck');

    for (let x = 0; x < elems.length; x++) {
        elems[x].addEventListener("click", listener)
    }

    function check(quesAmont, value, id){
        console.log(quesAmont, value, id);
        if (value > quesAmont){
            alert("Превышено допустимое значение количества вопросов для теста: " + quesAmont);
            document.getElementById("checkQuesAmount" + id).value = quesAmont;
            return;
        }
        printTotalAmount();
    }


    function printTotalAmount(){
        let total = 0;
        let count = 0;
        for (let i = 0; i < elems.length; i++) {
            let elem = document.getElementById("checkQuesAmount" + elems[i].id);
            if (elems[i].checked){
                count++;
                elem.disabled = false;
                let quesAmount = Number(elem.value);
                total = total + quesAmount;
            }
            else{
                elem.disabled = true;
            }
        }
        if(count > 1){
            $('#testNameTr').show();
        }
        else {
            $('#testNameTr').hide();
        }
        document.getElementById("totalQuesAmount").innerText = total;
    }

    function getModalView(testId){
        printTotalAmount();


        let checkTest = document.getElementById(testId);
        let checkQuesAmount = document.getElementById("checkQuesAmount" + testId);
        checkQuesAmount.disabled = !checkTest.checked;

        let form =  $('#examAppointForm');
        $('.inpClass').remove();

        let inptestId = document.createElement("input");
        inptestId.type = "hidden";
        inptestId.className = "inpClass";
        inptestId.form = "examAppointForm";
        inptestId.name = "testId";
        inptestId.value = testId;
        form.append(inptestId);


        let inpUserId = document.createElement("input");
        inpUserId.type = "hidden";
        inpUserId.className = "inpClass";
        inpUserId.form = "examAppointForm";
        inpUserId.name = "userId";
        inpUserId.value = document.getElementById("userIdSelect").value;
        form.append(inpUserId);

    }

    function appointExam() {
        document.location='#close';
        const msg = $('#examAppointForm').serialize();
        $.ajax({
            type: 'POST',
            url: '/exam/appointExam',
            data: msg,
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
