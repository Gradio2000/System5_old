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
  <div style="margin-left: 100px; margin-right: 100px; position: static">
    <input id="inp" type="text" class="myinput" oninput="getReq(this.value)" placeholder="Начните вводить адрес..." />
    <input id="inpStr" type="text" class="myinput" oninput="findStreet(this.value)" placeholder="Улица..." style="display: none"/>
    <input id="inpHouse" type="text" class="myinput" oninput="findHouse(this.value)" placeholder="Дом..." style="display: none"/>
    <div id="ins"></div>
  </div>
  <div id="adressDiv" style="margin-left: 100px; margin-right: 100px; font-style: italic"></div>
  <div id="indexDiv" style="margin-top: 10px; margin-left: 100px; font-size: larger; font-weight: 500"></div>
</div>
</body>
</html>
<script>

  let regCodeId;
  let areaCodeId;
  let cityCodeId;
  let punktCodeId;
  let streetCodeId;

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
          alert('Ошибка! function getReq');
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
        getStreetList(data);
      },
      error: function () {
        alert('Ошибка! function punktChoose');
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
    if(index !== "      "){
      let indexElement = document.createElement("a");
      indexElement.id = "indexElement";
      indexElement.innerText = "Индекс: " + index;
      document.getElementById("indexDiv").append(indexElement);
    }
    else {
      streetCodeId = streetElement.getAttribute("streetCode");
      $.ajax({
        type: 'POST',
        url: '/searchHouse',
        data: {
          "regCodeId" : regCodeId,
          "areaCodeId" : areaCodeId,
          "cityCodeId" : cityCodeId,
          "punktCodeId" : punktCodeId,
          "streetCodeId" : streetCodeId
        },
        success: function (data) {
          // запустится при успешном выполнении запроса и в data будет ответ скрипта
          let adressElement = document.getElementById("adressElement")
          adressElement.innerText = adressElement.innerText + ", " + streetElement.innerText;
          document.getElementById("div").remove();
          $('#inpStr').hide();
          $('#inpHouse').show();
          getHouseList(data);
        },
        error: function () {
          alert('Ошибка! function getIndex');
        }
      });

    }

  }

  function setInputVisible(){
    $('#adressElement').hide().val('');
    let inp = $('#inp').show().val('');
    $('#inpStr').hide().val('');
    $('#inpHouse').hide().val('');
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
        getStreetList(data);
      },
      error: function () {
        alert('Ошибка! function findStreet');
      }
    });

  }

  function findHouse(value){
    $('#indexElement').remove();
    $.ajax({
      type: 'POST',
      url: '/findHouse',
      data: ({
        "regCodeId": regCodeId,
        "areaCodeId": areaCodeId,
        "cityCodeId": cityCodeId,
        "punktCodeId":punktCodeId,
        "streetCodeId": streetCodeId,
        "value": value}),
      success: function (data) {
        // запустится при успешном выполнении запроса и в data будет ответ скрипта
        getHouseList(data);
      },
      error: function () {
        alert('Ошибка! function findHouse');
      }
    });
  }


  function getStreetList(data){
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
      a.setAttribute("streetCode", data[i].streetCode);
      a.style = "list-style-type: none";
      div.append(a);
    }
    document.getElementById("ins").append(div);
  }

  function getHouseList(data){
    let el = document.getElementById("div");
    if (el != null){
      el.remove();
    }

    let div = document.createElement("ul");
    div.id = "div";

    for (let i = 0; i < data.length; i++) {
      let a = document.createElement("li");
      a.id = data[i].id;
      a.innerText = data[i].name;
      a.className = "punkt";
      a.setAttribute("index", data[i].index);
      a.setAttribute("onclick", "getIndex(this.id)");
      a.style = "list-style-type: none";
      div.append(a);
    }
    document.getElementById("ins").append(div);
  }
</script>

