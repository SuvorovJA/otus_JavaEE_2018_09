<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JAX-WS Tax Estimate Client</title>
</head>

<body>

<form action="${pageContext.request.contextPath}/taxEstimate" method="POST">
    <table border="0">

        <tr>
            <td><b>Revenues</b></td>
            <td><input type="text" name="revenues" value="1000000.0" size="65"/></td>
        </tr>
        <tr>
            <td><b>Expenses</b></td>
            <td><input type="text" name="expenses" value="200000.0" size="65"/></td>
        </tr>
        <tr>
            <td><b>Tax Rate</b></td>
            <td><input type="text" name="taxRate" value="20.0" size="65"/></td>
        </tr>
        <tr>
            <td><b>Income Tax</b></td>
            <td><label name="incomeTax" value="" size="65">${incomeTax}</label></td>
        </tr>

        <tr>
            <td colspan="2"><input type="submit" value="Estimate"/></td>
        </tr>
    </table>
</form>

</body>
</html>