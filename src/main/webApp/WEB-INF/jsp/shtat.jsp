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
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>


<html>
<head>
    <title>Title</title>
</head>
<body>


<table class="iksweb" >
    <tbody>
    <tr>
        <th class="tblsht">Структурное подразделение</th>
    </tr>
    <c:forEach var="division" items="${divisionList}">
    <tr>
        <td class="tblsht">
            <a href= "javascript:getPositions(${division.divisionId - 1})">${division.division}</a>
        </td>
    </tr>
    </c:forEach>
    <tr>
        <label style="color: crimson; font: bold italic 110% serif">
            <c:if test="${param.get('errordivision') == true}">Введите название структурного подразделения!</c:if>
        </label>
        <td class="tblsht" id="insbtn"><button name="addDiv" id="mybtn" type="button" class="btn" onclick="insertInputText()">Добавить</button></td>
    </tr>

    </tbody>
</table>
<br/>
<table class="iksweb" id="myTable">
    <tbody >
    <tr>
        <th class="tblsht">Должности</th>
        <th class="tblsht">Работники</th>
    </tr>
    </tbody>
</table>


<form id="addDivision" name="addDivision" method="post" action="/admin/division">
</form>


</body>

<script>
    //рабочая функция на потом
    // function call(){
    //     const msg = $('#addDivision').serialize();
    //     $.ajax({
    //         type: 'POST',
    //         url: '/admin/division',
    //         data: msg,
    //         success: function (data) {
    //             // запустится при успешном выполнении запроса и в data будет ответ скрипта
    //         },
    //         error: function () {
    //             alert('Ошибка!');
    //             console.log(msg);
    //         }
    //     });
    // }


        function getPositions(id){
       $('.insert').remove();
       $.ajax({
           url: "/positions/" + id
       }).then(function(data) {
           const data1 = data._embedded.positions;
           for (let i = 0; i < data1.length; i++){
               let position = data1[i].position;
               $('#myTable > tbody:last-child').append(`
                            <tr class="insert">
                                <td class="tblsht">
                                    <a href="/list/` + data1[i].user.userId + `">` +
                                        data1[i].position +`
                                    </a>
                                </td>
                                <td class="tblsht">` +
                                      data1[i].user.name +
                                `</td>
                            </tr>

                        `);
           }

            $('#myTable > tbody:last-child').append(`<tr>
            <td colspan="2" class="tblsht insert"><button name="addDiv" type="button" class="btn">Добавить</button></td>
            </tr>`);
            });
        };

        function insertInputText(){
        $('#mybtn').hide();
        $('#insbtn')
            .prepend('<input class="myinput rem" form="addDivision" name="division" placeholder="Введите подразделение"/>')
            .append('<button type="submit" id="sendButton" class="btn rem" form="addDivision">OK</button>')
            .append('<button type="button" class="btncancel rem" onclick=getShtat()>Отмена</button>');
        }

    function getShtat() {
        $('.rem').remove();
        $('#mybtn').show();
    }
</script>

<style>
    <%@include file="myStyle.css"%>
</style>
</html>
