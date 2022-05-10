<%--
  Created by IntelliJ IDEA.
  User: aleksejlaskin
  Date: 10.05.2022
  Time: 19:04
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

<table class="iksweb">
    <tbody>
        <c:forEach var="division" items="${divisionList}">
            <tr>
                <th colspan="3">${division.division}</th>
            </tr>
            <c:forEach var="position" items="${division.positions}">
                <tr>
                    <td>${position.position}</td>
                    <td>${position.user.name}</td>
                    <td>
                        <c:forEach items="${position.employersList}" var="empoloer">
                            <p>${empoloer.position} ${empoloer.user.name}</p>
                        </c:forEach>
                    </td>
                </tr>
            </c:forEach>
        </c:forEach>
    <tr><a href="">Добавить подразделение</a> </tr>
    </tbody>
</table>


</body>
<style>
    /* Стили таблицы (IKSWEB) */
    table.iksweb{
        text-decoration: none;
        border-collapse:collapse;
        width:100%;
        text-align:left;
        table-layout: fixed;
    }

    table.iksweb th{
        font-weight:normal;
        font-size:14px;
        color:#ffffff;
        background-color:#354251;
        width: 100%;
    }

    table.iksweb td{
        font-size:13px;
        color:#354251;
        width: 100%;
    }

    table.iksweb td,table.iksweb th{
        white-space:pre-wrap;
        padding:10px 5px;
        line-height:13px;
        vertical-align: middle;
        border: 1px solid #354251;
    }

    table.iksweb tr:hover{
        background-color:#f9fafb
    }

    table.iksweb tr:hover td{
        color:#354251;
        cursor:default;}
</style>
</html>
