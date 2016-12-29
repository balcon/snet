
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>man</title>
</head>
<body>
<c:if test="${empty list}">
    empty
</c:if>
<c:if test="${not empty list}">
    not empty
    <table border="1">
    <c:forEach var="ob" varStatus="status" items="${list}">
        <tr>
            <td><c:out value="${ob}"/></td>
        </tr>
    </c:forEach>
    </table>
</c:if>
<form action="main" method="get">
    <input type="text" name="text">
    <input type="submit" value="POST">
</form>
</body>
</html>
