<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>SNetwork</title>
</head>
<body>
<table height="100%" align="center">
<tr valign="center">
<td> <table border="1">
        <tr>
            <td><a href="<c:url value="/main"/>">Main</a></td>
            <td><a href="<c:url value="/main/page1"/>">Page1</a></td>
            <td><a href="<c:url value="/main/messages"/>">Messages</a></td>
            <td><a href="<c:url value="/main/userList"/>">List</a></td>
            <td><a href="<c:url value="/registration"/>">Registration</a></td>
            <td><a href="<c:url value="/login"/>">LogIn</a></td>
            <c:if test="${not empty sessionScope.user.getUsername()}">
                <td>Logged as: <b><c:out value="${sessionScope.user.getUsername()}"/> (ID: ${sessionScope.user.getId()})</b></td>
            </c:if>

        </tr>
</table></td>
</tr>
    <tr valign="center">
        <td align="center">

            <jsp:doBody/>
        </td>
    </tr>
</table>

</body>
</html>
