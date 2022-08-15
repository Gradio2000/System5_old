<%--
  Created by IntelliJ IDEA.
  User: aleksejlaskin
  Date: 17.08.2022
  Time: 16:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Sortable/1.14.0/Sortable.min.js" integrity="sha512-zYXldzJsDrNKV+odAwFYiDXV2Cy37cwizT+NkuiPGsa9X1dOz04eHvUWVuxaJ299GvcJT31ug2zO4itXBjFx4w==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <div class="container">
  <div class="column">
    <h1>All Tasks</h1>
    <div class="list-group-item" draggable="true">Wash Clothes</div>
    <div class="list-group-item" draggable="true">Take a stroll outside</div>
    <div class="list-group-item" draggable="true">Design Thumbnail</div>
    <div class="list-group-item" draggable="true">Attend Meeting</div>
    <div class="list-group-item" draggable="true">Fix workshop</div>
    <div class="list-group-item" draggable="true">Visit the zoo</div>
  </div>
  <div class="column">
    <h1>In progress</h1>
  </div>
  <div class="column">
    <h1>Paused</h1>
  </div>
  <div class="column">
    <h1>Under Review</h1>
  </div>
  <div class="column">
    <h1>Completed</h1>
  </div>
</div>
</body>
<script>
  const items = document.querySelectorAll('.item');
  const columns = document.querySelectorAll('.column');

  columns.forEach((column) => {
    new Sortable(column, {
      group: "shared",
      animation: 150,
      ghostClass: "blue-background-class"
    });
  });

  items.forEach(item => {
    item.addEventListener('dragstart', dragStart)
    item.addEventListener('dragend', dragEnd)
  });

  let dragItem = null;

  function dragStart() {
    console.log('drag started');
    dragItem = this;
    setTimeout(() => this.className = 'invisible', 0)
  }

  function dragEnd() {
    console.log('drag ended');
    this.className = 'item'
    dragItem = null;
  }

  columns.forEach(column => {
    column.addEventListener('dragover', dragOver);
    column.addEventListener('dragenter', dragEnter);
    column.addEventListener('dragleave', dragLeave);
    column.addEventListener('drop', dragDrop);
  });

  function dragDrop() {
    console.log('drag dropped');
    // this.append(dragItem);
  }

  function dragOver(e) {
    e.preventDefault()
    console.log('drag over');
  }

  function dragEnter() {
    console.log('drag entered');
  }
  function dragLeave() {
    console.log('drag left');
  }

</script>
<style>
  .container {
    font-family: "Trebuchet MS", sans-serif;
    display: flex;
    gap: 30px;
  }
  .column {
    flex-basis: 20%;
    background: #ddd;
    min-height: 90vh;
    padding: 5px;
    border-radius: 10px;
  }
  .column h1 {
    text-align: center;
    font-size: 22px;
  }
  .list-group-item {
    background: #fff;
    margin: 20px;
    padding: 20px;
    border-radius: 5px;
    cursor: pointer;
  }
</style>
</html>
