<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.view" var="view"/>

<tags:mainMenu active="people">
    <c:forEach var="user" items="${users}">

        <div class="media panel panel-primary" style="margin-bottom: 1px; margin-top: 0px">
            <div class="panel-body">
                <div class="media-left">
                    <c:set var="imageId" value="${user.getPhoto().getId()}"/>
                    <img src="<c:url value="/main/image?imageId=${imageId}"/>" class="media-object list-photo">
                </div>
                <div class="media-body">
                    <c:set var="name"
                           value="${user.getFirstName()} ${user.getLastName()}"/>
                    <h4 class="media-heading"><c:out value="${name}"/></h4>
                </div>
            </div>
        </div>
    </c:forEach>
</tags:mainMenu>