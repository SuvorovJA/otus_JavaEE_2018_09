<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="doit" uri="/stattags" %>
<doit:statistic/>

<jsp:include page="login"/>

<p style="color: red;">${errorString}</p>
<p style="color: green;">${infoString}</p>

<div>
    <form method="POST" action="${pageContext.request.contextPath}/login">
        <label class="loginform">Логин
            <input type="text" name="login" required pattern="^[a-z0-9_-]{3,8}$"
                   title="От 3 до 8 латинских букв и цифр"></label><br>
        <!--9 special for testing server-side validation-->
        <label class="loginform">Пароль
            <input type="password" name="password" required pattern="^[a-z0-9_-]{3,9}$"
                   title="От 3 до 8 латинских букв и цифр"></label><br>
        <br>
        <input type="submit" value="Вход">
    </form>
</div>