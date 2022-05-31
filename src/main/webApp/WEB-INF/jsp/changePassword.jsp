<%--
  Created by IntelliJ IDEA.
  User: aleksejlaskin
  Date: 31.05.2022
  Time: 21:24
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
    <jsp:include page="../includes/header.jsp"/>
    <jsp:include page="../includes/menu.jsp"/>
    <title>Изменить пароль</title>
</head>
<body>
<div class="main">
    <div class="my-box">
        <form:form action="changePassword" method="post" modelAttribute="changePasswordForm">
            <h5 style="width: 300px">Новый пароль</h5>
            <form:input path="password" type="password" title="password" placeholder="новый пароль" class="field password"/>
            <form:errors path="password" cssClass="errorMsg"/>
            <h5 style="width: 300px">Подтвердите пароль</h5>
            <form:input path="confirmPassword" type="password" title="password" placeholder="подтвердите пароль" class="field confpass"/>
            <form:errors path="confirmPassword" cssClass="errorMsg"/>

        <div>
            <button type="submit" class="btn" hidden="hidden">Сохранить</button>
            <button type="button" onclick="document.location = '/list'" class="btn" hidden>Назад</button>
        </div>
    </form:form>
    </div>
</div>
</body>
</html>
