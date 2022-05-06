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

<c:if test="${employer == false}">
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
                            <form:input type="hidden" path="resempl1"/>
                            <form:input type="hidden" path="resempl2"/>
                            <form:input type="hidden" path="resempl3"/>
                            <form:input type="hidden" path="resempl4"/>
                            <form:input type="hidden" path="resempl5"/>
                            <label>Выберите месяц</label>
                            <br/>
                            <form:select path="month" items="${monthList}"/>
                            <br/>
                            <label>Показатель 1 <form:input path="res1"/></label>
                            <form:errors path="res1" cssClass="errorMsg"/>
                            <br/>
                            <label>Показатель 2 <form:input path="res2"/></label>
                            <br/>
                            <label>Показатель 3 <form:input path="res3"/></label>
                            <br/>
                            <label>Показатель 4 <form:input path="res4"/></label>
                            <br/>
                            <label>Показатель 5 <form:input path="res5"/></label>
                            <br/>
                            <button type="submit" class="btn">Отправить</button>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:if>

<c:if test="${employer == true}">
    <div id="openModal" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">

                    <h3 class="modal-title">Самооценка</h3>


                    <a href="#close" title="Close" class="close">×</a>
                </div>
                    <div class="modal-body">
                        <form:form method="post" action="/addsempl" modelAttribute="system5">
                            <form:input type="hidden" path="userId" value="${userId}"/>
                            <form:input type="hidden" path="res1"/>
                            <form:input type="hidden" path="res2"/>
                            <form:input type="hidden" path="res3"/>
                            <form:input type="hidden" path="res4"/>
                            <form:input type="hidden" path="res5"/>
                            <label>Выберите месяц</label>
                            <br/>
                            <form:select path="month" items="${monthList}"/>
                            <br/>
                            <label>Показатель 1 <form:input path="resempl1"/></label>
                            <form:errors path="res1" cssClass="errorMsg"/>
                            <br/>
                            <label>Показатель 2 <form:input path="resempl2"/></label>
                            <br/>
                            <label>Показатель 3 <form:input path="resempl3"/></label>
                            <br/>
                            <label>Показатель 4 <form:input path="resempl4"/></label>
                            <br/>
                            <label>Показатель 5 <form:input path="resempl5"/></label>
                            <br/>
                            <button type="submit" class="btn">Отправить</button>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:if>

<!-- openModal - id модального окна (элемента div) -->
<c:if test="${employer == false}">
    <button type="button" class="btn" onclick="document.location='#openModal'">Добавить самооценку</button>
</c:if>
<c:if test="${employer == true}">
    <button type="button" class="btn" onclick="document.location='#openModal'">Добавить оценку</button>
</c:if>
<button type="button" class="btn" onclick="document.location='/home'">Назад</button>
<br/>
<label style="color: crimson; font: bold italic 110% serif">
    <c:if test="${param.get('error')}">Ошибка при заполнении полей самооценки. Попробуйте еще раз!</c:if>
</label>

</body>
</html>
<style>
    <%@include file="myStyle.css"%>
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