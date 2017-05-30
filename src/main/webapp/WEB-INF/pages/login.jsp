<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.view" var="view"/>
<fmt:setBundle basename="i18n.errors" var="errors"/>

<tags:mainMenu>
    <div class="page-header col-md-offset-4">
        <h3><fmt:message bundle="${view}" key="titles.login"/></h3>
    </div>
    <jsp:useBean id="formValidation" scope="request" class="com.epam.study.snet.model.FormValidation"/>
    <form class="form-horizontal<c:if test='${formValidation.errors.containsKey("loginForm")}'> has-error </c:if>"
          action="login" method="post">
        <c:if test='${formValidation.errors.containsKey("loginForm")}'>
            <div class="alert alert-danger" role="alert">
                <fmt:message bundle="${errors}" key='${formValidation.errors.get("loginForm")}'/>
            </div>
        </c:if>
        <tags:typicalInput inputType="text"
                           name="username"
                           labelProp="user.username"
                           initValue="user1"
                           errors="${formValidation.errors}"
                           inline="true"/>
            <%--setupValue="${param.username}"--%>

        <tags:typicalInput inputType="password"
                           name="password"
                           labelProp="user.password"
                           initValue="123456"
                           errors="${formValidation.errors}"
                           inline="true"/>

        <div class="col-md-offset-4">
            <button type="submit" class="btn btn-primary">
                <fmt:message bundle="${view}" key="titles.login"/></button>
            <a href="<c:url value="/registration"/>" class="btn btn-link">
                <fmt:message bundle="${view}" key="titles.registration"/></a>
        </div>
    </form>
</tags:mainMenu>