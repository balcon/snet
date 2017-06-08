<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.view" var="view"/>

<jsp:useBean id="people" scope="request" type="com.epam.study.snet.model.People"/>

<tags:mainMenu active="people">
    <c:if test="${empty people.users}">
        <div class="alert alert-info">
            <fmt:message bundle="${view}" key="people.no_people"/>
        </div>
    </c:if>

    <jsp:useBean id="countries" scope="request" type="com.epam.study.snet.model.Countries"/>
    <c:forEach var="user" items="${people.users}">
        <c:set var="relation" value="${user.checkRelation(sessionScope.loggedUser)}"/>
        <c:set var="relationStyle"/>
        <c:if test='${relation=="BAD"}'>
            <c:set var="relationStyle" value="relation-bad"/>
        </c:if>
         <c:if test='${relation=="GOOD"}'>
            <c:set var="relationStyle" value="relation-good"/>
        </c:if>
         <c:if test='${relation=="SAME"}'>
            <c:set var="relationStyle" value="relation-same"/>
        </c:if>

        <div class="media panel panel-primary ${relationStyle}" style="margin-bottom: 1px; margin-top: 0px">
            <div class="panel-body">
                <div class="media-left">
                    <img src="<c:url value="${user.photo.sourcePath}"/>" class="media-object list-photo">
                </div>
                <div class="media-body">
                    <c:set var="fullName"
                           value="${user.firstName} ${user.lastName}"/>
                    <h4 class="media-heading"><c:out value="${fullName}"/>,
                        <small>${countries.getName(user.country)}
                            <span class="flag-icon flag-icon-${user.country.code.toLowerCase()}"></span>
                        </small>
                    </h4>
                </div>
                <div class="media-right">
                    <c:url var="urlToChat" value="/main/chat"/>
                    <a href="${urlToChat}?companionId=${user.id}" style="text-decoration: none;">Chat</a>
                </div>
            </div>
        </div>
    </c:forEach>
    <c:url var="urlToPeople" value="/main/people"/>
    <c:set var="numberPages" value="${people.numberPages}"/>
    <c:set var="activePage" value="${people.activePage}"/>
    <c:if test="${numberPages>1&&activePage!=0}">
        <div class="text-center">
            <ul class="pagination">
                <c:forEach var="page" begin="1" end="${numberPages}">
                    <li <c:if test="${page==activePage}">class="active"</c:if>>
                        <a href="${urlToPeople}?page=${page}">${page}</a></li>
                </c:forEach>
                <li><a href="${urlToPeople}?page=0">
                    <fmt:message bundle="${view}" key="pagination.show_all"/>
                </a></li>
            </ul>
        </div>
    </c:if>
</tags:mainMenu>