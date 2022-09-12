<%--
  Created by IntelliJ IDEA.
  User: aleksejlaskin
  Date: 05.06.2022
  Time: 13:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c"   %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"       prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql"       prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml"       prefix="x"   %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"  %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"  %>
<script type="text/javascript" src="../../../js/jquery-3.6.0.js"></script>
<html>
<style>
  <%@include file="../../includes/myStyle.css"%>
</style>
<head>
  <jsp:include page="../../includes/header.jsp"/>
  <jsp:include page="../../includes/menu.jsp"/>
  <title>Отчет</title>
</head>
<body>
<div class="main">
  <div id="printableArea" style="text-align: center">
    <p>Оценка работников полевого учреждения Банка России № 42667 за год</p>
    <table class="table">
      <tbody>
      <tr>
        <th style="width: 15%">Работник</th>
        <th style="width: 15%">Должность</th>
          <th><b>ЯНВАРЬ</b></th>
          <th><b>ФЕВРАЛЬ</b></th>
          <th><b>МАРТ</b></th>
          <th><b>АПРЕЛЬ</b></th>
          <th><b>МАЙ</b></th>
          <th><b>ИЮНЬ</b></th>
          <th><b>ИЮЛЬ</b></th>
          <th><b>АВГУСТ</b></th>
          <th><b>СЕНТЯБРЬ</b></th>
          <th><b>ОКТЯБРЬ</b></th>
          <th><b>НОЯБРЬ</b></th>
          <th><b>ДЕКАБРЬ</b></th>
        <th><b>ИТОГО</b></th>
      </tr>
      <c:forEach var="userDtoList" items="${modelMap}" varStatus="count">
        <tr>
          <td>${userDtoList.key.name}</td>
          <td>${userDtoList.key.position.position}</td>
          <c:forEach var="total" items="${userDtoList.value}">
            <td>${total}</td>
          </c:forEach>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>
  <div>
    <button type="button" class="btn" onclick="document.location='/list'">Назад</button>
    <input type="button" onclick="printDiv('printableArea')" value="Печать" class="btn"/>
  </div>
</div>

</body>
</html>
<script>
  function printDiv(divName) {
    var printContents = document.getElementById(divName).innerHTML;
    var originalContents = document.body.innerHTML;

    document.body.innerHTML = printContents;

    window.print();

    document.body.innerHTML = originalContents;
  }
</script>