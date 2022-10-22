
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c"   %>
<div class="sidenav">
    <a href="/list">Мои оценки</a>
    <a href="/my_employers">Оценка работников</a>
    <br/>
    <a href="/tests/mytests">Мои тесты</a>
    <a href="/testGroup/listForTesting">Тестирование</a>
    <a href="/appointTests/getUserAppoint">Назначенные зачёты</a>
    <br/>

    <c:forEach var="role" items="${user.roles}">
        <c:if test="${role == 'ADMIN'}">
            <a href="/admin/prepareReport">Отчет за месяц</a>
            <a href="/admin/prepareHalfYearReport">Отчет за полугодие</a>
            <a href="/admin/prepareYearReport">Отчет за год</a>
            <br/>
            <a href="/admin/archive">Архив оценок</a>
            <a href="/admin/shtat">Штатное расписание</a>
            <br/>
        </c:if>
    </c:forEach>

<c:forEach var="role" items="${user.roles}">
    <c:if test="${role == 'ADMIN_TEST'}">
    <a href="/testGroup/list">Редактирование тестов</a>
    <a href="/exam/exam">Назначение зачётов</a>
    <a href="/exam/journal">Журнал зачётов (ЭКР)</a>
    <a href="/exam/journalBase">Журнал зачетов (общий)</a>
    <br/>
    </c:if>
</c:forEach>
    <a href="/kanban/kanban">Канбан</a>
    <a href="/converter/converter">Конвертер ОИСФЛ</a>
    </br>
    <br/>
    <a href="/search">Поиск</a>
    <br/>
    <a href="/logout">Выйти</a>
</div>

