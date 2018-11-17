<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<div class="navbaritem"><a href="index.jsp">Новости|Главная</a></div>
<div class="navbaritem"><a href="employes.jsp">Работники</a></div>
<div class="navbaritem"><a href="about.jsp">Про</a></div>
<div class="navbaritem"><a href="scripts.jsp">Serverside js</a></div>
<c:if test="${applicationScope.ru_otus_sua_statistic_GATHERING eq 'ENABLED'}">
    <c:if test="${not empty sessionScope.AuthenticatedUser}">
        <div class="navbaritem"><a href="${pageContext.request.contextPath}/statistic">Статистика</a></div>
    </c:if>
</c:if>
<div class="navbaritem"><a href="login.jsp">Вход</a></div>