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
  <input type="text" oninput="getReq(this.value)"/>
  <div id="ins">
</div>
</div>
</body>
</html>
<script>
  function getReq(value){
    if (value.length > 2){
      $.ajax({
        type: 'POST',
        url: '/searchPost',
        data: {"value": value},
        success: function (data) {
          // запустится при успешном выполнении запроса и в data будет ответ скрипта
          console.log(data);
          let el = document.getElementById("div")
          if (el != null){
            el.remove();
          }

          let div = document.createElement("ul");
          div.id = "div";
          for (let i = 0; i < data.length; i++) {
            let a = document.createElement("li");
            a.innerText = data[i].socr + " " + data[i].name + " " + data[i].code
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
  }
</script>

