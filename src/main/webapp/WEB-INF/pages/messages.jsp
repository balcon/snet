<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.view" var="view"/>

<jsp:useBean id="messages" scope="request" type="com.epam.study.snet.model.Messages"/>

<tags:mainMenu active="messages">
    <div class="col-xs-offset-3">
        <h3><fmt:message bundle="${view}" key="messages.last_messages"/></h3>
    </div>
    <c:if test="${empty messages.lastMessages}">
        <br>
        <div class="alert alert-info">
            <fmt:message bundle="${view}" key="messages.no_messages"/>
        </div>
    </c:if>
    <c:url var="urlToChat" value="/main/chat"/>

    <c:forEach var="lastMessage" items="${messages.lastMessages}">
        <a href="${urlToChat}?companionId=${lastMessage.companion.id}" style="text-decoration: none;">
            <c:set var="unreadMessages" value="${lastMessage.numberUnread}"/>
            <div class="media panel panel-primary
              <c:if test=" ${unreadMessages>0}"> unread-message</c:if>" style="margin-bottom: 5px">
                <div class="panel-body">
                    <div class="media-left media-top">
                        <img src="<c:url value="${lastMessage.companion.photo.sourcePath}"/>"
                             class="media-object chat-photo">
                    </div>
                    <div class="media-body">
                        <c:set var="companionFullName"
                               value="${lastMessage.companion.firstName} ${lastMessage.companion.lastName}"/>
                        <c:set var="userDeleted" value="${lastMessage.companion.deleted}"/>
                        <h4 class="media-heading">
                            <c:if test="${userDeleted}">
                            <del></c:if>
                                <c:out value="${companionFullName}"/>
                                <c:if test="${userDeleted}"></del>
                            </c:if>
                            <small>
                                <c:set var="preparedDateTime"
                                       value="${fn:replace(lastMessage.lastMessageTime, 'T', ' ')}"/>
                                <fmt:parseDate value="${preparedDateTime}" pattern="yyyy-MM-dd HH:mm:ss"
                                               var="parsedDateTime" type="both"/>
                                <i><fmt:formatDate value="${parsedDateTime}" type="both" timeStyle="short"/></i>
                            </small>
                            <c:if test="${unreadMessages>0}"><span class="badge">${unreadMessages}</span></c:if>
                        </h4>

                        <p>
                            <c:if test="${lastMessage.response}">
                                <img src="<c:url value="${lastMessage.loggedUser.photo.sourcePath}"/>"
                                     class="chat-photo-response">
                            </c:if>
                            <c:out value=" ${lastMessage.body}"/>
                        </p>
                    </div>
                </div>
            </div>
        </a>
    </c:forEach>
</tags:mainMenu>
