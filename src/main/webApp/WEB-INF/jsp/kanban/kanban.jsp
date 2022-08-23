<%--
  Created by IntelliJ IDEA.
  User: aleksejlaskin
  Date: 17.08.2022
  Time: 16:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c"   %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"       prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql"       prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml"       prefix="x"   %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"  %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"  %>
<script type="text/javascript" src="../../../js/jquery-3.6.0.js"></script>
<script type="text/javascript" src="../../../js/sortable.js"></script>
<html>
<style>
    <%@include file="../../includes/myStyle.css"%>

    .closex{
        position: absolute;
        z-index: 2;
        right: 10px;
        top: 1px;
        opacity: 0;
        color: #8f8f8f;
    }

    .closex:hover{
        cursor: pointer;
        color: black;
    }

    /*input[type=text], select, textarea {*/
    /*    width: 100%;*/
    /*    padding: 12px;*/
    /*    border: 1px solid #ccc;*/
    /*    border-radius: 4px;*/
    /*    box-sizing: border-box;*/
    /*    margin-top: 6px;*/
    /*    margin-bottom: 16px;*/
    /*    resize: vertical;*/
    /*}*/

    input[type=submit] {
        background-color: #4CAF50;
        color: white;
        padding: 12px 20px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }

    input[type=submit]:hover {
        background-color: #45a049;
    }

    .container-form {
        border-radius: 5px;
        background-color: #f2f2f2;
        padding: 20px;
    }


    .span-shadow {
        background: #1fb5bf
    }
    .span-shadow:hover {
        box-shadow: 0 0 0 2px #1fb5bf inset, 0 0 0 4px white inset;
    }


    .span-shadow-yellow {
        background: #faff5b
    }
    .span-shadow-yellow:hover {
        box-shadow: 0 0 0 2px #faff5b inset, 0 0 0 4px white inset;
    }

    .span-shadow-green {
        background: #76ff80
    }
    .span-shadow-green:hover {
        box-shadow: 0 0 0 2px #76ff80 inset, 0 0 0 4px white inset;
    }

    .container {
        font-family: "Trebuchet MS", sans-serif;
        font-size: medium;
        display: flex;
        gap: 10px;
        padding: 5px;
    }
    .column {
        flex-basis: 20%;
        background: #f1f1f1;
        min-height: 90vh;
        /*padding: 5px;*/
        border-radius: 10px;
    }
    .column h1 {
        text-align: center;
        font-size: 22px;
    }
    .list-group-item {
        z-index: 1;
        position: relative;
        background: #fff;
        margin: 5px;
        padding: 10px;
        border-radius: 5px;
        cursor: move;
    }

    .list-group-item:hover .closex{
        opacity: 1;
    }

    .started{
        background: #fff;
    }
    .continues{
        background: #faff5b;
    }
    .finished{
        background: #76ff80;
    }


    .collapsible {
        background-color: #939393;
        color: white;
        cursor: pointer;
        padding: 18px;
        width: 100%;
        border: none;
        text-align: left;
        outline: none;
        font-size: 15px;
        border-radius: 10px;
    }

    .active, .collapsible:hover {
        background-color: #737373;
    }

    .collapsible:after {
        content: '\002B';
        color: white;
        font-weight: bold;
        float: right;
        margin-left: 5px;
    }

    .active:after {
        content: "\2212";
    }

    .content {
        padding: 0 18px;
        max-height: 0;
        overflow: hidden;
        transition: max-height 0.2s ease-out;
        background-color: #f1f1f1;
    }
</style>
<head>
    <jsp:include page="../../includes/header.jsp"/>
    <jsp:include page="../../includes/menu.jsp"/>
    <title>Канбан</title>
</head>
<body>
<div class="main">
    <div class="container">
        <div id="col1" class="column">
            <h1>Надо сделать</h1>
            <c:forEach items="${kanbanList}" var="kanbanDto">
                <c:if test="${kanbanDto.started == true}">
                    <div id="${kanbanDto.id}" class="list-group-item started span-shadow" draggable="true" onclick="openModal(this.id)">
                        <div class="closex" onclick="deleteKanban(${kanbanDto.id})"><a>x</a></div>
                        <div><a>${kanbanDto.kanbanName}</a></div>
                        <div style="margin-top: 5px"><a style="font-size:10px;">Автор: ${kanbanDto.userDtoNameOnlyWithPositionDto.name}</a></div>
                        <div><a style="font-size:10px">срок: <fmt:formatDate value="${kanbanDto.taskEndDate}" pattern="dd.MM.yyyy"/></a></div>
                        <div>
                            <c:if test="${kanbanDto.userDtoNameOnlyList.size() > 1}">
                                <a style="font-size:10px">отв.: ${kanbanDto.userDtoNameOnlyList.get(0).name} и др.</a>
                            </c:if>
                            <c:if test="${kanbanDto.userDtoNameOnlyList.size() == 1}">
                                <a style="font-size:10px">отв.: ${kanbanDto.userDtoNameOnlyList.get(0).name}</a>
                            </c:if>
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </div>
        <div id="col2" class="column">
            <h1>Уже делаем</h1>
            <c:forEach items="${kanbanList}" var="kanbanDto">
                <c:if test="${kanbanDto.continues == true}">
                    <div id="${kanbanDto.id}" class="list-group-item continues span-shadow-yellow" draggable="true">
                        <div class="closex" onclick="deleteKanban(${kanbanDto.id})"><a>x</a></div>
                        <div><a>${kanbanDto.kanbanName}</a></div>
                        <div style="margin-top: 5px"><a style="font-size:10px">Автор: ${kanbanDto.userDtoNameOnlyWithPositionDto.name}</a></div>
                        <div><a style="font-size:10px">срок: <fmt:formatDate value="${kanbanDto.taskEndDate}" pattern="dd.MM.yyyy"/></a></div>
                        <c:if test="${kanbanDto.userDtoNameOnlyList.size() > 1}">
                            <a style="font-size:10px">отв.: ${kanbanDto.userDtoNameOnlyList.get(0).name} и др.</a>
                        </c:if>
                        <c:if test="${kanbanDto.userDtoNameOnlyList.size() == 1}">
                            <a style="font-size:10px">отв.: ${kanbanDto.userDtoNameOnlyList.get(0).name}</a>
                        </c:if>
                    </div>
                </c:if>
            </c:forEach>
        </div>
        <div id="col3" class="column">
            <h1>Мы сделали</h1>
            <c:forEach items="${kanbanList}" var="kanbanDto">
                <c:if test="${kanbanDto.finished == true}">
                    <div id="${kanbanDto.id}" class="list-group-item finished span-shadow-green" draggable="true">
                        <div class="closex" onclick="deleteKanban(${kanbanDto.id})"><a>x</a></div>
                        <div><a>${kanbanDto.kanbanName}</a></div>
                        <div style="margin-top: 5px"><a style="font-size:10px">Автор: ${kanbanDto.userDtoNameOnlyWithPositionDto.name}</a></div>
                        <div><a style="font-size:10px">срок: <fmt:formatDate value="${kanbanDto.taskEndDate}" pattern="dd.MM.yyyy"/></a></div>
                        <c:if test="${kanbanDto.userDtoNameOnlyList.size() > 1}">
                            <a style="font-size:10px">отв.: ${kanbanDto.userDtoNameOnlyList.get(0).name} и др.</a>
                        </c:if>
                        <c:if test="${kanbanDto.userDtoNameOnlyList.size() == 1}">
                            <a style="font-size:10px">отв.: ${kanbanDto.userDtoNameOnlyList.get(0).name}</a>
                        </c:if>
                    </div>
                </c:if>
            </c:forEach>
        </div>
        <div style="width: 40%">
            <button class="collapsible">Новая задача</button>
            <div class="content">
                <div class="container-form">
                    <form:form id="newCanbanForm" action="/kanban/addNewKanban" method="post" modelAttribute="kanban" >
                        <label class="mylabel-forkanban" for="kanbanName">Название</label>
                        <input class="myinput-forKanban" type="text" id="kanbanName" name="kanbanName" placeholder="Что делаем..">

                        <label class="mylabel-forkanban" for="describe">Описание</label>
                        <textarea class="mytextarea-forKanban" id="describe" name="describe" placeholder="Напишите, что ожидаете.."></textarea>

                        <label class="mylabel-forkanban" for="taskEndDate">Желаемая дата завершения</label>
                        <input class="myinput-forKanban" type="date" id="taskEndDate" name="date"/>

                        <input type="hidden" name="started" value="true"/>
                        <input type="hidden" name="continues" value="false"/>
                        <input type="hidden" name="finished" value="false"/>
                        <input type="hidden" name="user"/>

                        <button class="btn" type="button" onclick="submitForm()">Сохранить</button>
                    </form:form>
                </div>
            </div>
        </div>
    </div>

    <div id="openModal" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="editKanban">
                    <input id="kanbanId" type="hidden" name="kanbanId">
                    <div class="modal-header" id="modal-header">
                        <a title="Edit" class="edit" onclick="editKanbanName()">...</a>
                        <a title="Close" class="close" onclick="closeModal()">×</a>
                    </div>
                    <div class="modal-body my-modal">
                        <label id="taskAuthor" class="mylabel-forkanban" style="margin: 0">Автор: </label>
                        <label id="labelDescribe" class="mylabel-forkanban">Описание</label>
                        <label id="labelEndDate" class="mylabel-forkanban">Плановая дата выполнения</label>
                        <label id="labelMembers" class="mylabel-forkanban">Участники:
                            <ul id="membersBlock" class="membersBlock"></ul>
                        </label>
                        <select name="memberSelect" id="memberSelect" onchange="editMembers()">
                            <option value="" disabled selected>Добавить</option>
                        </select>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <form id="kanbanForm"></form>
</div>
</body>

<script>
      const columns = document.querySelectorAll('.column');
      columns.forEach((column) => {
        new Sortable(column, {
          group: "shared",
          animation: 150,
          ghostClass: "blue-background-class",

            onAdd: function (evt) {
              if(column.id === "col1"){
                  evt.item.className = "list-group-item started span-shadow";
              }
              else if(column.id === "col2"){
                  evt.item.className = "list-group-item continues span-shadow-yellow";
              }
              else if(column.id === "col3"){
                  evt.item.className = "list-group-item finished span-shadow-green";
              }


                $.ajax({
                    type: 'POST',
                    url: '/kanban/move',
                    data: { columnId: column.id, kanbanId: evt.item.id },
                    success: function (data) {
                        // запустится при успешном выполнении запроса и в data будет ответ скрипта
                    },
                    error: function () {
                        alert('Ошибка сохранения статуса задачи!');
                        console.log(evt);
                    }
                });
            }
        });
      });

      var coll = document.getElementsByClassName("collapsible");
      var i;

      for (i = 0; i < coll.length; i++) {
          coll[i].addEventListener("click", function() {
              this.classList.toggle("active");
              var content = this.nextElementSibling;
              if (content.style.maxHeight){
                  content.style.maxHeight = null;
              } else {
                  content.style.maxHeight = content.scrollHeight + "px";
              }
          });
      }

      function deleteKanban(id){
          let el = document.getElementById(id);
          el.setAttribute("hidden", true);
          $.ajax({
              type: 'POST',
              url: '/kanban/delete',
              data: { kanbanId: id },
              success: function (data) {
                  // запустится при успешном выполнении запроса и в data будет ответ скрипта
              },
              error: function () {
                  el.removeAttribute("hidden");
                  alert('Ошибка удаления задачи!');
              }
          });
      }

      function submitForm(){
          let date = document.getElementById("taskEndDate").value;
          console.log(date);
          if (date === ""){
              alert("Заполните плановую дату задачи!");
              return;
          }
          $('#newCanbanForm').submit();
      }

      function openModal(id){
          $.ajax({
              type: 'GET',
              url: '/kanban/getKanban',
              data: {kanId: id},
              success: function (data) {
                  // запустится при успешном выполнении запроса и в data будет ответ скрипта
                  clearModal();
                  document.getElementById("kanbanId").value = data.id;

                  // <input type="text" class="modal-title myinput-forKanban" id="modal-title" name="kanbanName" disabled="" onchange="changeTitle(this.value)">
                  let title = document.createElement("input");
                  title.type = "text"
                  title.className = "modal-title myinput-forKanban";
                  title.id = "modal-title";
                  title.name = "kanbanName";
                  title.disabled = "disabled";
                  title.value = data.kanbanName;
                  title.form = "editKanban";
                  title.setAttribute("onchange", "changeTitle(this.value)");
                  document.getElementById("modal-header").prepend(title);

                  // <a>Иванов С.Н.</a>
                  let author = document.createElement("a");
                  author.textContent = data.userDtoNameOnlyWithPositionDto.name;
                  document.getElementById("taskAuthor").append(author);

                  // <textarea name="describe" id="describe" class="mytextarea-forKanban" onchange="changeDescribe(this.value)"></textarea>
                  let describe = document.createElement("textarea");
                  describe.form = "kanbanForm";
                  describe.name = "describe";
                  describe.id = "describe";
                  describe.innerText = data.describe;
                  describe.className = "mytextarea-forKanban";
                  describe.setAttribute("onchange", "changeDescribe(this.value)");
                  document.getElementById("labelDescribe").append(describe);

                  // <input class="myinput-forKanban" id="endDate" name="endDate" type="date" onchange="editDate(this.value)" style="margin-top: 2px; width: 160px;">
                  let endDate = document.createElement("input");
                  endDate.className = "myinput-forKanban";
                  endDate.id = "endDate";
                  endDate.name = "endDate";
                  endDate.type = "date";
                  endDate.form = "kanbanForm";
                  if (data.taskEndDate != null){
                      let newDate = new Date(data.taskEndDate);
                      newDate.setDate(newDate.getDate() + 1);
                      endDate.valueAsDate = newDate;
                  }
                  endDate.style = "margin-top: 2px; width: 160px";
                  endDate.setAttribute("onchange", "editDate(this.value)");
                  document.getElementById("labelEndDate").append(endDate);

                  // <ul id="membersBlock" class="membersBlock"><li class="member">Петрова И.Н.</li></ul>
                  let membersBlock = document.getElementById("membersBlock");
                  for (let i = 0; i < data.userDtoNameOnlyList.length; i++){
                      let member = document.createElement("li");
                      member.innerText = data.userDtoNameOnlyList[i].name;
                      member.className = "member";
                      membersBlock.append(member);
                  }
                  document.getElementById("labelMembers").append(membersBlock);

                  // <select name="memberSelect" id="memberSelect">
                  let memberSelect = document.getElementById("memberSelect");
                  $.ajax({
                      type: 'GET',
                      url: '/kanban/getUserDtoList',
                      data: {kanId: id},
                      success: function (data) {
                          for (let i = 0; i < data.length; i++) {
                              let opt = document.createElement("option");
                              opt.value = data[i].userId;
                              opt.innerText = data[i].name;
                              memberSelect.append(opt);
                          }
                      },
                      error: function () {
                          alert('Ошибка получения списка пользователей! \n' +
                                'function openModal(id) \n' +
                                'url: /kanban/getUserDtoList');
                      }
                  });
              },
              error: function () {
                  alert('Ошибка получения данных с сервера! \n function openModal(id)');
              }
          });

          document.location='#openModal'
      }

      function clearModal(){
          let el = document.getElementById("modal-title");
          if (el != null){
              el.remove();
          }

          let describe = document.getElementById("describe");
          if (describe != null){
              describe.remove();
          }
      }

      function closeModal(){
          document.location = '#close';
          location.reload();
      }

      function editKanbanName(){
          let el = document.getElementById("modal-title");
          el.removeAttribute("disabled");
          el.focus();
          el.setSelectionRange(el.value.length,el.value.length);
      }

      function changeTitle(name){
          const msg = $('#editKanban').serialize();
          $.ajax({
              type: 'POST',
              url: '/kanban/editName',
              data: msg,
              success: function (data) {
              },
              error: function () {
                  alert('Ошибка отправки данных на сервер! \n function changeTitle(name)');
              }
          });
      }

      function changeDescribe(describe){
          const msg = $('#editKanban').serialize();
          $.ajax({
              type: 'POST',
              url: '/kanban/editDescribe',
              data: msg,
              success: function (data) {
              },
              error: function () {
                  alert('Ошибка отправки данных на сервер! \n function changeDescribe(describe)');
              }
          });
      }

      function editDate(date){
          const msg = $('#editKanban').serialize();
          $.ajax({
              type: 'POST',
              url: '/kanban/editDate',
              data: msg,
              success: function (data) {
              },
              error: function () {
                  alert('Ошибка отправки данных на сервер! \n function editDate(date)');
              }
          });
      }

      function editMembers(){
          const msg = $('#editKanban').serialize();
          $.ajax({
              type: 'POST',
              url: '/kanban/editMembers',
              data: msg,
              success: function (data){
                  let member = document.createElement("li");
                  member.innerText = data.name;
                  member.className = "member";
                  document.getElementById("membersBlock").append(member);
              },
              error: function(){
                  alert('Ошибка отправки данных на сервер! \n function editMembers()')
              }
          });

      }
</script>

</html>
