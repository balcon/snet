<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="i18n.view" var="view"/>
<fmt:setBundle basename="i18n.errors" var="errors"/>
<tags:mainMenu>
    <div class="page-header col-md-offset-4">
        <h2><fmt:message bundle="${view}" key="registration.title"/></h2>
    </div>
    <form class="form-horizontal" action="registration" method="post">

        <div class="form-group input-group-sm<c:if test='${validation.containsKey("username")}'> has-error has-feedback </c:if>">
            <fmt:message var="username" bundle="${view}" key="registration.username"/>
            <label class="col-md-3 control-label" for=username>${username}</label>
            <div class="col-md-6">
                <input id="username" type="text" name="username" class="form-control has-error"
                       placeholder="${username}" value="<c:out value="${param.username}"/>">
                <c:if test='${validation.containsKey("username")}'>
                    <span class="glyphicon glyphicon-exclamation-sign  form-control-feedback"></span>
                    <span class="help-block"><small><fmt:message bundle="${errors}"
                                                          key='${validation.get("username")}'/></small></span>
                </c:if>
            </div>
        </div>

        <div class="form-group input-group-sm<c:if test='${validation.containsKey("password")}'> has-error has-feedback </c:if>">
            <fmt:message var="password" bundle="${view}" key="registration.password"/>
            <label class="col-md-3 control-label" for=password>${password}</label>
            <div class="col-md-6">
                <input id="password" type="password" name="password" class="form-control has-error"
                       placeholder="${password}" value="<c:out value="${param.password}"/>">
                <c:if test='${validation.containsKey("password")}'>
                    <span class="glyphicon glyphicon-exclamation-sign  form-control-feedback"></span>
                    <span class="help-block"><small><fmt:message bundle="${errors}"
                                                                 key='${validation.get("password")}'/></small></span>
                </c:if>
            </div>
        </div>

        <div class="form-group input-group-lg<c:if test='${validation.containsKey("firstName")}'> has-error has-feedback </c:if>">
            <fmt:message var="firstName" bundle="${view}" key="registration.firstName"/>
            <label class="col-md-3 control-label" for=firstName>${firstName}
            </label>
            <div class="col-md-6">
                <input id="firstName" type="text" name="firstName" class="form-control has-error"
                       placeholder="${firstName}" value="<c:out value="${param.firstName}"/>">
                <c:if test='${validation.containsKey("firstName")}'>
                    <span class="glyphicon glyphicon-exclamation-sign  form-control-feedback"></span>
                    <span class="help-block"><small><fmt:message bundle="${errors}"
                                                                 key='${validation.get("firstName")}'/></small></span>
                </c:if>
            </div>
        </div>

        <div class="form-group<c:if test='${validation.containsKey("lastName")}'> has-error has-feedback </c:if>">
            <fmt:message var="lastName" bundle="${view}" key="registration.lastName"/>
            <label class="col-md-3 control-label" for=lastName>${lastName}
            </label>
            <div class="col-md-6">
                <input id="lastName" type="text" name="lastName" class="form-control has-error"
                       placeholder="${lastName}" value="<c:out value="${param.lastName}"/>">
                <c:if test='${validation.containsKey("lastName")}'>
                    <span class="glyphicon glyphicon-exclamation-sign  form-control-feedback"></span>
                    <span class="help-block"><small><fmt:message bundle="${errors}"
                                                                 key='${validation.get("lastName")}'/></small></span>
                </c:if>
            </div>
        </div>
        <button type="submit" class="btn btn-primary col-md-offset-4">
            <fmt:message bundle="${view}" key="registration.title"/></button>
    </form>
</tags:mainMenu>

