<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>${pageContext.request.contextPath}: Servlet и JSP - Серверные скрипты</title>
    <link href="${pageContext.request.contextPath}/css/styles2.css" rel="stylesheet" type="text/css">
    <script src="${pageContext.request.contextPath}/js/currency.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/news.js" type="text/javascript"></script>
</head>
<body onload="getCurrency();getNews();">
<div class="center">
    <header>
        <jsp:include page="_header.html"></jsp:include>
    </header>
    <nav>
        <jsp:include page="_navigationbar.html"></jsp:include>
    </nav>
    <main>
        <jsp:include page="__execute.jsp"></jsp:include>
    </main>
    <aside>
        <jsp:include page="__currency.html"></jsp:include>
    </aside>
    <footer>
        <jsp:include page="_footer.html"></jsp:include>
    </footer>
</div>
</body>
</html>