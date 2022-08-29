<%--
  Created by IntelliJ IDEA.
  User: aleksejlaskin
  Date: 21.06.2022
  Time: 23:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c"   %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"       prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql"       prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml"       prefix="x"   %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"  %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"  %>
<script type="text/javascript" src="../../../../js/jquery-3.6.0.js"></script>
<html>

<style>
  <%@include file="../../../includes/myStyle.css"%>

</style>
<head>
  <jsp:include page="../../../includes/header.jsp"/>
  <jsp:include page="../../../includes/menu.jsp"/>

  <title>Список тестов</title>
</head>
<body>
<div class="main">
  <table id="color_table" style="width: 100%; table-layout: auto">
    <tr>
      <th class="tblsht">Tесты группы: ${groupTest.name}</th>
      <th>Выбрать одиночный тест</th>
      <th>Собрать сводный тест</th>
    </tr>
    <form id="searchTest" method="POST" action="test">
      <c:forEach var="test" items="${testList}">
        <tr>
          <td class="tblsht">
            <a>${test.testName}</a>
          </td>
          <td>
            <input type="radio" class="radioTest" value="${test.testId}" name="id"/>
          </td>
          <td>
            <input type="checkbox" class="chektest" name="testIds" value="${test.testId}"/>
          </td>
        </tr>
      </c:forEach>
    </form>
  </table>
  <button form="searchTest" class="btn" type="submit">ОК</button>
</div>
</body>
<script>
  const elems = document.body.querySelectorAll('.chektest');

  for (let x = 0; x < elems.length; x++) {
    elems[x].addEventListener("click", listener)
  }

  function listener(){
    const element = document.getElementsByClassName("radioTest");
    for (let x = 0; x < elems.length; x++) {
      if (elems[x].checked){
        for (let i = 0; i < element.length; i++) {
          element[i].disabled = true;
        }
        return;
      }
    }
    for (let i = 0; i < element.length; i++) {
      element[i].disabled = false;
    }
  }

</script>
</html>
