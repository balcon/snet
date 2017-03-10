<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<tags:mainPage>
    <h2>Chat with</h2>

    <form action="chat" method="post">
        <input type="hidden" name="companionId" value="${companionId}">
        <input type="text" name="body" placeholder="Input text here">
        <input type="submit" value="Send">
    </form>

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
                </tr>
            </c:forEach>
        </table>
    </c:if>
</tags:mainPage>
