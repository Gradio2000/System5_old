<%--
  Created by IntelliJ IDEA.
  User: aleksejlaskin
  Date: 04.10.2022
  Time: 23:14
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

  <title>Поиск</title>
</head>
<body>
<div class="main">
  <input id="inp" type="text" oninput="getReq(this.value)" style="width: 400px"/>
  <div id="adressDiv"></div>
  <input id="inpStr" type="text" oninput="findStreet(this.value)" placeholder="улица" style="width: 400px; display: none"/>
  <div id="ins"></div>
  <div id="indexDiv"></div>
</div>
</body>
</html>
<script>

  let regCodeId;
  let areaCodeId;
  let cityCodeId;
  let punktCodeId;

  function getReq(value){
    let el = document.getElementById("adressElement");
    if (el != null){
      el.remove();
    }
    let elem = document.getElementById("indexElement");
    if (elem != null){
      elem.remove();
    }
    if (value.length > 2){
      $.ajax({
        type: 'POST',
        url: '/searchPost',
        data: {"value": value},
        success: function (data) {
          // запустится при успешном выполнении запроса и в data будет ответ скрипта
          let el = document.getElementById("div")
          if (el != null){
            el.remove();
          }

          let div = document.createElement("ul");
          div.id = "div";
          for (let i = 0; i < data.length; i++) {
            let a = document.createElement("li");
            a.innerText = data[i].name
            a.className = "punkt"
            a.id = data[i].regCode.toString() + data[i].areaCode + data[i].cityCode + data[i].punktCode;
            a.setAttribute("onclick", "punktChoose(this.id)");
            a.setAttribute("regCode", data[i].regCode);
            a.setAttribute("areaCode", data[i].areaCode);
            a.setAttribute("cityCode", data[i].cityCode);
            a.setAttribute("punktCode", data[i].punktCode);
            a.style = "list-style-type: none"
            div.append(a);
          }
          document.getElementById("ins").append(div);
        },
        error: function () {
          alert('Ошибка!');
        }
      });
    }
  }

  function punktChoose(id){
    $('#inp').hide();
    $('#inpStr').show();

    let el = document.getElementById(id);
    let regCode = el.getAttribute("regCode");
    let areaCode = el.getAttribute("areaCode");
    let cityCode = el.getAttribute("cityCode");
    let punktCode = el.getAttribute("punktCode");

    let adressElement = document.createElement("a");
    adressElement.className = "punkt";
    adressElement.innerText = el.innerText;
    adressElement.id = "adressElement";
    adressElement.setAttribute("onclick", "setInputVisible()");
    document.getElementById("adressDiv").append(adressElement);

    regCodeId = el.getAttribute("regCode");
    areaCodeId = el.getAttribute("areaCode");
    cityCodeId = el.getAttribute("cityCode");
    punktCodeId = el.getAttribute("punktCode");

    $.ajax({
      type: 'POST',
      url: '/searchStreet',
      data: {"regCode": regCode, "areaCode": areaCode, "cityCode": cityCode, "punktCode": punktCode},
      success: function (data) {
        // запустится при успешном выполнении запроса и в data будет ответ скрипта
        let el = document.getElementById("div");
        if (el != null){
          el.remove();
        }

        let div = document.createElement("ul");
        div.id = "div";

        for (let i = 0; i < data.length; i++) {
          let a = document.createElement("li");
          a.id = data[i].id;
          a.innerText = data[i].name + " " + data[i].socr;
          a.className = "punkt";
          a.setAttribute("index", data[i].index);
          a.setAttribute("onclick", "getIndex(this.id)");
          a.style = "list-style-type: none"
          div.append(a);
        }
        document.getElementById("ins").append(div);
      },
      error: function () {
        alert('Ошибка!');
      }
    });
  }

  function getIndex(id){
    let elem = document.getElementById("indexElement");
    if (elem != null){
      elem.remove();
    }
    let streetElement = document.getElementById(id);
    let index = streetElement.getAttribute("index");
    let indexElement = document.createElement("a");
    indexElement.id = "indexElement";
    indexElement.innerText = "Индекс: " + index;
    document.getElementById("indexDiv").append(indexElement);
  }

  function setInputVisible(){
    $('#adressElement').hide();
    $('#inp').show();
    $('#inpStr').hide();
    $('#div').remove();
    $('#indexElement').remove();
  }

  function findStreet(value){
    $.ajax({
      type: 'POST',
      url: '/findStreet',
      data: ({
        "regCodeId": regCodeId,
        "areaCodeId": areaCodeId,
        "cityCodeId": cityCodeId,
        "punktCodeId":punktCodeId,
        "value": value}),
      success: function (data) {
        // запустится при успешном выполнении запроса и в data будет ответ скрипта
        console.log(data);
        let el = document.getElementById("div");
        if (el != null){
          el.remove();
        }

        let div = document.createElement("ul");
        div.id = "div";

        for (let i = 0; i < data.length; i++) {
          let a = document.createElement("li");
          a.id = data[i].id;
          a.innerText = data[i].name + " " + data[i].socr;
          a.className = "punkt";
          a.setAttribute("index", data[i].index);
          a.setAttribute("onclick", "getIndex(this.id)");
          a.style = "list-style-type: none"
          div.append(a);
        }
        document.getElementById("ins").append(div);
      },
      error: function () {
        alert('Ошибка!');
        console.log(msg);
      }
    });

  }
</script>

