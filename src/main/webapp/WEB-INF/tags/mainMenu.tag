<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.view" var="view"/>
<%@ attribute name="active" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="<c:url value="/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/webjars/bootstrap/3.3.7-1/css/bootstrap-theme.min.css"/>">
    <script src="<c:url value="/webjars/bootstrap/3.3.7-1/js/bootstrap.js"/>"></script>
    <meta charset="utf-8">
    <style>
        body {
            margin-top: 20px;
        }
    </style>
    <title>SNetwork</title>
</head>
<body>
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