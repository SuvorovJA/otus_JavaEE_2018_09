<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div>Работники</div>

<jsp:include page="employeServlet"/>

<p style="color: red;">${errorString}</p>

<form method="GET">
    <table border="1" cellpadding="5" cellspacing="1">
        <tr>
            <td><input type="text" style="width:98%;" name="search_fullName" value="${search_fullName}"/></td>
            <td>
                <%--<div>--%>
                <input type="text" style="float:left;display: inline;width:46%;"
                       placeholder="min"
                       name="search_age_min" value="${search_age_min}"/>
                <input type="text" style="float:right;display: inline;width:46%;"
                       placeholder="max"
                       name="search_age_max" value="${search_age_max}"/>
                <%--</div>--%>
            </td>
            <td><input type="text" style="width:98%;" name="search_city" value="${search_city}"/></td>
            <td><input type="text" style="width:98%;" name="search_departament" value="${search_departament}"/></td>
            <td><input type="text" style="width:98%;" name="search_appointment" value="${search_appointment}"/></td>
            <td><%--<input type="text" style="width:98%;" name="search_salary" value="${search_salary}"/>--%></td>
            <td><input type="text" style="width:98%;" name="search_login" value="${search_login}"/></td>
            <td>
                <button type="submit">Поиск</button>
            </td>
            <td><a href="${pageContext.request.contextPath}/employes.jsp">
                <button type="button">Очистить</button>
            </a></td>
        </tr>

        <tr>
            <th>ФИО</th>
            <th>Д.Р.</th>
            <th>Город</th>
            <th>Отдел</th>
            <th>Должность</th>
            <th>Зарплата</th>
            <th>Логин</th>
            <th colspan="2"></th>
        </tr>

        <%--FOR HOWTO--%>
        <%--<jsp:useBean id="jpa" class="ru.otus.sua.L07.entities.helpers.JpaDtoForEmployeEntity"--%>
        <%--scope="page"></jsp:useBean>--%>
        <%--<c:forEach items="${jpa.instanceReadAllEmployesForJSP().employes}" var="e">--%>

        <c:forEach items="${employes}" var="e">
            <tr>
                <td>${e.fullName}</td>
                <td>
                    <fmt:formatDate value="${e.dateOfBirth}" pattern="dd.MM.yyyy"/>
                </td>
                <td>${e.city}</td>
                <td>${e.departament.name}</td>
                <td>${e.appointment.name}</td>
                <td>${e.salary}</td>
                <td>${e.credentials.login}</td>
                <td><a href="editEmploye?id=${e.id}">Изменить</a></td>
                <td><a href="deleteEmploye?id=${e.id}">Удалить</a></td>
            </tr>
        </c:forEach>
    </table>
</form>