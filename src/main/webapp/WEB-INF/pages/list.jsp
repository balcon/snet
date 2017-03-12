<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<tags:mainPage>
    <c:if test="${empty users}">
        There are no users
    </c:if>
    <c:if test="${not empty users}">
        <table border="1">
            <tr>
                <td>ID</td>
                <td>Username</td>
                <td>First Name</td>
                <td>Last Name</td>
            </tr>
            <c:forEach var="user" items="${users}">
                <c:if test="${user.getId()!=sessionScope.user.getId()}">
                    <tr>
                        <td>
                            <c:out value="${user.getId()}"/>
                        </td>
                        <td>
                            <c:out value="${user.getUsername()}"/>
                        </td>
                        <td>
                            <c:out value="${user.getFirstName()}"/>
                        </td>
                        <td>
                            <c:out value="${user.getLastName()}"/>
                        </td>

                        <td>
                            <c:url var="urlToChat" value="/main/chat"/>
                            <c:set var="companionId" value="${user.getId()}"/>
                            <a href="${urlToChat}?companionId=${companionId}">BeginChat</a></td>
                    </tr>
                </c:if>
            </c:forEach>
        </table>
    </c:if>
</tags:mainPage>
