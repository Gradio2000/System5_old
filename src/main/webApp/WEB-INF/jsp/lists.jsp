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
        <th colspan="2">Личная результативность</th>
        <th colspan="2">Инициативность</th>
        <th colspan="2">Совершенствование профессиональных знаний</th>
        <th colspan="2">Клиентоориентированность</th>
        <th colspan="2">Работа в команде</th>
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
        <td>${system5.system5empl.resempl1}</td>
        <td>${system5.res2}</td>
        <td>${system5.system5empl.resempl2}</td>
        <td>${system5.res3}</td>
        <td>${system5.system5empl.resempl3}</td>
        <td>${system5.res4}</td>
        <td>${system5.system5empl.resempl4}</td>
        <td>${system5.res5}</td>
        <td>${system5.system5empl.resempl5}</td>
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
                            <label>Выберите месяц</label>
                            <br/>
                            <form:select path="month" items="${monthList}"/>
                            <br/>
                            <label>Личная результативность <form:input path="res1"/></label>
                            <form:errors path="res1" cssClass="errorMsg"/>
                            <br/>
                            <label>Инициативность <form:input path="res2"/></label>
                            <br/>
                            <label>Совершенствование профессиональных знаний <form:input path="res3"/></label>
                            <br/>
                            <label>Клиентоориентированность <form:input path="res4"/></label>
                            <br/>
                            <label>Работа в команде <form:input path="res5"/></label>
                            <br/>
                            <br/>
                            <label>Выберите руководителя, которому отправите самооценку</label>
                            <br/>
                            <select name="comm_id">
                                <c:forEach items="${userList}" var="user">
                                    <option value="${user.position.position_id}">${user.name}</option>
                                </c:forEach>
                            </select>
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

                    <h3 class="modal-title">Оценка</h3>


                    <a href="#close" title="Close" class="close">×</a>
                </div>
                    <div class="modal-body">
                        <form:form method="post" action="/addsempl" modelAttribute="system5empl">
                            <form:input path="user_id" type="hidden" value="${userId}"/>
                            <label>Выберите месяц</label>
                            <br/>
                            <form:select path="system5Id">
                                <c:forEach items="${months}" var="month">
                                    <form:option value="${month.key}">${month.value}</form:option>
                                </c:forEach>
                            </form:select>
                            <br/>
                            <label>Личная результативность <form:input path="resempl1"/></label>
                            <br/>
                            <label>Инициативность <form:input path="resempl2"/></label>
                            <br/>
                            <label>Совершенствование профессиональных знаний <form:input path="resempl3"/></label>
                            <br/>
                            <label>Клиентоориентированность <form:input path="resempl4"/></label>
                            <br/>
                            <label>Работа в команде <form:input path="resempl5"/></label>
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
<c:if test="${months.size() != 0}">
    <c:if test="${employer == true}">
        <button type="button" class="btn" onclick="document.location='#openModal'">Добавить оценку</button>
    </c:if>
</c:if>


<button type="button" class="btn" onclick="document.location='/home'">Назад</button>
<br/>
<label style="color: crimson; font: bold italic 110% serif">
    <c:if test="${param.get('error') == 1}">Ошибка при заполнении полей самооценки. Попробуйте еще раз!</c:if>
</label>
<label style="color: crimson; font: bold italic 110% serif">
    <c:if test="${param.get('error') == 2}">Ошибка при заполнении полей оценки. Попробуйте еще раз!</c:if>
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