<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.view" var="view"/>

<tags:mainMenu active="messages">
    <div class="col-md-offset-3">
        <h3><fmt:message bundle="${view}" key="messages.last_messages"/></h3>
    </div>
    <c:if test="${empty lastMessages}">
        <br>
        <div class="alert alert-info">
            <fmt:message bundle="${view}" key="messages.no_messages"/>
        </div>
    </c:if>
    <c:url var="urlToChat" value="/main/chat"/>

    <c:forEach var="lastMessage" items="${lastMessages}">
        <a href="${urlToChat}?companionId=${lastMessage.getCompanion().getId()}" style="text-decoration: none;">
            <c:set var="unreadMessages" value="${lastMessage.getNumberUnread()}"/>
            <div class="media panel panel-primary
              <c:if test="${unreadMessages>0}"> unread-message</c:if>" style="margin-bottom: 5px">
                <div class="panel-body">
                    <div class="media-left media-top">
                        <c:set var="companionImageId" value="${lastMessage.getCompanion().getPhoto().getId()}"/>
                        <img src="<c:url value="/main/image?imageId=${companionImageId}"/>"
                             class="media-object chat-photo">
                    </div>
                    <div class="media-body">
                        <c:set var="companionName"
                               value="${lastMessage.getCompanion().getFirstName()} ${lastMessage.getCompanion().getLastName()}"/>
                        <h4 class="media-heading"><c:out value="${companionName}"/>
                            <small>
                                <c:set var="preparedDateTime"
                                       value="${fn:replace(lastMessage.getLastMessageTime(), 'T', ' ')}"/>
                                <fmt:parseDate value="${preparedDateTime}" pattern="yyyy-MM-dd HH:mm:ss"
                                               var="parsedDateTime" type="both"/>
                                <i><fmt:formatDate value="${parsedDateTime}" type="both" timeStyle="short"/></i>
                            </small>
                            <c:if test="${unreadMessages>0}"><span class="badge">${unreadMessages}</span></c:if>
                        </h4>

                        <p>
                            <c:if test="${lastMessage.isResponse()}">
                                <c:set var="loggedUserImageId"
                                       value="${lastMessage.getLoggedUser().getPhoto().getId()}"/>
                                <img src="<c:url value="/main/image?imageId=${loggedUserImageId}"/>"
                                     class="chat-photo-response">
                            </c:if>
                            <c:out value=" ${lastMessage.getBody()}"/>
                        </p>
                    </div>
                </div>
            </div>
        </a>
    </c:forEach>
</tags:mainMenu>
