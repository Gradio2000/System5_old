<%--
  Created by IntelliJ IDEA.
  User: aleksejlaskin
  Date: 23.04.2022
  Time: 23:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Регистрация</title>
</head>
<body>
    <div class="log-form">
        <h2>Регистрация - шаг 2</h2>
        <form:form method="post" action="/finishreg" modelAttribute="formFinishReg">
            <form:input type="text" title="name" placeholder="Фамилия И.О." path="name" class="field name"/>
            <form:errors path="name" cssClass="errorMsg" />
            <br/>
            <select id="select" name="position_id" style="margin-top: 2em">
                <option value="" disabled selected>Выберите должность</option>
                <c:forEach var="position" items="${positionList}">
                    <option value="${position.position_id}">${position.position}</option>
                </c:forEach>
            </select>
            <form:errors path="position_id" cssClass="errorMsg"/>
            <br/>
            <button type="submit" class="btn" id="but">Регистрация</button>
            <button type="button" class="btn" id="but" onclick="document.location='/cancel'">Отмена</button>
        </form:form>
    </div>
</body>
<style>
    <%@include file="../includes/myStyle.css"%>
</style>
</html>
