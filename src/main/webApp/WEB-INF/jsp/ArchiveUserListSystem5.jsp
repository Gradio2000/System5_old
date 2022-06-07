<%--
  Created by IntelliJ IDEA.
  User: aleksejlaskin
  Date: 06.06.2022
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
<script type="text/javascript" src="../../js/jquery-3.6.0.js"></script>
<html>
<style>
    <%@include file="../includes/myStyle.css"%>
</style>
<head>
    <title>Архив оценок</title>
</head>

<body class="bod">
<jsp:include page="../includes/header.jsp"/>
<jsp:include page="../includes/menu.jsp"/>

<div class="main">

    <table class="table">
        <tbody>
        <tr>
            <th rowspan="2">Месяц</th>
            <th colspan="2">Личная результативность</th>
            <th colspan="2">Инициативность</th>
            <th colspan="2">Совершенствование профессиональных знаний</th>
            <th colspan="2">Клиентоориентированность</th>
            <th colspan="2">Работа в команде</th>
            <th colspan="2">Итог</th>
        </tr>
        <tr>
            <th>самооценка</th>
            <th>оценка руководителя</th>
            <th>самооценка</th>
            <th>оценка руководителя</th>
            <th>самооценка</th>
            <th>оценка руководителя</th>
            <th>самооценка</th>
            <th>оценка руководителя</th>
            <th>самооценка</th>
            <th>оценка руководителя</th>
            <th>самооценка</th>
            <th>оценка руководителя</th>
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
                <td>${system5.totalMark5.totalMark}</td>
                <td>${system5.totalMark5.totalMarkEmpl}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <%--    модальное окно для самооценки--%>

        <div id="openModal" class="modal">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h3 class="modal-title">Изменение самооценки</h3>
                        <a href="#close" title="Close" class="close">×</a>
                    </div>
                    <div class="modal-body my-modal">
                        <form:form method="post" action="/admin/addFromAdminModule" modelAttribute="system5">
                            <label>Выберите месяц</label>
                            <form:input path="userId" value="${userId}" type="hidden"/>
                            <form:select cssClass="select-css" path="month" items="${monthList}"/>
                            <form:input path="res1" placeholder="Личная результативность"/>
                            <form:errors path="res1" cssClass="errorMsg"/>
                            <form:input path="res2" placeholder="Инициативность"/>
                            <form:input path="res3" placeholder="Совершенствование профессиональных знаний"/>
                            <form:input path="res4" placeholder="Клиентоориентированность"/>
                            <form:input path="res5" placeholder="Работа в команде"/>
                            <br/>
                            <br/>
                            <button type="submit" class="btn">Отправить</button>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>



    <%--    модальное окно для изменения оценки--%>
    <div id="openModalEdit" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title">Изменение оценки</h3>
                    <a href="#close" title="Close" class="close">×</a>
                </div>
                <div class="modal-body my-modal">
                    <form:form method="post" action="/admin/editSystem5EmplFromAdminModule" modelAttribute="system5empl">
                        <form:input path="user_id" type="hidden" value="${userId}"/>
                        <label>Выберите месяц</label>

                        <form:select cssClass="select-css" path="system5Id">
                            <c:forEach items="${monthListForEditSystem5Empl}" var="month">
                                <form:option value="${month.key}">${month.value}</form:option>
                            </c:forEach>
                        </form:select>
                        <br/>

                        <form:input path="resempl1" placeholder="Личная результативность"/>
                        <form:input path="resempl2" placeholder="Инициативность"/>
                        <form:input path="resempl3" placeholder="Совершенствование профессиональных знаний"/>
                        <form:input path="resempl4" placeholder="Клиентоориентированность"/>
                        <form:input path="resempl5" placeholder="Работа в команде"/>
                        <button type="submit" class="btn">Отправить</button>
                    </form:form>
                </div>
            </div>
        </div>
    </div>

    <!-- openModal - id модального окна (элемента div) -->
    <button type="button" class="btn" onclick=document.location='#openModal'>Добавить/изменить самооценку</button>
    <button type="button" class="btn" onclick="document.location='#openModalEdit'">Добавить/изменить оценку</button>
    <button type="button" class="btn" onclick="document.location='/admin/archive'">Назад</button>
    <br/>


    <%--    кнопка изменить--%>
    <c:if test="${employer == false}">
        <c:set var="let" value="0"/>
        <c:forEach var="system5" items="${system5List}">
            <c:if test="${system5.rated == 0}">
                <c:set var="let" value="1"/>
            </c:if>
        </c:forEach>
        <c:if test="${let != 0}">
            <button type="button" class="buttonch" onclick="editSelfRated()">Изменить</button>
        </c:if>
    </c:if>
    <%--    кнопка изменить--%>


    <label style="color: crimson; font: bold italic 110% serif">
        <c:if test="${param.get('error') == 1}">Ошибка при заполнении полей самооценки.
            Принимаются только латинские буквы A-E a-e.
            Попробуйте еще раз!</c:if>
    </label>
    <label style="color: crimson; font: bold italic 110% serif">
        <c:if test="${param.get('error') == 2}">Ошибка при заполнении полей оценки.
            Принимаются только латинские буквы A-E a-e. Попробуйте еще раз!</c:if>
    </label>
</div>

</body>
</html>

<script>


    function editSelfRated(){
        $.ajax({
            type: 'GET',
            url: '/getMonths',
            success: function (data) {
                let elem = document.getElementById('selectMonthForEdit');
                for (let i = 0; i < data.length; i++) {
                    let opt = document.createElement('option');
                    opt.innerText = data[i];
                    opt.setAttribute("value", data[i]);
                    elem.append(opt);
                }
            },
            error: function () {
                alert('Ошибка получение данных с сервера!');
            }
        });
        document.location='#openModalEdit';
    }

</script>
