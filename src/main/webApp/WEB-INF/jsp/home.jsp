<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: aleksejlaskin
  Date: 23.04.2022
  Time: 23:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="../../js/jquery-3.6.0.js"></script>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="../includes/header.jsp"/>
<jsp:include page="../includes/menu.jsp"/>
<div class="main">
    <form id="monsel" method="get" action="/admin/report"></form>
</div>

</body>
</html>

<style>
    <%@include file="../includes/myStyle.css"%>
</style>

<script>
    function get_report(){
        $.ajax({
            url: "/getMonth"
        }).then(function (months){
            let el = document.getElementById("monsel");
            let select = document.createElement("select");
            select.name = "month";
            select.className = "select-css";
            select.style = "width: max-content";
            for (let i = 0; i < months.length; i++) {
                let option = document.createElement("option");
                option.innerText = months[i];
                select.append(option);
            }
            el.append(select);

            let button = document.createElement("button");
            button.type = "submit";
            button.className = "btn";
            button.innerText = "Сформировать";
            el.append(button);
        });
    }

    function get_employers(){
        document.location="/home";
        $(".employer").remove();
        let p = document.createElement("p");
        p.id = "employers";
        p.innerText = "Оцените, пожалуйста, работу сотрудников:"
        $(".main").append(p);
        $.ajax({
            url: "/my_employers"
        }).then(function(data) {
            console.log(data)
            for (let i = 0; i < data.length; i++){
                $("#employers").append(`<br class="employer"/><a class="employer" href="/list/` + data[i].user.userId + `">` +
                data[i].position + " " + data[i].user.name + `</a>`);
            }
        });
    }
</script>