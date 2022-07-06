<%--
  Created by IntelliJ IDEA.
  User: aleksejlaskin
  Date: 23.04.2022
  Time: 23:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en" >
<head>
    <meta charset="UTF-8">
    <title>ЛенИнформ</title>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"  %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

</head>
<body>
<div class="log-form">
    <h2>Вход в систему</h2>
    <form action="/perform_login" method="post">
        <label style="color: crimson"><c:if test="${param.get('error')}">Ошибка логина и пароля</c:if></label>
        <input type="text" title="Логин" placeholder="логин" name="login"/>
        <input type="password" title="Пароль" placeholder="пароль" name="password" />
        <button type="submit" class="btn">Войти</button>
        <a class="forgot" href="/registration">Регистрация</a>
        <br/>
        <a class="rememberPas" onclick="rememberPassword()">Вспомнить пароль</a>
    </form>
</div>
</body>
<style>
    <%@include file="../includes/myStyle.css"%>
</style>
<script>
    function rememberPassword(){
        alert("Функция восстановления пароля не реализована. Обратитесь к администратору!");
    }
</script>
</html>
