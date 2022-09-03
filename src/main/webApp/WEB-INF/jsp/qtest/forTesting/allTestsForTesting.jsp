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
      <th>Выбрать тест</th>
      <th class="tblsht">Tесты группы: ${groupTest.name}</th>
      <th>Задать количество вопросов</th>
    </tr>
    <form id="searchTest" method="POST" action="/processing/start">
      <c:forEach var="test" items="${testList}">
        <tr>
          <td style="width: 10%;">
            <input type="checkbox" class="chektest" name="testIds" value="${test.testId}"/>
          </td>
          <td class="tblsht">
            <a>${test.testName}</a>
          </td>
          <td style="width: 20%;">
            <input id="ques${test.testId}" type="text"
                   class="myinput" style="margin-top: 0; padding: 0" value="${test.quesAmount}"
                   name="quesAmounts" disabled onchange="check(${test.quesAmount}, this.value, this.id)"/>
          </td>
        </tr>
      </c:forEach>
    </form>
    <tr>
      <td colspan="3" style="text-align: left">
        <a>Выбрано <span id="totalQuesAmount">0</span> вопросов</a>
      </td>
    </tr>
    <tr>
      <td colspan="3" style="text-align: left">
        <input form="searchTest" type="number" class="myinput" name="criteria" style="margin-top: 0; padding: 0" placeholder="Установите критерий в процентах"/>
      </td>
    </tr>
    <tr id="testNameTr" style="display: none">
      <td colspan="3" style="text-align: left">
        <textarea form="searchTest" class="myinput" name="testName" style="margin-top: 0; padding: 0; font-family: inherit" placeholder="Введите название сводного теста"></textarea>
      </td>
    </tr>
  </table>
  <button form="searchTest" class="btn" type="submit">Начать тест</button>
</div>
</body>
<script>
  const elems = document.body.querySelectorAll('.chektest');

  for (let x = 0; x < elems.length; x++) {
    elems[x].addEventListener("click", listener)
  }

  function check(quesAmont, value, id){
    if (value > quesAmont){
      alert("Превышено допустимое значение количества вопросов для теста: " + quesAmont);
      document.getElementById(id).value = quesAmont;
      return;
    }
    listener();
  }

  function listener(){
    let total = 0;
    let count = 0;
    for (let i = 0; i < elems.length; i++) {
      let elem = document.getElementById("ques" + elems[i].value);
      if (elems[i].checked){
        count++;
        elem.disabled = false;
        let quesAmount = Number(elem.value);
        total = total + quesAmount;
      }
      else{
        elem.disabled = true;
      }
    }
    if(count > 1){
      $('#testNameTr').show();
    }
    else {
      $('#testNameTr').hide();
    }
    document.getElementById("totalQuesAmount").innerText = total;
  }



</script>
</html>
