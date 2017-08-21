<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.view" var="view"/>

<tags:mainMenu active="main">
    <jsp:useBean id="main" scope="request" type="com.epam.study.snet.beans.Main"/>
    <div class="row">
        <div class="col-md-3">
            <img src="<c:url value="${main.user.photo.sourcePath}"/>" class="rounded img-thumbnail">
        </div>
        <div class="col-md-9">
            <h3>
                    ${main.user.firstName} ${main.user.lastName}
                <span class="flag-icon flag-icon-${main.user.country.code.toLowerCase()}"></span>
                <c:if test="${main.itself==false}">
                    <a href="<c:url value="/main/chat?companionId=${main.user.id}"/>"
                       class="pull-right btn btn-link btn-lg">
                        <span class="glyphicon glyphicon-envelope"></span></a>
                </c:if>
            </h3>
            <small><fmt:message bundle="${view}" key="user.country"/></small>
            <b>${main.countries.getName(main.user.country)}</b>
            <br>
            <small><fmt:message bundle="${view}" key="user.birthday"/></small>
            <fmt:parseDate value="${main.user.birthday}" pattern="yyyy-MM-dd"
                           var="parsedDate"/>
            <b><fmt:formatDate value="${parsedDate}" dateStyle="long"/></b>
            <small> (${main.user.age} <fmt:message bundle="${view}" key="user.age.years"/>)</small>
            <br>
            <small><fmt:message bundle="${view}" key="user.gender"/></small>
            <b>
                <c:if test='${main.user.gender=="MALE"}'>
                    <fmt:message bundle="${view}" key="user.gender.male"/> </c:if>
                <c:if test='${main.user.gender=="FEMALE"}'>
                    <fmt:message bundle="${view}" key="user.gender.female"/></c:if>
            </b>
            <br>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <c:set var="preparedDateTime" value="${fn:replace(main.user.statusMessage.sendingTime, 'T', ' ')}"/>
                    <fmt:parseDate value="${preparedDateTime}" pattern="yyyy-MM-dd HH:mm:ss"
                                   var="parsedDateTime" type="both"/>
                    <small><b><i><fmt:formatDate value="${parsedDateTime}" type="both" timeStyle="short"/></i></b></small>
                    ${main.user.statusMessage.body}
                </div>
                <c:if test="${main.itself==true}">
                    <div class="panel-footer">
                        <form class="form-horizontal" action="<c:url value="/main"/>" method="post">
                            <div class="form-group col-md-11">
                                <input class="form-control" type="text" name="messageBody"
                                       placeholder="<fmt:message bundle="${view}" key="status.invitiation"/>">
                            </div>
                            <button type="submit" class="btn btn-primary">
                                <span class="glyphicon glyphicon-pencil"></span>
                            </button>
                        </form>
                    </div>
                </c:if>
            </div>


        </div>

    </div>
    <div class="row">
        <div class="col-md-4">
            <ul class="list-group">
                <li class="list-group-item list-group-item-danger">
                    <fmt:message bundle="${view}" key="country.bad_relations"/>
                </li>
                <c:forEach var="country" items="${main.relationManager.getBadRelations(main.user.country)}">
                    <a href="<c:url value="/main/people?country=${country.code}"/>" class="list-group-item">
                        <span class="flag-icon flag-icon-${country.code.toLowerCase()}"></span>
                            ${main.countries.getName(country)}
                    </a>
                </c:forEach>
            </ul>
        </div>
        <div class="col-md-4">
            <ul class="list-group">
                <li class="list-group-item list-group-item-success">
                    <fmt:message bundle="${view}" key="country.good_relations"/>
                </li>
                <c:forEach var="country" items="${main.relationManager.getGoodRelations(main.user.country)}">
                    <a href="<c:url value="/main/people?country=${country.code}"/>" class="list-group-item">
                        <span class="flag-icon flag-icon-${country.code.toLowerCase()}"></span>
                            ${main.countries.getName(country)}
                    </a>
                </c:forEach>
            </ul>
        </div>
        <div class="col-md-4">
            <ul class="list-group">
                <li class="list-group-item list-group-item-info">
                    <fmt:message bundle="${view}" key="peple.compatriots"/>
                </li>
                <c:forEach var="user" items="${main.compatriots}">
                    <a href="<c:url value="/main?id=${user.id}"/>" class="list-group-item">
                            ${user.firstName} ${user.lastName}
                    </a>
                </c:forEach>
            </ul>
        </div>
    </div>

</tags:mainMenu>
