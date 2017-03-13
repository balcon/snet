<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="i18n.view" var="view"/>
<fmt:setBundle basename="i18n.errors" var="errors"/>
<tags:mainMenu>
    <div class="page-header col-md-offset-4">
        <h2><fmt:message bundle="${view}" key="login.title"/></h2>
    </div>
    <form class="form-horizontal <c:if test='${validation.containsKey("loginForm")}'> has-error </c:if>" action="login"
          method="post">
        <c:if test='${validation.containsKey("loginForm")}'>
            <div class="alert alert-danger" role="alert">
                <fmt:message bundle="${errors}" key='${validation.get("loginForm")}'/>
            </div>
        </c:if>

        <div class="form-group input-group-sm<c:if test='${validation.containsKey("username")}'> has-error has-feedback </c:if>">
            <fmt:message var="username" bundle="${view}" key="login.username"/>
            <label class="col-md-3 control-label" for=username>${username}</label>
            <div class="col-md-6">
                <input id="username" type="text" name="username" class="form-control has-error"
                       placeholder="${username}" value="<c:out value="${param.username}"/>">
                <c:if test='${validation.containsKey("username")}'>
                    <span class="glyphicon glyphicon-exclamation-sign form-control-feedback"></span>
                    <span class="help-block"><small><fmt:message bundle="${errors}"
                                                                 key='${validation.get("username")}'/></small></span>
                </c:if>
            </div>
        </div>

        <div class="form-group input-group-sm<c:if test='${validation.containsKey("password")}'> has-error has-feedback </c:if>">
            <fmt:message var="password" bundle="${view}" key="login.password"/>
            <label class="col-md-3 control-label" for=password>${password}</label>
            <div class="col-md-6">
                <input id="password" type="password" name="password" class="form-control has-error"
                       placeholder="${password}" value="<c:out value="${param.password}"/>">
                <c:if test='${validation.containsKey("password")}'>
                    <span class="glyphicon glyphicon-exclamation-sign form-control-feedback"></span>
                    <span class="help-block"><small><fmt:message bundle="${errors}"
                                                                 key='${validation.get("password")}'/></small></span>
                </c:if>
            </div>
        </div>
        <div class="col-md-offset-4">
        <button type="submit" class="btn btn-primary">
            <fmt:message bundle="${view}" key="login.title"/></button>
            <a href="<c:url value="/registration"/>" class="btn btn-link">
                <fmt:message bundle="${view}" key="registration.title"/></a>
        </div>
    </form>
</tags:mainMenu>