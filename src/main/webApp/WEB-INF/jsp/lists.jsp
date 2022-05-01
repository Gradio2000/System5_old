<%--
  Created by IntelliJ IDEA.
  User: aleksejlaskin
  Date: 01.05.2022
  Time: 21:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c"   %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"       prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql"       prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml"       prefix="x"   %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"  %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"  %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table class="table">
    <tbody>
    <tr>
        <th rowspan="2">Месяц</th>
        <th colspan="2">Показатель 1</th>
        <th colspan="2">Показатель 2</th>
        <th colspan="2">Показатель 3</th>
        <th colspan="2">Показатель 4</th>
        <th colspan="2">Показатель 5</th>
    </tr>
    <tr>
        <td>самооценка</td>
        <td>оценка руководителя</td>
        <td>самооценка</td>
        <td>оценка руководителя</td>
        <td>самооценка</td>
        <td>оценка руководителя</td>
        <td>самооценка</td>
        <td>оценка руководителя</td>
        <td>самооценка</td>
        <td>оценка руководителя</td>
    </tr>
    <c:forEach var="system5" items="${system5List}">
    <tr>
        <td>${system5.month}</td>
        <td>${system5.res1}</td>
        <td></td>
        <td>${system5.res2}</td>
        <td></td>
        <td>${system5.res3}</td>
        <td></td>
        <td>${system5.res4}</td>
        <td></td>
        <td>${system5.res5}</td>
        <td></td>
    </tr>
    </c:forEach>
    </tbody>
</table>

<div id="openModal" class="modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title">Самооценка</h3>
                <a href="#close" title="Close" class="close">×</a>
            </div>
            <div class="modal-body">
                <form:form method="post" action="/adds" modelAttribute="system5">
                    <form:input type="hidden" path="userId"/>
                    <form:input type="hidden" path="month"/>
                    <label>Показатель 1 <form:input path="res1"/></label>
                    <br/>
                    <label>Показатель 2 <form:input path="res2"/></label>
                    <br/>
                    <label>Показатель 3 <form:input path="res3"/></label>
                    <br/>
                    <label>Показатель 4 <form:input path="res4"/></label>
                    <br/>
                    <label>Показатель 5 <form:input path="res5"/></label>
                    <button type="submit">Отправить</button>
                </form:form>
            </div>
        </div>
    </div>
</div>

<!-- openModal - id модального окна (элемента div) -->
<a href="#openModal">Добавить самооценку</a>
</body>
</html>
<style>
    table {
        width: 100%;
        table-layout: fixed;
    }
    table td {
        width: 100%;
    }
    table {
        text-align: center;
        font-family: Arial, Helvetica, sans-serif;
        border-collapse: collapse;
        width: 100%;
    }

    table td, table th {
        text-align: center;
        border: 1px solid #ddd;
        padding: 8px;
    }

    table tr:nth-child(even){
        text-align: center;
        background-color: #f2f2f2;
    }


    table th {
        text-align: center;
        padding-top: 12px;
        padding-bottom: 12px;
        background-color: #1fb5bf;
        color: white;
    }


    /* стилизация содержимого страницы */
    body {
        font-family: -apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif;
        font-size: 16px;
        font-weight: 400;
        line-height: 1.5;
        color: #292b2c;
        background-color: #fff;
    }

    /* свойства модального окна по умолчанию */
    .modal {
        position: fixed; /* фиксированное положение */
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
        background: rgba(0,0,0,0.5); /* цвет фона */
        z-index: 1050;
        opacity: 0; /* по умолчанию модальное окно прозрачно */
        -webkit-transition: opacity 200ms ease-in;
        -moz-transition: opacity 200ms ease-in;
        transition: opacity 200ms ease-in; /* анимация перехода */
        pointer-events: none; /* элемент невидим для событий мыши */
        margin: 0;
        padding: 0;
    }
    /* при отображении модального окно */
    .modal:target {
        opacity: 1; /* делаем окно видимым */
        pointer-events: auto; /* элемент видим для событий мыши */
        overflow-y: auto; /* добавляем прокрутку по y, когда элемент не помещается на страницу */
    }
    /* ширина модального окна и его отступы от экрана */
    .modal-dialog {
        position: relative;
        width: auto;
        margin: 10px;
    }
    @media (min-width: 576px) {
        .modal-dialog {
            max-width: 500px;
            margin: 30px auto; /* для отображения модального окна по центру */
        }
    }
    /* свойства для блока, содержащего контент модального окна */
    .modal-content {
        position: relative;
        display: -webkit-box;
        display: -webkit-flex;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-orient: vertical;
        -webkit-box-direction: normal;
        -webkit-flex-direction: column;
        -ms-flex-direction: column;
        flex-direction: column;
        background-color: #fff;
        -webkit-background-clip: padding-box;
        background-clip: padding-box;
        border: 1px solid rgba(0,0,0,.2);
        border-radius: .3rem;
        outline: 0;
    }
    @media (min-width: 768px) {
        .modal-content {
            -webkit-box-shadow: 0 5px 15px rgba(0,0,0,.5);
            box-shadow: 0 5px 15px rgba(0,0,0,.5);
        }
    }
    /* свойства для заголовка модального окна */
    .modal-header {
        display: -webkit-box;
        display: -webkit-flex;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-align: center;
        -webkit-align-items: center;
        -ms-flex-align: center;
        align-items: center;
        -webkit-box-pack: justify;
        -webkit-justify-content: space-between;
        -ms-flex-pack: justify;
        justify-content: space-between;
        padding: 15px;
        border-bottom: 1px solid #eceeef;
    }
    .modal-title {
        margin-top: 0;
        margin-bottom: 0;
        line-height: 1.5;
        font-size: 1.25rem;
        font-weight: 500;
    }
    /* свойства для кнопки "Закрыть" */
    .close {
        float: right;
        font-family: sans-serif;
        font-size: 24px;
        font-weight: 700;
        line-height: 1;
        color: #000;
        text-shadow: 0 1px 0 #fff;
        opacity: .5;
        text-decoration: none;
    }
    /* свойства для кнопки "Закрыть" при нахождении её в фокусе или наведении */
    .close:focus, .close:hover {
        color: #000;
        text-decoration: none;
        cursor: pointer;
        opacity: .75;
    }
    /* свойства для блока, содержащего основное содержимое окна */
    .modal-body {
        position: relative;
        -webkit-box-flex: 1;
        -webkit-flex: 1 1 auto;
        -ms-flex: 1 1 auto;
        flex: 1 1 auto;
        padding: 15px;
        overflow: auto;
    }


</style>

<script>
    document.addEventListener("DOMContentLoaded", function(){
        var scrollbar = document.body.clientWidth - window.innerWidth + 'px';
        console.log(scrollbar);
        document.querySelector('[href="#openModal"]').addEventListener('click',function(){
            document.body.style.overflow = 'hidden';
            document.querySelector('#openModal').style.marginLeft = scrollbar;
        });
        document.querySelector('[href="#close"]').addEventListener('click',function(){
            document.body.style.overflow = 'visible';
            document.querySelector('#openModal').style.marginLeft = '0px';
        });
    });


</script>