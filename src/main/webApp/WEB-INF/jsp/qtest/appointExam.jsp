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
    <div style="margin-top: 90px">
        <form id="selectUser">
            <select class="select-css" id="userIdSelect" name="userId" onchange="getUsersAppoints(this.value)"
                    style="width: max-content;">
                <option value="" disabled selected>Выберите работника</option>
                <c:forEach var="user" items="${userDtoList}">
                    <option value="${user.userId}">${user.name}</option>
                </c:forEach>
            </select>
        </form>
    </div>
    <table id="color_table" style="width: 100%">
        <tbody>
        <tr>
            <th>Название теста</th>
            <th style="width: 10%">Назначить</th>
            <th style="width: 10%">Количество вопросов</th>
        </tr>
        <form id="examAppointForm">
            <c:forEach var="groupTest" items="${groupTestDtoList}">
                <tr>
                    <th colspan="3" style="text-align: left">группа: ${groupTest.name}</th>
                </tr>
                    <c:forEach var="test" items="${groupTest.testDtoList}">
                        <tr>
                            <td style="text-align: left">${test.testName}</td>
                            <td><input type="checkbox" class="appointCheck" id="${test.testId}"
                                       name="testIds"
                                       value="${test.testId}" disabled onchange="selectTest(${test.testId})"/>
                            </td>
                            <td><input type="number" id="checkQuesAmount${test.testId}" class="myinput" value="${test.quesAmount}"
                                       name="quesAmounts"
                                       onchange="check(${test.quesAmount}, this.value, ${test.testId})" style="padding: 0; margin: 0" disabled/>
                            </td>
                        </tr>
                    </c:forEach>
            </c:forEach>
        </form>
        </tbody>
    </table>
    <div id="totQuesAm" class="hidEl" style="margin: 10px; font-size: small; font-weight: bold; font-style: italic; display: none">
        <a>Всего выбрано: <span id="totalQuesAmount"></span> вопросов</a>
    </div>
    <div id="criteria" class="hidEl" style="margin: 10px; margin-top: 30px; font-size: small; display: none">
        <label>Установите критерий в процентах</label>
        <input class="myinput" form="examAppointForm" type="number" name="criteria" style="height: 0; margin-top: 0"/>
    </div>
    <div id="testNameTr"  style="margin: 10px; margin-top: 30px; font-size: small; display: none">
        <label>Введите наименование сводного теста</label>
        <input class="myinput" form="examAppointForm" type="text" name="consolidTestName" style="height: 0; margin-top: 0"/>
    </div>
    <div id="baseDoc" class="hidEl" style="margin: 10px; font-size: small; display: none">
        <label>Введите номер и дату распорядительного документа</label>
        <input class="myinput" form="examAppointForm" type="text" name="baseDocName" style="height: 0; margin-top: 0"/>
    </div>
    <div id="eko" class="hidEl" style="font-size: small; display: none">
        <input form="examAppointForm" type="checkbox" name="eko" style="margin-left: 10px"/>
        <a> проверка знания порядка ведения ЭКО</a>
    </div>
    <div style="margin: 10px">
        <button class="btn" onclick="appointExam()">Назначить</button>
    </div>
    <div id="divTable" style="margin-top: 20px; display: none">
        <table id="color_table2" style="width: 100%">
            <tr>
                <th>Назначенные зачёты</th>
                <th style="width: 10%">Статус</th>
                <th style="width: 10%">Удалить</th>
            </tr>
        </table>
    </div>
</div>


</body>
</html>
<script>
    const elems = document.body.querySelectorAll('.appointCheck');

    // for (let x = 0; x < elems.length; x++) {
    //     elems[x].addEventListener("click", listener)
    // }

    function check(quesAmont, value, id){
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

    function selectTest(testId){
        $('.hidEl').show();
        printTotalAmount();

        let checkTest = document.getElementById(testId);
        let checkQuesAmount = document.getElementById("checkQuesAmount" + testId);
        checkQuesAmount.disabled = !checkTest.checked;

        let form =  $('#examAppointForm');
        $('.inpClass').remove();


        let inpUserId = document.createElement("input");
        inpUserId.type = "hidden";
        inpUserId.className = "inpClass";
        inpUserId.form = "examAppointForm";
        inpUserId.name = "userId";
        inpUserId.value = document.getElementById("userIdSelect").value;
        form.append(inpUserId);

    }

    function appointExam() {
        const msg = $('#examAppointForm').serializeArray();
        const userId = msg.find(el => el.name === "userId").value;
        $('.hidEl').hide();
        $('#testNameTr').hide();
        $.ajax({
            type: 'POST',
            url: '/exam/appointExam',
            data: msg,
            success: function (data) {
                getUsersAppoints(userId);
            },
            error: function () {
                alert('Ошибка!');
            }
        });
    }

    function getUsersAppoints(userId){
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

                let divTable = $('#divTable');
                divTable.hide();
                $('.trTable').remove();
                for (let i = 0; i < data.length; i++) {
                    let tr = document.createElement("tr");
                    tr.className = "trTable";

                    let td2 = document.createElement("td");
                    td2.innerText = data[i].testName;
                    td2.style = "text-align: left";

                    let td3 = document.createElement("td");
                    td3.innerText = "назначен";

                    let td4 = document.createElement("td");

                    let btn = document.createElement("button");
                    btn.className = "btncancel";
                    btn.type = "button";
                    btn.style = "margin-top: 0; padding: 3px";
                    btn.setAttribute("onclick", "deleteAppoint(" + data[i].id + ", " + userId + ")");
                    btn.innerText = "Удалить";

                    td4.append(btn);
                    tr.append(td2);
                    tr.append(td3);
                    tr.append(td4);

                    document.getElementById("color_table2").append(tr);
                    divTable.show();
                }
            },
            error: function () {
                alert('Ошибка!');
            }
        });
    }

    function deleteAppoint(appointId, userId){
        $.ajax({
           type: 'POST',
           data: {appointId: appointId},
           url: '/exam/deleteAppoint',
           success: function (data){
               $('.hidEl').hide();
               $('#testNameTr').hide();
               getUsersAppoints(userId);
           },
            error: function (){
               alert("Ошибка удаления записи! \n function deleteAppoint(userId, testId)");
            }
        });
    }

</script>
