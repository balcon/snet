<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.view" var="view"/>
<fmt:setBundle basename="i18n.errors" var="errors"/>

<tags:mainMenu active="messages">

    <h2>Chat with</h2>
    <%--TODO: make correct action form with c:url--%>
    <form action="chat" method="post">
        <input type="hidden" name="companionId" value="${companionId}">
        <input type="text" name="body" placeholder="Input text here">
        <input type="submit" value="Send">
    </form>

    <c:if test="${empty messages}">
        There are no messages
    </c:if>
    <c:forEach var="message" items="${messages}">

        <div class="media panel panel-primary <c:if test="${message.isUnread()==true}">
         unread-message</c:if>" style="margin-bottom: 1px; margin-top: 0px">
            <div class="panel-body">
                <div class="media-left">
                    <c:set var="companionImageId" value="${message.getSender().getPhoto().getId()}"/>
                    <img src="<c:url value="/main/image?imageId=${companionImageId}"/>" class="media-object"
                         style="height:70px">
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

                    <p>
                        <c:out value="${message.getBody()}"/></p>
                </div>
            </div>
        </div>
        <c:url var="urlToChat" value="/main/chat"/>
    </c:forEach>
    <c:if test="${numberPages>1}">
        <div class="text-center">
            <ul class="pagination">
                <c:forEach var="page" begin="1" end="${numberPages}">
                    <li <c:if test="${page==activePage}">class="active"</c:if>>
                        <a href="${urlToChat}?companionId=${companionId}&page=${page}">${page}</a></li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
</tags:mainMenu>
