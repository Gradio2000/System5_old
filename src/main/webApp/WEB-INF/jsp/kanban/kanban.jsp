<%--
  Created by IntelliJ IDEA.
  User: aleksejlaskin
  Date: 14.08.2022
  Time: 20:46
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

  <title>Канбан</title>
</head>
<body>
<div class="main">
  <table id="color_table">
    <tbody>
    <tr>
        <th>Сделать</th>
        <th>В процессе</th>
        <th>Сделано</th>
    </tr>
    <tr>
      <td class="container">
          <section class="tasks">
            <ul class="tasks__list">
              <c:forEach var="task" items="${kanbanList}">
                <li class="tasks__item">${task.kanbanName}</li>
              </c:forEach>
            </ul>
          </section>
      </td>
      <td class="container"></td>
      <td class="container"></td>
    </tr>
    </tbody>
  </table>
</div>
</body>
<script>
  const tasksListElement = document.querySelector(`.tasks__list`);
  const taskElements = tasksListElement.querySelectorAll(`.tasks__item`);

  for (const task of taskElements) {
    task.draggable = true;
  }

  tasksListElement.addEventListener(`dragstart`, (evt) => {
    evt.target.classList.add(`selected`);
  });

  tasksListElement.addEventListener(`dragend`, (evt) => {
    evt.target.classList.remove(`selected`);
  });

  const getNextElement = (cursorPosition, currentElement) => {
    const currentElementCoord = currentElement.getBoundingClientRect();
    const currentElementCenter = currentElementCoord.y + currentElementCoord.height / 2;

    const nextElement = (cursorPosition < currentElementCenter) ?
            currentElement :
            currentElement.nextElementSibling;

    return nextElement;
  };

  tasksListElement.addEventListener(`dragover`, (evt) => {
    evt.preventDefault();

    const activeElement = tasksListElement.querySelector(`.selected`);
    const currentElement = evt.target;
    const isMoveable = activeElement !== currentElement &&
            currentElement.classList.contains(`tasks__item`);

    if (!isMoveable) {
      return;
    }

    const nextElement = getNextElement(evt.clientY, currentElement);

    if (
            nextElement &&
            activeElement === nextElement.previousElementSibling ||
            activeElement === nextElement
    ) {
      return;
    }

    tasksListElement.insertBefore(activeElement, nextElement);
  });

</script>
<style>
  /*html, body {*/
  /*  margin: 0;*/
  /*  padding: 0;*/
  /*}*/

  /*body {*/
  /*  font-family: "Tahoma", sans-serif;*/
  /*  font-size: 18px;*/
  /*  line-height: 25px;*/
  /*  color: #164a44;*/

  /*  background-color: #1fb5bf;*/
  /*}*/

  .container {
    /*display: contents;*/
    /*justify-content: center;*/
  }

  .tasks__title {
    margin: 50px 0 20px 0;

    text-align: center;
    text-transform: uppercase;
  }

  .tasks__list {
    margin: 0;
    padding: 0;

    list-style: none;
  }

  .tasks__item {
    /*max-width: 250px;*/
    margin-bottom: 10px;
    padding: 5px;

    text-align: center;
    border: 2px dashed #b2d9d0;
    border-radius: 10px;
    cursor: move;
    background-color: #dff2ef;

    transition: background-color 0.5s;
  }

  .tasks__item:last-child {
    margin-bottom: 0;
  }

  .selected {
    opacity: 0.6;
  }

</style>
</html>
