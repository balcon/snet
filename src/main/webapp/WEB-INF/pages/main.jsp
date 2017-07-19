<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.view" var="view"/>

<tags:mainMenu active="main">
    <jsp:useBean id="user" scope="request" type="com.epam.study.snet.entity.User"/>
    <div class="row">
        <div class="col-md-3">
            <img src="<c:url value="${user.photo.sourcePath}"/>" class="rounded img-thumbnail">
        </div>
        <jsp:useBean id="countries" scope="request" type="com.epam.study.snet.model.Countries"/>

        <div class="col-md-9">
            <h3>
                    ${user.firstName} ${user.lastName}
                <span class="flag-icon flag-icon-${user.country.code.toLowerCase()}"></span>
                <a href="<c:url value="/main/chat?companionId=${user.id}"/>" class="pull-right btn btn-link btn-lg">
                    <span class="glyphicon glyphicon-envelope"></span></a>
            </h3>
            <small><fmt:message bundle="${view}" key="user.country"/></small>
            <b>${countries.getName(user.country)}</b>
            <br>
            <small><fmt:message bundle="${view}" key="user.birthday"/></small>
            <fmt:parseDate value="${user.birthday}" pattern="yyyy-MM-dd"
                           var="parsedDate"/>
            <b><fmt:formatDate value="${parsedDate}" dateStyle="long"/></b>
            <small> (${user.age} <fmt:message bundle="${view}" key="user.age.years"/>)</small>
            <br>
            <small><fmt:message bundle="${view}" key="user.gender"/></small>
            <b>
                <c:if test='${user.gender=="MALE"}'>
                    <fmt:message bundle="${view}" key="user.gender.male"/> </c:if>
                <c:if test='${user.gender=="FEMALE"}'>
                    <fmt:message bundle="${view}" key="user.gender.female"/></c:if>
            </b>
            <br>
            <div class="panel panel-default">
                <div class="panel-heading"><b>15:35:00 26.12.2017:</b> azaza</div>
            </div>
            <form action="<c:url value="/main"/>" method="post">
                <input type="text" name="messageBody">
                <input type="submit" value="S">
            </form>
        </div>

    </div>
    <div class="row">
        <div class="col-md-4">
            <ul class="list-group">
                <li class="list-group-item list-group-item-danger">
                    <fmt:message bundle="${view}" key="country.bad_relations"/>
                </li>
                <c:forEach var="country" items="${user.country.badRelations}">
                    <a href="<c:url value="/main/people?country=${country.code}"/>" class="list-group-item">
                        <span class="flag-icon flag-icon-${country.code.toLowerCase()}"></span>
                            ${countries.getName(country)}
                    </a>
                </c:forEach>
            </ul>
        </div>
        <div class="col-md-4">
            <ul class="list-group">
                <li class="list-group-item list-group-item-success">
                    <fmt:message bundle="${view}" key="country.good_relations"/>
                </li>
                <c:forEach var="country" items="${user.country.goodRelations}">
                    <a href="<c:url value="/main/people?country=${country.code}"/>" class="list-group-item">
                        <span class="flag-icon flag-icon-${country.code.toLowerCase()}"></span>
                            ${countries.getName(country)}
                    </a>
                </c:forEach>
            </ul>
        </div>
        <div class="col-md-4">
            <ul class="list-group">
                <li class="list-group-item list-group-item-info">
                    <fmt:message bundle="${view}" key="peple.compatriots"/>
                </li>
                <jsp:useBean id="friends" scope="request" type="java.util.List<com.epam.study.snet.entity.User>"/>
                <c:forEach var="user" items="${friends}">
                    <a href="<c:url value="/main?id=${user.id}"/>" class="list-group-item">
                            ${user.firstName} ${user.lastName}
                    </a>
                </c:forEach>
            </ul>
        </div>
    </div>

</tags:mainMenu>
