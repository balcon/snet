<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.view" var="view"/>
<fmt:setBundle basename="i18n.errors" var="errors"/>


<tags:mainMenu active="messages">
    <%--<div class="page-header col-md-offset-4">--%>
    <%--<h3><fmt:message bundle="${view}" key="titles.messages"/></h3>--%>
    <%--</div>--%>
    <h3>Messages</h3>
    <c:if test="${empty messages}">
        There are no messages
    </c:if>
        <c:url var="urlToChat" value="/main/chat"/>

        <c:forEach var="lastMessage" items="${lastMessages}">
            <a href="${urlToChat}?companionId=${lastMessage.getCompanion().getId()}" style="text-decoration: none;">
              <div class="media panel panel-primary" style="margin-bottom: 5px">
                    <div class="panel-body">
                        <div class="media-left media-top">
                            <c:set var="companionImageId" value="${lastMessage.getCompanion().getPhoto().getId()}"/>
                            <img src="<c:url value="/main/image?imageId=${companionImageId}"/>" class="media-object"
                                 style="height:70px">
                        </div>
                        <div class="media-body">
                            <c:set var="companionName"
                                   value="${lastMessage.getCompanion().getFirstName()} ${lastMessage.getCompanion().getLastName()}"/>
                            <h4 class="media-heading"><c:out value="${companionName}"/>
                                <small>
                                    <i><fmt:formatDate value="${lastMessage.getLastMessageTime()}" type="both"
                                                       timeStyle="short"/></i>
                                </small>
                                <span class="badge">4</span>
                            </h4>

                            <p><c:if test="${lastMessage.isResponse()}">
                                <c:set var="currentUserImageId"
                                       value="${lastMessage.getLoggedUser().getPhoto().getId()}"/>
                                <img src="<c:url value="/main/image?imageId=${currentUserImageId}"/>"
                                     style="height:30px"></c:if>
                                <c:out value=" ${lastMessage.getBody()}"/></p>
                        </div>
                    </div>
              </div>
            </a>
        </c:forEach>
</tags:mainMenu>
