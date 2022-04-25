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
    <title>Registration</title>
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
        </form:form>
    </div>
</body>
<style>
    .errorMsg {
        /*position: absolute;*/
        /*left: 0;*/
        /*top: 40px;*/
        /*width: 200px;*/
        color: red;
        font-size: 12px;
        font-style: italic;
    }

    @font-face {
        font-family: 'Open Sans';
        font-style: normal;
        font-weight: 300;
        font-stretch: normal;
        src: url(https://fonts.gstatic.com/s/opensans/v27/memSYaGs126MiZpBA-UvWbX2vVnXBbObj2OVZyOOSr4dVJWUgsiH0B4gaVc.ttf) format('truetype');
    }
    @font-face {
        font-family: 'Open Sans';
        font-style: normal;
        font-weight: 400;
        font-stretch: normal;
        src: url(https://fonts.gstatic.com/s/opensans/v27/memSYaGs126MiZpBA-UvWbX2vVnXBbObj2OVZyOOSr4dVJWUgsjZ0B4gaVc.ttf) format('truetype');
    }
    @font-face {
        font-family: 'Open Sans';
        font-style: normal;
        font-weight: 600;
        font-stretch: normal;
        src: url(https://fonts.gstatic.com/s/opensans/v27/memSYaGs126MiZpBA-UvWbX2vVnXBbObj2OVZyOOSr4dVJWUgsgH1x4gaVc.ttf) format('truetype');
    }
    @font-face {
        font-family: 'Open Sans Condensed';
        font-style: normal;
        font-weight: 300;
        src: url(https://fonts.gstatic.com/s/opensanscondensed/v15/z7NFdQDnbTkabZAIOl9il_O6KJj73e7Ff1GhDuXMQg.ttf) format('truetype');
    }
    @font-face {
        font-family: 'Open Sans Condensed';
        font-style: normal;
        font-weight: 700;
        src: url(https://fonts.gstatic.com/s/opensanscondensed/v15/z7NFdQDnbTkabZAIOl9il_O6KJj73e7Ff0GmDuXMQg.ttf) format('truetype');
    }
    * {
        box-sizing: border-box;
    }
    body {
        font-family: 'open sans', helvetica, arial, sans;
        background: url(http://farm8.staticflickr.com/7064/6858179818_5d652f531c_h.jpg) no-repeat center center fixed;
        -webkit-background-size: cover;
        -moz-background-size: cover;
        -o-background-size: cover;
        background-size: cover;
    }
    .log-form {
        width: 40%;
        min-width: 320px;
        max-width: 475px;
        background: #fff;
        position: absolute;
        top: 50%;
        left: 50%;
        -webkit-transform: translate(-50%, -50%);
        -moz-transform: translate(-50%, -50%);
        -o-transform: translate(-50%, -50%);
        -ms-transform: translate(-50%, -50%);
        transform: translate(-50%, -50%);
        box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.25);
    }
    @media (max-width: 40em) {
        .log-form {
            width: 95%;
            position: relative;
            margin: 2.5% auto 0 auto;
            left: 0%;
            -webkit-transform: translate(0%, 0%);
            -moz-transform: translate(0%, 0%);
            -o-transform: translate(0%, 0%);
            -ms-transform: translate(0%, 0%);
            transform: translate(0%, 0%);
        }
    }
    .log-form form {
        display: block;
        width: 100%;
        padding: 2em;
    }
    .log-form h2 {
        color: #5d5d5d;
        font-family: 'open sans condensed';
        font-size: 1.35em;
        display: block;
        background: #2a2a2a;
        width: 100%;
        text-transform: uppercase;
        padding: 0.75em 1em 0.75em 1.5em;
        box-shadow: inset 0px 1px 1px rgba(255, 255, 255, 0.05);
        border: 1px solid #1d1d1d;
        margin: 0;
        font-weight: 200;
    }
    .log-form input {
        display: block;
        /*margin: auto auto;*/
        width: 100%;
        /*margin-bottom: 2em;*/
        margin-top: 2em;
        padding: 0.5em 0;
        border: none;
        border-bottom: 1px solid #eaeaea;
        padding-bottom: 1.25em;
        color: #757575;
    }
    .log-form input:focus {
        outline: none;
    }
    .log-form .btn {
        display: inline-block;
        margin-top: 2em;
        background: #1fb5bf;
        border: 1px solid #1ba0a9;
        padding: 0.5em 2em;
        color: white;
        margin-right: 0.5em;
        box-shadow: inset 0px 1px 0px rgba(255, 255, 255, 0.2);
    }
    .log-form .btn:hover {
        background: #23cad5;
    }
    .log-form .btn:active {
        background: #1fb5bf;
        box-shadow: inset 0px 1px 1px rgba(0, 0, 0, 0.1);
    }
    .log-form .btn:focus {
        outline: none;
    }
    .log-form .forgot {
        color: #33d3de;
        line-height: 0.5em;
        position: relative;
        top: 2.5em;
        text-decoration: none;
        font-size: 0.75em;
        margin: 0;
        padding: 0;
        float: right;
    }
    .log-form .forgot:hover {
        color: #1ba0a9;
    }
</style>
</html>
