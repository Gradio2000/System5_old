<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: aleksejlaskin
  Date: 23.04.2022
  Time: 23:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <a href="/list">Посмотреть все оценки</a>
    <br/>
    <a href="/getUser">Сведения о пользователе</a>
    <br/>
    <a href="/admin/shtat">Получить штатное расписание</a>
    <br/>
    <a href="javascript:get_employers()">Посмотреть подчиненных сотрудников</a>
    <br/>
    <br/>
    <p>Получить отчет за месяц</p>
    <br/>
    <form method="get" action="/admin/report">
        <select id="monsel" name="month" class="select-css" style="width: max-content"></select>
        <button type="submit" class="btn">Сформировать</button>
    </form>

    <p class="zzz"></p>
    <br/>
    <a href="/logout">Выйти</a>

</body>

<style>
    <%@include file="myStyle.css"%>
</style>

<script>
    document.addEventListener("DOMContentLoaded", ready);
    function ready(){
        $.ajax({
            url: "/getMonth"
        }).then(function (months){
            let select = document.getElementById("monsel");
            for (let i = 0; i < months.length; i++) {
                let option = document.createElement("option");
                option.innerText = months[i];
                select.append(option);
            }
        });
    }


   function get_employers() {
        $.ajax({
            url: "/my_employers"
        }).then(function(data) {
            for (let i = 0; i < data.length; i++){
                let position = data[i].position;
                $(".zzz").append(`<a href="/list/` + data[i].user.userId + `">` +
                    data[i].position + " " + data[i].user.name + `</a>` + `<br/>`);
            }
        });
    };

</script>
</html>
