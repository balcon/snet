<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.view" var="view"/>

<jsp:useBean id="chat" scope="request" type="com.epam.study.snet.model.Chat"/>
<jsp:useBean id="loggedUser" scope="session" type="com.epam.study.snet.entity.User"/>

<tags:mainMenu active="messages">

    <div class="col-xs-offset-3">
        <h3>${chat.companion.firstName} ${chat.companion.lastName}</h3>
    </div>
    <c:set var="companionId" value="${chat.companion.id}"/>
    <c:url var="urlToChat" value="/main/chat"/>
    <c:if test="${chat.companion.deleted==false}">
        <form action="${urlToChat}" method="post">
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
    </c:if>
    <c:if test="${empty chat.messages}">
        <div class="alert alert-info">
            <fmt:message bundle="${view}" key="messages.no_messages"/>
        </div>
    </c:if>
    <c:forEach var="message" items="${chat.messages}">

        <div class="media panel panel-primary <c:if test="${message.unread==true}">
         unread-message</c:if>" style="margin-bottom: 1px; margin-top: 0px">
            <div class="panel-body">
                <div class="media-left">
                    <img src="<c:url value="${message.sender.photo.sourcePath}"/>"
                         class="media-object chat-photo">
                </div>
                <div class="media-body">
                    <c:set var="senderFullName"
                           value="${message.sender.firstName} ${message.sender.lastName}"/>
                    <h4 class="media-heading"><c:out value="${senderFullName}"/>
                        <small>
                            <c:set var="preparedDateTime" value="${fn:replace(message.sendingTime, 'T', ' ')}"/>
                            <fmt:parseDate value="${preparedDateTime}" pattern="yyyy-MM-dd HH:mm:ss"
                                           var="parsedDateTime" type="both"/>
                            <i><fmt:formatDate value="${parsedDateTime}" type="both" timeStyle="short"/></i>
                        </small>
                    </h4>
                    <p style="white-space:pre;"><c:out value='${message.body}'/></p>
                </div>
                <c:if test="${message.sender.equals(loggedUser)&&message.unread==true}">
                    <div class="media-right">
                        <form action="${urlToChat}" method="post">
                            <input type="hidden" name="companionId" value="${companionId}">
                            <input type="hidden" name="action" value="remove">
                            <input type="hidden" name="messageId" value="${message.id}">
                            <button type="submit" class="btn btn-link btn-sm"
                                    title="<fmt:message bundle="${view}" key="message.remove"/>">
                                <span class="glyphicon glyphicon-remove"></span>
                            </button>
                        </form>
                    </div>
                </c:if>
            </div>
        </div>
    </c:forEach>
    <c:set var="numberPages" value="${chat.numberPages}"/>
    <c:set var="activePage" value="${chat.activePage}"/>
    <c:if test="${numberPages>1&&activePage!=0}">
        <div class="text-center">
            <ul class="pagination">
                <c:forEach var="page" begin="1" end="${numberPages}">
                    <li <c:if test="${page==activePage}">class="active"</c:if>>
                        <a href="${urlToChat}?companionId=${companionId}&page=${page}">${page}</a></li>
                </c:forEach>
                <li><a href="${urlToChat}?companionId=${companionId}&page=0">
                    <fmt:message bundle="${view}" key="pagination.show_all"/>
                </a></li>
            </ul>
        </div>
    </c:if>
</tags:mainMenu>
