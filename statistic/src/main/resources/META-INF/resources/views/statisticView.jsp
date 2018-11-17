<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="stat" uri="/stattags" %>
<html>
<head>
    <title>Просмотр статистики</title>
</head>
<body>

<c:choose>
    <c:when test="${applicationScope.ru_otus_sua_statistic_GATHERING eq 'DISABLED'}">
        <p>сбор статистической информации отключен</p>
    </c:when>
    <c:when test="${totalPages == 0}">
        <p>статистической информации нет</p>
    </c:when>
    <c:otherwise>
        <div style="justify-content: center;">
            <table border="1" cellpadding="3" cellspacing="0" style="margin: 0 auto;">
                <tr>
                    <th>id</th>
                    <th>prev id</th>
                    <th>login</th>
                    <th>session</th>
                    <th>page</th>
                    <th>ipaddress</th>
                    <th>browser</th>
                    <th>client time</th>
                    <th>client tzo</th>
                    <th>server time</th>
                    <th>marker</th>
                </tr>

                <c:forEach var="entity" items="${statisticList}">
                    <tr>
                        <td>${entity.id}</td>
                        <td>${entity.prevId}</td>
                        <td>${entity.login}</td>
                        <td>${entity.session}</td>
                        <td>${entity.page}</td>
                        <td>${entity.ipaddress}</td>
                        <td>${entity.browser}</td>
                        <td>${entity.clientTimestamp}</td>
                        <td>${entity.clientTZOffset}</td>
                        <td>${entity.serverTimestamp}</td>
                        <td>${entity.marker}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <p/>
        <div style="justify-content: center;">
            <table border="1" cellpadding="5" cellspacing="5" style="margin: 0 auto;">
                <tr>
                    <td>
                        <c:if test="${currentPage != 1}">
                            <a href="${pageContext.request.contextPath}/statistic?page=${currentPage - 1}">&lt;&lt;&lt;</a>
                        </c:if>
                    </td>
                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <c:choose>
                            <c:when test="${currentPage eq i}">
                                <td>${i}</td>
                            </c:when>
                            <c:otherwise>
                                <td><a href="${pageContext.request.contextPath}/statistic?page=${i}">${i}</a></td>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <td>
                        <c:if test="${currentPage lt totalPages}">
                            <a href="${pageContext.request.contextPath}/statistic?page=${currentPage + 1}">&gt;&gt;&gt;</a>
                        </c:if>
                    </td>
                </tr>
            </table>
        </div>
    </c:otherwise>
</c:choose>
</body>
</html>
