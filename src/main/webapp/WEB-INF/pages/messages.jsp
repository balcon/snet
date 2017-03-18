<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.view" var="view"/>
<fmt:setBundle basename="i18n.errors" var="errors"/>

<tags:mainMenu active="messages">
    <div class="page-header col-md-offset-4">
        <h3><fmt:message bundle="${view}" key="messages.title"/></h3>
    </div>

    <c:if test="${empty messages}">
        There are no messages
    </c:if>
    <c:if test="${not empty messages}">

        <c:url var="urlToChat" value="/main/chat"/>
        <c:forEach var="message" items="${messages}">
            <%--LAST MESSAGE--%>
            <c:set var="companionId" value="${message.getReceiver().getId()}"/>
            <%-- Current logged user can't be chat-companion to itself --%>
            <c:if test="${companionId==sessionScope.user.getId()}">
                <c:set var="companionId" value="${message.getSender().getId()}"/>
            </c:if>
            <a href="${urlToChat}?companionId=${companionId}" style="text-decoration: none;">
                <div class="media panel panel-primary ">
                    <div class="panel-body">
                        <div class="media-left">
                            <img src="https://www.w3schools.com/bootstrap/img_avatar1.png" class="media-object"
                                 style="width:60px">
                        </div>
                        <div class="media-body">
                            <h4 class="media-heading"><c:out value="${message.getSender().getFirstName()}"/>
                                <small><i>12.13.2014 12:00</i></small>
                            </h4>
                            <p><c:out value="${message.getBody()}"/></p>
                        </div>
                    </div>
                </div>
            </a>
            <%-- END OF LAST MESSAGE --%>

        </c:forEach>
    </c:if>
</tags:mainMenu>
