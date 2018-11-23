<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="doit" uri="/stattags" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>${pageContext.request.contextPath}: Servlet и JSP - Работники</title>
    <link href="${pageContext.request.contextPath}/css/styles2.css" rel="stylesheet" type="text/css">
    <script src="${pageContext.request.contextPath}/js/ws.js" type="text/javascript"></script>
</head>
<body onload="getCurrency();getNews();"><doit:statistic/>
<div class="center">
    <header>
        <jsp:include page="_header.html"/>
    </header>
    <nav>
        <jsp:include page="_navigationbar.jsp"/>
    </nav>
    <main>
        <jsp:include page="__employes.jsp"/>
    </main>
    <aside>
        <jsp:include page="__currency.html"/>
    </aside>
    <footer>
        <jsp:include page="_footer.html"/>
    </footer>
</div>
</body>
</html>