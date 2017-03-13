<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<tags:mainMenu active="messages">
    <h2>Messages</h2>

    <c:if test="${empty messages}">
        There are no messages
    </c:if>
    <c:if test="${not empty messages}">

            <c:forEach var="message" items="${messages}">

                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <c:out value="${message.getSender().getUsername()}"/>
                        <span class="glyphicon glyphicon-hand-right"></span>
                        <c:out value="${message.getReceiver().getUsername()}"/></div>
                    <div class="panel-body">
                        <c:out value="${message.getBody()}"/>
                    </div>
                </div>


                        <%--<c:url var="urlToChat" value="/main/chat"/>--%>

                        <%--<c:set var="companionId" value="${message.getReceiver().getId()}"/>--%>
                        <%--&lt;%&ndash; Current logged user can't be chat-companion to itself &ndash;%&gt;--%>
                        <%--<c:if test="${companionId==sessionScope.user.getId()}">--%>
                            <%--<c:set var="companionId" value="${message.getSender().getId()}"/>--%>
                        <%--</c:if>--%>
                        <%--<a href="${urlToChat}?companionId=${companionId}">BeginChat</a>--%>

            </c:forEach>
    </c:if>
</tags:mainMenu>
