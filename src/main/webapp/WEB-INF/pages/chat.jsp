<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.view" var="view"/>

<tags:mainMenu active="messages">

    <div class="col-md-offset-4">
        <h3>${companionName}</h3>
    </div>
    <c:url var="chat" value="/main/chat"/>
    <form action="${chat}" method="post">
        <input type="hidden" name="companionId" value="${companionId}">
        <div class="form-group" style="margin-bottom: 10px">
            <div class="input-group">
            <textarea style="resize: vertical;" name="body" class="form-control  col-xs-12" rows="2"
                      placeholder="<fmt:message bundle="${view}" key="chat.invitiation"/>"></textarea>
                <div class="input-group-btn">
                    <button type="submit" class="btn btn-primary btn-lg">
                        <span class="glyphicon glyphicon-send"></span>
                    </button>
                </div>
            </div>
        </div>
    </form>
    <c:if test="${empty messages}">
        <br>
        <div class="alert alert-info">
            <fmt:message bundle="${view}" key="messages.no_messages"/>
        </div>
    </c:if>
    <c:forEach var="message" items="${messages}">

        <div class="media panel panel-primary <c:if test="${message.isUnread()==true}">
         unread-message</c:if>" style="margin-bottom: 1px; margin-top: 0px">
            <div class="panel-body">
                <div class="media-left">
                    <c:set var="companionImageId" value="${message.getSender().getPhoto().getId()}"/>
                    <img src="<c:url value="/main/image?imageId=${companionImageId}"/>" class="media-object chat-photo">
                </div>
                <div class="media-body">
                    <c:set var="senderName"
                           value="${message.getSender().getFirstName()} ${message.getSender().getLastName()}"/>
                    <h4 class="media-heading"><c:out value="${senderName}"/>
                        <small>
                            <c:set var="preparedDateTime" value="${fn:replace(message.getSendingTime(), 'T', ' ')}"/>
                            <fmt:parseDate value="${preparedDateTime}" pattern="yyyy-MM-dd HH:mm:ss"
                                           var="parsedDateTime" type="both"/>
                            <i><fmt:formatDate value="${parsedDateTime}" type="both" timeStyle="short"/></i>
                        </small>
                    </h4>
                    <p style="white-space:pre;"><c:out value='${message.getBody()}'/></p>
                </div>
            </div>
        </div>
    </c:forEach>
    <c:url var="urlToChat" value="/main/chat"/>
    <c:if test="${numberPages>1&&activePage!=0}">
        <div class="text-center">
            <ul class="pagination">
                <c:forEach var="page" begin="1" end="${numberPages}">
                    <li <c:if test="${page==activePage}">class="active"</c:if>>
                        <a href="${urlToChat}?companionId=${companionId}&page=${page}">${page}</a></li>
                </c:forEach>
                <li><a href="${urlToChat}?companionId=${companionId}&page=0">
                    <fmt:message bundle="${view}" key="messages.show_all"/>

                </a></li>
            </ul>
        </div>
    </c:if>
</tags:mainMenu>
