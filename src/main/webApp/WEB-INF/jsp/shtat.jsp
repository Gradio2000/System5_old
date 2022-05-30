<%--
  Created by IntelliJ IDEA.
  User: aleksejlaskin
  Date: 10.05.2022
  Time: 19:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c"   %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"       prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql"       prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml"       prefix="x"   %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"  %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"  %>
<script type="text/javascript" src="../../js/jquery-3.6.0.js"></script>


<html>
<head>

    <title>Штат</title>
</head>
<body>
<jsp:include page="../includes/header.jsp"/>
<jsp:include page="../includes/menu.jsp"/>
<div class="main">
<table  id="color_table">
    <tbody>
    <tr>
        <th class="tblsht">Структурное подразделение</th>
    </tr>
    <c:forEach var="division" items="${divisionList}">
    <tr>
        <td class="tblsht">
            <a id=${division.divisionId}>${division.division}</a>
        </td>
    </tr
    </c:forEach>

        <label style="color: crimson; font: bold italic 110% serif">
            <c:if test="${param.get('errordivision') == true}">Введите название структурного подразделения!</c:if>
            <c:if test="${param.get('errorposition') == true}">Введите название должности!</c:if>
        </label>
    </tbody>
</table>

<button name="addDiv" id="mybtn" type="button" class="btn" onclick="insertInputText()">Добавить</button>
<button name="addDiv" id="mybtnCh" type="button" class="buttonch btnch" onclick="changeDivision()">Изменить</button>
<button name="addDiv" id="mybtnDel" type="button" class="btncancel btndel" onclick="deleteDivision()">Удалить</button>
<div id="ins"></div>

<br/>
<br/>
<table class="iksweb" id="color_table1">
    <tbody >
    <tr>
        <th class="tblsht">Должности</th>
        <th class="tblsht">Работники</th>
        <th class="tblsht">Администратор</th>
    </tr>
    </tbody>
</table>
<div>
    <div id="insbtn" style="width: 33.33%; float: left"></div>
    <div id="deluserbtn" style="width: 33.33%; float: left"></div>
</div>
</br>

<div id="insdelbtn"></div>




<form id="addDivision" name="addDivision" method="post" action="/admin/division"></form>
<form id="insertUser" name="insertUser" method="post" action="/admin/user/insert"></form>
<form id="changeDivision" name=changeDivision" method="post" action="/admin/division/change"></form>
<form id="changePosition" name=changePosition" method="post" action="/admin/position/change"></form>
<form id="addPosition" name="addPosition" method="post" ></form>

    <div style="position: absolute; margin-top: 100px">
        <button type="button" class="btn" onclick="document.location='/list'">На главную</button>
    </div>
</div>
</body>

<script>

    highlight_Table_Rows("color_table", "hover_Row", "clicked_Row");
    highlight_Table_Rows("color_table1", "hover_Row", "clicked_Row");

    let el = document.getElementById("color_table");
    el.addEventListener("click", getPositions);

    let el1 = document.getElementById("color_table1");
    el1.addEventListener("click", selectPosition);


    function getPositions(){
        getShtat();
        $('.insert').remove();
        $('#butdel').remove();
        $('#butch').remove();
        $('.remPos').remove();
        $('#butDelUser').remove();

        let el = document.getElementById("color_table").getElementsByClassName("clicked_Row").item(0).children.item(0).children.item(0);
        let id = el.id;

        let elem = $('#color_table1 > tbody:last-child');

        $.ajax({
            url: "/admin/positions/" + id
       }).then(function(data) {
            if (data.myer === true){
                insertButton(id);
            } else {
                const data1 = data._embedded.positions;
                let userName;
                for (let i = 0; i < data1.length; i++) {
                    let position = data1[i].position;
                    if (data1[i].user == null) {
                        userName = "";
                    } else userName = data1[i].user.name;

                    let elem1 = document.createElement("tr");
                    elem1.setAttribute("class", "insert");

                    let elem2 = document.createElement("td");
                    elem2.setAttribute("class", "tblsht");
                    elem2.setAttribute("id", data1[i].position_id);
                    elem2.innerText = data1[i].position;

                    let elem3 = document.createElement("td");
                    elem3.setAttribute("class", "tblsht");

                    let elem4 = document.createElement("td");
                    elem4.setAttribute("class", "tblsht");
                    elem4.id="forrole";


                    if  (data1[i].user != null){
                        let a = document.createElement("a");
                        a.setAttribute("id", "userNameInsert");
                        a.innerText = userName;
                        elem3.append(a);

                        let elcheck = document.createElement("input");
                        elcheck.type = "checkbox";
                        elcheck.name="checkrole";
                        if (data1[i].user.roles.includes("ADMIN")){
                            elcheck.checked = "checked";
                        }
                        elcheck.setAttribute("onchange", "showbutrol(" + data1[i].position_id + ")");
                        elem4.append(elcheck);
                    }

                    elem1.append(elem2);
                    elem1.append(elem3);
                    elem1.append(elem4);
                    elem.append(elem1);

                }
                $('#mybtnDel').show();
                $('#mybtnCh').show();
            }
        });

        insertButton(id);

        }

        function showbutrol(id){
            $.ajax({
                type: 'POST',
                url: '/admin/user/setrole',
                data: {"id": id},
                success: function (data) {
                    let elll = document.getElementById("forrole");
                    let a = document.createElement("a");
                    a.innerText = data.res;
                    elll.append(a);
                },
                error: function () {
                    alert('Ошибка!');
                    console.log(msg);
                }
            });


        }

    function selectPosition(){
            $('.userRem').remove();
            $('#butInsUser').remove();
            $('#butDelUser').remove();
            $('.rem').remove();
            $('.remPos').remove();
            $('#mybtn').hide();
            $('#butdel').remove();
            $('#butch').remove();
            $('#mybtnCh').hide();
            $('#mybtnDel').hide();
            $('#mybtnPos').show();

            let el = document.getElementById("color_table1").getElementsByClassName("clicked_Row").item(0).children.item(0);
            let id = el.id;

            insertDeleteAndChangeButton(id);
        }

    function insertButton(id) {
            $('.mybtnPos').remove();
            let el = document.getElementById("insbtn");
            let but = document.createElement("button");
            but.setAttribute("id", "mybtnPos");
            but.setAttribute("type", "button");
            but.setAttribute("onclick", "insertInputTextForPositions(" + id + ")");
            but.setAttribute("class", "btn mybtnPos");
            but.innerText = "Добавить";
            el.append(but);
        }

    function insertDeleteAndChangeButton(id){
        console.log(id);
        let userNameInTheTable = document
            .getElementById("color_table1")
            .getElementsByClassName("clicked_Row")
            .item(0).children
            .item(1).textContent;

        let el = document.getElementById("insbtn");
        let el1 = document.getElementById("deluserbtn");

        let butdel = document.createElement("button");
            butdel.setAttribute("id", "butdel");
            butdel.setAttribute("type", "button");
            butdel.setAttribute("onclick", "deletePosition(" + id + ")");
            butdel.setAttribute("class", "btncancel btndel");
            butdel.innerText = "Удалить";

        let butch = document.createElement("button");
            butch.setAttribute("id", "butch");
            butch.setAttribute("type", "button");
            butch.setAttribute("onclick", "changePosition()");
            butch.setAttribute("class", "buttonch btnch");
            butch.innerText = "Изменить"

            if (userNameInTheTable !== "") {
                let butDelUser = document.createElement("button");
                butDelUser.setAttribute("id", "butDelUser");
                butDelUser.setAttribute("type", "button");
                butDelUser.setAttribute("onclick", "deleteUser()");
                butDelUser.setAttribute("class", "btncancel btndel");
                butDelUser.innerText = "Уволить";
                el1.append(butDelUser);
            }
            if (userNameInTheTable === "") {
                let butInsUser = document.createElement("button");
                butInsUser.setAttribute("id", "butInsUser");
                butInsUser.setAttribute("type", "button");
                butInsUser.setAttribute("onclick", "insertUserInto()");
                butInsUser.setAttribute("class", "btn");
                butInsUser.innerText = "Принять";
                el1.append(butInsUser);
            }

        el.append(butch);
        el.append(butdel);

            $('#butch').show();
            $('#butdel').show();
            $('#butDelUser').show();
        }

    function deleteDivision(){
        let el = document.getElementById("color_table").getElementsByClassName("clicked_Row").item(0).children.item(0).children.item(0);
        let id = el.id;
        document.location.href = '/admin/division/' + id;
    }

    function changeDivision(){
        let el = document.getElementById("color_table").getElementsByClassName("clicked_Row").item(0).children.item(0).children.item(0);
        let id = el.id;
        insertInputTextForChangeDivision(id);
    }

    function deletePosition(id){

            document.location.href = '/admin/position/delete/' + id;
        }

    function changePosition(){
        let el = document.getElementById("color_table1").getElementsByClassName("clicked_Row").item(0).children.item(0);
        let id = el.id;
        insertInputTextForChangePositions(id);
    }

    function deleteUser(){
        let el = document.getElementById("color_table1").getElementsByClassName("clicked_Row").item(0).children.item(0);
        let id = el.id;

        document.location.href = '/admin/user/delete/' + id;
    }

    function insertUserInto(){
        $('#butInsUser').hide();
        let form = $('#insertUser');

        let el = document.getElementById("color_table1").getElementsByClassName("clicked_Row").item(0).children.item(0);


        let input = document.createElement("input");
        input.type = "hidden";
        input.form = "insertUser";
        input.value = el.id;
        input.name = "positionId";

        form.append(input);

        let selectInput = document.createElement("select");
        selectInput.className = "userRem select-css"
        selectInput.form = "insertUser";
        selectInput.name = "userId";
        selectInput.id = "insertUserInput";

        let br = document.createElement("br");
        br.className = "userRem";

        $('#deluserbtn')
            .append(br)
            .append(form)
            .append('<button type="submit" id="selectButton" class="btn userRem" form="insertUser">OK</button>')
            .append('<button type="button" class="btncancel userRem" onclick=cancel()>Отмена</button>');

        $.ajax({
            type: 'GET',
            url: '/admin/user/get',
            success: function (data) {
                let users = data._embedded.users
                console.log(users);
                for (let i = 0; i < users.length; i++) {
                    let option = document.createElement("option");
                    option.value = users[i].userId;
                    option.innerText = users[i].name;
                    selectInput.append(option);
                }
                form.append(selectInput);
            },
            error: function () {
                alert('Ошибка получения списка пользователей!');

            }
        });


    }

    function cancel(){
        $('.userRem').remove();
        $('#butInsUser').show();
    }

    function insertInputText(){
        $('#mybtn').hide();
        $('#mybtnDel').hide();
        $('#mybtnCh').hide();
        $('#ins')
            .append('<input class="myinput rem" form="addDivision" name="division" placeholder="Введите подразделение"/>')
            .append('<button type="submit" id="sendButton" class="btn rem" form="addDivision">OK</button>')
            .append('<button type="button" class="btncancel rem" onclick=getShtat()>Отмена</button>');
        }

    function insertInputTextForChangeDivision(id){
        $('#mybtn').hide();
        $('#mybtnDel').hide();
        $('#mybtnCh').hide();
        $('#ins')
            .append('<input type="hidden" class="myinput rem" form="changeDivision" name="id" value="' + id +'"/>')
            .append('<input class="myinput rem" form="changeDivision" name="divisionName" placeholder="Введите новое название"/>')
            .append('<button type="submit" id="sendButton" class="btn rem" form="changeDivision">OK</button>')
            .append('<button type="button" class="btncancel rem" onclick=getShtat()>Отмена</button>');
    }

    function insertInputTextForPositions(id){
        $('.mybtnPos').hide();
        $('#insbtn')
            .prepend('<input class="myinput remPos" form="addPosition" name="position" placeholder="Введите должность"/>')
            .append('<button type="submit" id="sendButtonPos" class="btn remPos" form="addPosition">OK</button>')
            .append('<input type="hidden" name="id" form="addPosition" value="' + id +'"/>')
            .append('<button type="button" class="btncancel remPos" onclick=getShtatPos()>Отмена</button>');
        $('#addPosition').attr("action", "/admin/position");

    }

    function insertInputTextForChangePositions(id){
        $('.mybtnPos').hide();
        $('#butch').remove();
        $('#butdel').remove();
        $('#insbtn')
            .prepend('<input class="myinput remPos" form="changePosition" name="position" placeholder="Введите новое название"/>')
            .append('<button type="submit" id="sendButtonPos" class="btn remPos" form="changePosition">OK</button>')
            .append('<input type="hidden" name="id" form="changePosition" value="' + id +'"/>')
            .append('<button type="button" class="btncancel remPos" form="changePosition" onclick=getShtatPos()>Отмена</button>');
        $('#sendButtonPos').attr("action", "/admin/position/change/");

    }

    function getShtat() {
        $('.rem').remove();
        $('#mybtn').show();
    }

    function getShtatPos() {
        $('.remPos').remove();
        $('.mybtnPos').show();
    }

    function highlight_Table_Rows(table_Id, hover_Class, click_Class, multiple) {
        $('#mybtnDel').show();
        $('#mybtnCh').show();
        var table = document.getElementById(table_Id);
        if (typeof multiple == 'undefined') multiple = false;

        if (hover_Class) {
            var hover_Class_Reg = new RegExp("\\b"+hover_Class+"\\b");
            table.onmouseover = table.onmouseout = function(e) {
                if (!e) e = window.event;
                var elem = e.target || e.srcElement;
                while (!elem.tagName || !elem.tagName.match(/td|th|table/i))
                    elem = elem.parentNode;

                if (elem.parentNode.tagName === 'TR' &&
                    elem.parentNode.parentNode.tagName === 'TBODY') {
                    var row = elem.parentNode;
                    if (!row.getAttribute('clicked_Row'))
                        row.className = e.type==="mouseover"?row.className +
                            " " + hover_Class:row.className.replace(hover_Class_Reg," ");
                }
            };
        }

         if (click_Class) table.onclick = function(e) {
            if (!e) e = window.event;
            var elem = e.target || e.srcElement;
            while (!elem.tagName || !elem.tagName.match(/td|th|table/i))
                elem = elem.parentNode;

            if (elem.parentNode.tagName === 'TR' &&
                elem.parentNode.parentNode.tagName === 'TBODY') {
                var click_Class_Reg = new RegExp("\\b"+click_Class+"\\b");
                var row = elem.parentNode;

                if (row.getAttribute('clicked_Row')) {
                    row.removeAttribute('clicked_Row');
                    row.className = row.className.replace(click_Class_Reg, "");
                    row.className += " "+hover_Class;
                }
                else {
                    if (hover_Class) row.className = row.className.replace(hover_Class_Reg, "");
                    row.className += " "+click_Class;
                    row.setAttribute('clicked_Row', true);

                    if (!multiple) {
                        var lastRowI = table.getAttribute("last_Clicked_Row");
                        if (lastRowI!==null && lastRowI!=='' && row.sectionRowIndex!==lastRowI) {
                            var lastRow = table.tBodies[0].rows[lastRowI];
                            lastRow.className = lastRow.className.replace(click_Class_Reg, "");
                            lastRow.removeAttribute('clicked_Row');
                        }
                    }
                    table.setAttribute("last_Clicked_Row", row.sectionRowIndex);
                }
            }

            // if (table_Id === "color_table"){
            //     getPositions();
            // }
            // else if (table_Id === "color_table1"){
            //     selectPosition();
            // }
        };
    }


</script>
<style>
    <%@include file="../includes/myStyle.css"%>
    .hover_Row { background-color: #e0dfdf; }
    .clicked_Row { background-color: #b1b0b0; }
</style>
</html>
