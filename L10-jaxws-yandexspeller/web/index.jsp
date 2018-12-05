<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JAX-WS Yandex Spell Client</title>
</head>

<body>

<form action="${pageContext.request.contextPath}/spellCheck" method="POST">
    <table border="0">

        <tr>
            <td><b>Word</b></td>
            <td><input type="text" name="word" value="синхрафазатрон" size="65"/></td>
        </tr>

        <tr>
            <td><b>Check</b></td>
            <td><label name="correction" value="" size="65">${correction}</label></td>
        </tr>

        <tr>
            <td colspan="2"><input type="submit" value="Spellcheck"/></td>
        </tr>
    </table>
</form>

<table>
    <th>Код</th>
    <th>Ошибка</th>
    <th>Краткое описание</th>
    </tr></thead>
    <tbody>
    <tr>
        <td>1</td>
        <td><span>ERROR_UNKNOWN_WORD</span></td>
        <td>Слова нет в словаре.</td>
    </tr>
    <tr>
        <td>2</td>
        <td><span>ERROR_REPEAT_WORD</span></td>
        <td>Повтор слова.</td>
    </tr>
    <tr>
        <td>3</td>
        <td><span>ERROR_CAPITALIZATION</span></td>
        <td>Неверное употребление прописных и строчных букв.
        </td>
    </tr>
    <tr>
        <td>4</td>
        <td><span>ERROR_TOO_MANY_ERRORS</span></td>
        <td>Текст содержит слишком много ошибок. При этом приложение может отправить Яндекс.Спеллеру оставшийся
            непроверенным текст в следующем запросе.
        </td>
    </tr>
    </tbody>
</table>

</body>
</html>