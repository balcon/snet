<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<tags:mainMenu active="messages">
    <h2>Chat with</h2>

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
                            <i>
                                    ${message.getSendingTime()}
                            </i>
                                <%--<i><fmt:formatDate value="${messages.getSendingTime()}" type="both"--%>
                                <%--timeStyle="short"/></i>--%>
                        </small>
                    </h4>

                    <p>
                        <c:out value="${message.getBody()}"/></p>
                </div>
            </div>
        </div>
    </c:forEach>
        <ul class="pagination">
            <c:forEach var="page" begin="1" end="${numberOfPages}">
                <li><a href="#">${page}</a></li>
            </c:forEach>
        </ul>
    ${requestScope.trueNumber}
</tags:mainMenu>
