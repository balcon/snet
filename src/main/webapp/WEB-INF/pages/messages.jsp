<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<tags:mainPage>
    <h2>Messages</h2>

    <c:if test="${empty messages}">
        There are no messages
    </c:if>
    <c:if test="${not empty messages}">
        <table border="1">
            <tr>
                <td>From</td>
                <td>To</td>
                <td>Body</td>
            </tr>
            <c:forEach var="message" items="${messages}">
                <tr>
                    <td>
                        <c:out value="${message.getSender().getUsername()}"/>
                    </td>
                    <td>
                        <c:out value="${message.getReceiver().getUsername()}"/>
                    </td>
                    <td>
                        <c:out value="${message.getBody()}"/>
                    </td>
                    <td>
                        <c:url var="urlToChat" value="/main/chat"/>

                        <c:set var="companionId" value="${message.getReceiver().getId()}"/>
                        <%-- Current logged user can't be chat-companion to itself --%>
                        <c:if test="${companionId==sessionScope.user.getId()}">
                            <c:set var="companionId" value="${message.getSender().getId()}"/>
                        </c:if>
                        <a href="${urlToChat}?companionId=${companionId}">BeginChat</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</tags:mainPage>
