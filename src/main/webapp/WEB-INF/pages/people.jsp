<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.view" var="view"/>

<jsp:useBean id="people" scope="request" type="com.epam.study.snet.view.People"/>

<tags:mainMenu active="people">
    <div class="col-xs-offset-4">
        <h3>
            <fmt:message bundle="${view}" key="titles.users"/>
        </h3>
    </div>
    <jsp:useBean id="countries" scope="request" type="com.epam.study.snet.controller.services.Countries"/>
    <jsp:useBean id="relationManager" scope="request" type="com.epam.study.snet.controller.services.RelationManager"/>
    <div class="dropdown text-right">
        <button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown">
            <fmt:message bundle="${view}" key="people.country_filter"/>
            <span class="caret"></span>
        </button>
        <ul class="dropdown-menu dropdown-menu-right">
            <li>
                <a href='<c:url value="/main/people"/>'>
                    <fmt:message bundle="${view}" key="pagination.show_all"/>
                </a>
            </li>
            <li class="divider"></li>
            <c:forEach var="oneCountry" items="${people.countries}">
                <li>
                    <a href="?country=${oneCountry.code}">
                            ${countries.getName(oneCountry)}
                        <span class="flag-icon flag-icon-${oneCountry.code.toLowerCase()} pull-right"></span>

                    </a>
                </li>
            </c:forEach>
        </ul>
    </div>

    <c:if test="${empty people.users}">
        <div class="alert alert-info">
            <fmt:message bundle="${view}" key="people.no_people"/>
        </div>
    </c:if>
    <c:forEach var="user" items="${people.users}">
        <c:set var="relation" value="${relationManager.checkRelation(sessionScope.loggedUser.country,user.country)}"/>
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
                    <a href="<c:url value="/main?id=${user.id}"/>" style="text-decoration: none;">
                        <h4 class="media-heading"><c:out value="${fullName}"/>,
                            <small>${countries.getName(user.country)}
                                <span class="flag-icon flag-icon-${user.country.code.toLowerCase()}"></span>
                            </small>
                        </h4>
                    </a>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <c:set var="preparedDateTime" value="${fn:replace(user.statusMessage.sendingTime, 'T', ' ')}"/>
                            <fmt:parseDate value="${preparedDateTime}" pattern="yyyy-MM-dd HH:mm:ss"
                                           var="parsedDateTime" type="both"/>
                            <small><b><i><fmt:formatDate value="${parsedDateTime}" type="both" timeStyle="short"/></i></b></small>
                                ${user.statusMessage.body}
                        </div>
                    </div>
                </div>
                <div class="media-right">
                    <a href="<c:url value="/main/chat?companionId=${user.id}"/>" class="btn btn-link btn-lg">
                        <span class="glyphicon glyphicon-envelope"></span></a>
                </div>
            </div>
        </div>
    </c:forEach>
    <c:url var="urlToPeople" value="/main/people"/>
    <c:set var="numberPages" value="${people.numberPages}"/>
    <c:set var="activePage" value="${people.activePage}"/>
    <c:set var="country" value="${param.country}"/>
    <c:if test="${!empty country}">
        <c:set var="country" value="&country=${param.country}"/>
    </c:if>

    <c:if test="${numberPages>1&&activePage!=0}">
        <div class="text-center">
            <ul class="pagination">
                <c:forEach var="page" begin="1" end="${numberPages}">
                    <li <c:if test="${page==activePage}">class="active"</c:if>>
                        <a href="${urlToPeople}?page=${page}${country}">${page}</a></li>
                </c:forEach>
                <li><a href="${urlToPeople}?page=0">
                    <fmt:message bundle="${view}" key="pagination.show_all"/>
                </a></li>
            </ul>
        </div>
    </c:if>
</tags:mainMenu>