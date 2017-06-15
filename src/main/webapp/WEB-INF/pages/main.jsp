<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:mainMenu active="main">
    <jsp:useBean id="loggedUser" scope="session" type="com.epam.study.snet.entity.User"/>
    <div class="col-md-3">
        <img src="<c:url value="${loggedUser.photo.sourcePath}"/>" class="rounded img-thumbnail">
    </div>
    <jsp:useBean id="countries" scope="request" type="com.epam.study.snet.model.Countries"/>
    <div class="col-md-5">
        <ul class="list-group">
            <c:forEach var="country" items="${loggedUser.country.badRelations}">
                <li class="list-group-item">
                    <span class="flag-icon flag-icon-${country.code.toLowerCase()}"></span>
                    ${countries.getName(country)}
                </li>
            </c:forEach>
        </ul>
    </div>

</tags:mainMenu>
