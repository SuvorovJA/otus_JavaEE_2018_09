<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="doit" uri="/stattags" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>${pageContext.request.contextPath}: Servlet и JSP - Browsers</title>
</head>
<body><doit:statistic/> <%--TODO redirecting to /statistic blocked in browserfilter--%>

<div>
    <h3>Вы используете неподдерживаемую версию браузера</h3>
    <a href="https://yandex.ru/ie/" target="_blank"><img
            src="${pageContext.request.contextPath}/icons/Ie128.png"></a>
    <a href="https://download.my-chrome.ru/" target="_blank"><img
            src="${pageContext.request.contextPath}/icons/Chrome128.png"></a>
    <a href="https://yandex.ru/firefox/" target="_blank"><img
            src="${pageContext.request.contextPath}/icons/Ff128.png"></a>
    <a href="https://yandex.ru/opera/" target="_blank"><img
            src="${pageContext.request.contextPath}/icons/Opera128.png"></a>
</div>
</body>
</html>