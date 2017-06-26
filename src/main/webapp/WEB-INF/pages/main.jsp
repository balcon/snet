<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.view" var="view"/>

<tags:mainMenu active="main">
    <jsp:useBean id="loggedUser" scope="session" type="com.epam.study.snet.entity.User"/>
    <div class="row">
        <div class="col-md-3">
            <img src="<c:url value="${loggedUser.photo.sourcePath}"/>" class="rounded img-thumbnail">
        </div>
        <jsp:useBean id="countries" scope="request" type="com.epam.study.snet.model.Countries"/>

        <div class="col-md-5">
            <h3>
                <span class="flag-icon flag-icon-${loggedUser.country.code.toLowerCase()}"></span>
                    ${loggedUser.firstName} ${loggedUser.lastName}
                <small>
                        (${loggedUser.age} <fmt:message bundle="${view}" key="user.age.years"/>)
                </small>
            </h3>
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
                <c:forEach var="country" items="${loggedUser.country.badRelations}">
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
                <c:forEach var="country" items="${loggedUser.country.goodRelations}">
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
                    <a href="<c:url value="/main/chat?companionId=${user.id}"/>" class="list-group-item">
                            ${user.firstName} ${user.lastName}
                    </a>
                </c:forEach>
            </ul>
        </div>
    </div>

</tags:mainMenu>
