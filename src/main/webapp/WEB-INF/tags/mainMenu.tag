<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="active" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.view" var="view"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="<c:url value="/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/webjars/bootstrap/3.3.7-1/css/bootstrap-theme.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/webjars/flag-icon-css/2.4.0/css/flag-icon.min.css"/>"/>
    <script src="<c:url value="/webjars/bootstrap/3.3.7-1/js/bootstrap.js"/>"></script>
    <meta charset="utf-8">
    <style>
        body {
            margin-top: 10px;
            background-color: darkgray;
        }

        button.locale-btn {
            background-color: #333;
        }

        button.locale-btn-active {
            border-color: lightgray;
        }

    </style>
    <title>SNetwork</title>
</head>
<body>

<nav class="navbar navbar-inverse" style="margin-bottom: 0px">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">WebSiteName</a>
        </div>
        <c:url var="locale" value="/locale"/>
        <c:set var="currentPage" value="${requestScope['javax.servlet.forward.request_uri']}"/>
        <ul class="nav navbar-nav navbar-right ">
            <li>
                <form action="${locale}" method="post" class="navbar-form  navbar-right ">
                    <input type="hidden" name="locale" value="en_US">
                    <input type="hidden" name="currentPage" value="${currentPage}">
                    <button class="btn locale-btn
            <c:if test='${sessionScope.locale=="en_US"}'> locale-btn-active </c:if>
            flag-icon flag-icon-us" type="submit"></button>
                </form>
            </li>
            <li>
                <form action="${locale}" method="post" class="navbar-form  navbar-right ">
                    <input type="hidden" name="locale" value="ru_RU">
                    <input type="hidden" name="currentPage" value="${currentPage}">
                    <button class="btn locale-btn
            <c:if test='${sessionScope.locale=="ru_RU"}'> locale-btn-active </c:if>flag-icon flag-icon-ru"
                            type="submit"></button>
                </form>
            </li>
        </ul>
    </div>
</nav>
<div class="container">
    <div class="row">
        <div class="col-md-3 col-xs-3 well">
            <ul class="nav nav-pills nav-stacked">
                <li <c:if test='${active=="main"}'>class="active"</c:if>>
                    <a href="<c:url value="/main"/>"><span class="glyphicon glyphicon-home"></span>
                        <fmt:message bundle="${view}" key="menu.main"/></a></li>
                <li <c:if test='${active=="messages"}'>class="active"</c:if>>
                    <a href="<c:url value="/main/messages"/>"><span class="glyphicon glyphicon-envelope"></span>
                        <fmt:message bundle="${view}" key="menu.messages"/></a></li>
                <li <c:if test='${active=="people"}'>class="active"</c:if>>
                    <a href="<c:url value="/main/people"/>"><span class="glyphicon glyphicon-user"></span>
                        <fmt:message bundle="${view}" key="menu.people"/></a></li>
                <li <c:if test='${active=="calendar"}'>class="active"</c:if>>
                    <a href="<c:url value="/main/calendar"/>"><span class="glyphicon glyphicon-th"></span>
                        <fmt:message bundle="${view}" key="menu.calendar"/></a></li>
                <li <c:if test='${active=="profile"}'>class="active"</c:if>>
                    <a href="<c:url value="/main/profile"/>"><span class="glyphicon glyphicon-cog"></span>
                        <fmt:message bundle="${view}" key="menu.profile"/></a></li>
                <li><a href="<c:url value="/logout"/>"><span class="glyphicon glyphicon-off"></span>
                    <fmt:message bundle="${view}" key="menu.logout"/></a></li>
            </ul>
        </div>
        <div class="col-md-9 col-xs-9 well">
            <jsp:doBody/>
        </div>
    </div>
</div>
</body>
</html>

<%--<c:if test="${not empty sessionScope.user.getUsername()}">--%>
<%--<td>Logged as: <b><c:out value="${sessionScope.user.getFirstName()}"/> <c:out value="${sessionScope.user.getLastName()}"/>(ID: ${sessionScope.user.getId()})</b></td>--%>
<%--</c:if>--%>