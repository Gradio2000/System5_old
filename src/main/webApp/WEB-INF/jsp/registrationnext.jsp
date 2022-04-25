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
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"  %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
    <div class="log-form">
        <h2>Registration</h2>
        <form id="myForm" class="myForm" name="myForm" method="post" action="/finishreg">
            <input type="text" title="name" placeholder="name" name="name" class="field name"/>
            <sf:errors path="name" cssClass="error" element="div"/>

            <select id="select" name="position_id">
                <option value="" disabled selected>Выберите должность</option>
                <c:forEach var="position" items="${positionList}">
                    <option value="${position.position_id}">${position.position}</option>
                </c:forEach>
            </select>
            <button type="submit" class="btn" id="but">Registration</button>
        </form>
    </div>
</body>
<style>
    <%@include file="myStyle.css"%>
</style>
</html>
