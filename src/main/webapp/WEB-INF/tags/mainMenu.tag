<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="active" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="<c:url value="/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/webjars/bootstrap-fileinput/4.3.8/css/fileinput.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/webjars/bootstrap/3.3.7-1/css/bootstrap-theme.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/webjars/flag-icon-css/2.4.0/css/flag-icon.min.css"/>"/>
    <script type="text/javascript" src="<c:url value="/webjars/bootstrap/3.3.7-1/js/bootstrap.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/webjars/jquery/1.11.1/jquery.min.js"/> "></script>
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

        div.media.unread-message {
            font-weight: 700;
            border-width: 3px;
        }

        img.chat-photo {
            height: 72px;
            width: 54px;
        }

        img.chat-photo-response {
            height: 32px;
            width: 24px;
        }

        img.list-photo {
            height: 88px;
            width: 66px;
        }


    </style>
    <title>SNet</title>
</head>
<body>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.view" var="view"/>
<nav class="navbar navbar-inverse" style="margin-bottom: 0px">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="<c:url value="/main"/>">
                <fmt:message bundle="${view}" key="header.title"/>
                <c:if test="${sessionScope.loggedUser!=null}">
                    <small>
                        <fmt:message bundle="${view}" key="header.registered">
                            <c:set var="regUsers" value="${sessionScope.registeredUsers}"/>
                            <fmt:param value='<span class="badge">${regUsers}</span>'/>
                        </fmt:message>
                    </small>
                </c:if>
            </a>
        </div>
        <c:url var="locale" value="/locale"/>
        <c:set var="currentPage" value="${requestScope['javax.servlet.forward.request_uri']}"/>
        <c:set var="queryString" value="${requestScope['javax.servlet.forward.query_string']}"/>
        <ul class="nav navbar-nav navbar-right ">
            <li>
                <form action="${locale}" method="post" class="navbar-form  navbar-right ">
                    <input type="hidden" name="locale" value="en_US">
                    <input type="hidden" name="currentPage" value="${currentPage}">
                    <input type="hidden" name="queryString" value="${queryString}">
                    <button class="btn locale-btn
            <c:if test='${sessionScope.locale=="en_US"}'> locale-btn-active </c:if>
            flag-icon flag-icon-us" type="submit"></button>
                </form>
            </li>
            <li>
                <form action="${locale}" method="post" class="navbar-form  navbar-right ">
                    <input type="hidden" name="locale" value="ru_RU">
                    <input type="hidden" name="currentPage" value="${currentPage}">
                    <input type="hidden" name="queryString" value="${queryString}">
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
                        <fmt:message bundle="${view}" key="titles.main"/></a></li>
                <li <c:if test='${active=="messages"}'>class="active"</c:if>>
                    <a href="<c:url value="/main/messages"/>"><span class="glyphicon glyphicon-envelope"></span>
                        <fmt:message bundle="${view}" key="titles.messages"/>
                        <c:if test="${sessionScope.unreadMessages > 0}">
                            <span class="badge">${sessionScope.unreadMessages}</span></c:if>
                    </a></li>
                <li <c:if test='${active=="people"}'>class="active"</c:if>>
                    <a href="<c:url value="/main/people"/>"><span class="glyphicon glyphicon-user"></span>
                        <fmt:message bundle="${view}" key="titles.users"/></a></li>
                <li <c:if test='${active=="calendar"}'>class="active"</c:if>>
                    <a href="<c:url value="/main/calendar"/>"><span class="glyphicon glyphicon-th"></span>
                        <fmt:message bundle="${view}" key="titles.calendar"/></a></li>
                <li <c:if test='${active=="profile"}'>class="active"</c:if>>
                    <a href="<c:url value="/main/profile"/>"><span class="glyphicon glyphicon-cog"></span>
                        <fmt:message bundle="${view}" key="titles.profile"/></a></li>
                <li><a href="<c:url value="/logout"/>"><span class="glyphicon glyphicon-log-out"></span>
                    <fmt:message bundle="${view}" key="menu.logout"/></a></li>
            </ul>
        </div>
        <div class="col-md-9 col-xs-9 well" style="min-height: 290px">
            <jsp:doBody/>
        </div>
    </div>
</div>
</body>
</html>
