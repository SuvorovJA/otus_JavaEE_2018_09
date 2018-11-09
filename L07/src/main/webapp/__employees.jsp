<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div>Работники</div>

<%--here will login check and redirect to login page--%>

<p style="color: red;">${errorString}</p>

<form method="GET">
    <table border="1" cellpadding="5" cellspacing="1">
        <%--<tr>--%>
        <%--<td><input type="text" style="width:98%;" name="search_inn" value="${search_inn}" /></td>--%>
        <%--<td><input type="text" style="width:98%;" name="search_ogrn" value="${search_ogrn}" /></td>--%>
        <%--<td><input type="text" style="width:98%;" name="search_name" value="${search_name}" /></td>--%>
        <%--<td><input type="text" style="width:98%;" name="search_address" value="${search_address}" /></td>--%>
        <%--<td><button type="submit">Поиск</button></td>--%>
        <%--<td><a href="${pageContext.request.contextPath}/insuranceOrgsList"><button type="button">Очистить</button></a></td>--%>
        <%--</tr>--%>

        <tr>
            <th>ФИО</th>
            <th>Д.Р.</th>
            <th>Город</th>
            <th>Отдел</th>
            <th>Должность</th>
            <th>Логин</th>
            <th colspan="2"></th>
        </tr>


        <jsp:useBean id="jpa" class="ru.otus.sua.L07.entities.helpers.JpaDtoForEmployeEntity"
                     scope="page"></jsp:useBean>

        <c:forEach items="${jpa.instanceReadAllEmployesForJSP().employes}" var="e">
            <tr>
                <td>${e.fullname}</td>
                <td>
                    <fmt:formatDate value="${e.dateOfBirth}" pattern="dd.MM.yyyy"></fmt:formatDate>
                </td>
                <td>${e.city}</td>
                <td>${e.department.name}</td>
                <td>${e.appointment.name}</td>
                <td>${e.credentials.login}</td>
                <td><a href="editEmploye?id=${e.id}">Изменить</a></td>
                <td><a href="deleteEmploye?id=${e.id}">Удалить</a></td>
            </tr>
        </c:forEach>
    </table>
</form>