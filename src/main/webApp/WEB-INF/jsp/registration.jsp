<%--
  Created by IntelliJ IDEA.
  User: aleksejlaskin
  Date: 23.04.2022
  Time: 23:13
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
    <title>Регистрация</title>
</head>
<body>
    <div class="log-form">
        <h2>Регистрация</h2>
        <form:form method="post" action="/adduser" modelAttribute="myForm">
            <form:input path="login" type="text" title="login" placeholder="логин" class="field email"/>
            <form:errors path="login" cssClass="errorMsg"/>
            <form:input path="password" type="password" title="password" placeholder="пароль" class="field password"/>
            <form:errors path="password" cssClass="errorMsg"/>
            <form:input path="confpass" type="password" title="password" placeholder="подтвердите пароль" class="field confpass"/>
            <form:errors path="confpass" cssClass="errorMsg"/>
            <br/>
            <button type="submit" class="btn" id="but">Регистрация</button>
            <button type="button" class="btn" id="but" onclick="document.location='/logout'">Отмена</button>
        </form:form>
    </div>
</body>
<style>
    <%@include file="../includes/myStyle.css"%>
</style>
</html>
