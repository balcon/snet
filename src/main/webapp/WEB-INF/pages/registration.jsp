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
            <%-- USERNAME input --%>
        <div class="form-group input-group-sm<c:if test='${validation.containsKey("username")}'> has-error has-feedback </c:if>">
            <fmt:message var="username" bundle="${view}" key="registration.username"/>
            <label class="col-md-3 control-label" for=username>${username}</label>
            <div class="col-md-6">
                <input id="username" type="text" name="username" class="form-control"
                       placeholder="${username}" value="<c:out value="${param.username}"/>">
                <c:if test='${validation.containsKey("username")}'>
                    <span class="glyphicon glyphicon-exclamation-sign  form-control-feedback"></span>
                    <span class="help-block"><small><fmt:message bundle="${errors}"
                                                                 key='${validation.get("username")}'/></small></span>
                </c:if>
            </div>
        </div>
            <%-- end of USERNAME input --%>
            <%-- PASSWORD input --%>
        <div class="form-group input-group-sm<c:if test='${validation.containsKey("password")}'> has-error has-feedback </c:if>">
            <fmt:message var="password" bundle="${view}" key="registration.password"/>
            <label class="col-md-3 control-label" for=password>${password}</label>
            <div class="col-md-6">
                <input id="password" type="password" name="password" class="form-control"
                       placeholder="${password}" value="<c:out value="${param.password}"/>">
                <c:if test='${validation.containsKey("password")}'>
                    <span class="glyphicon glyphicon-exclamation-sign  form-control-feedback"></span>
                    <span class="help-block"><small><fmt:message bundle="${errors}"
                                                                 key='${validation.get("password")}'/></small></span>
                </c:if>
            </div>
        </div>
            <%-- end of PASSWORD input --%>
            <%-- CONFIRM PASSWORD input --%>
        <div class="form-group input-group-sm<c:if test='${validation.containsKey("confirdPassword")}'> has-error has-feedback </c:if>">
            <fmt:message var="confirdPassword" bundle="${view}" key="registration.confirdPassword"/>
            <label class="col-md-3 control-label" for=confirdPassword>${confirdPassword}</label>
            <div class="col-md-6">
                <input id="confirdPassword" type="password" name="confirmPassword" class="form-control"
                       placeholder="${confirdPassword}" value="<c:out value="${param.confirdPassword}"/>">
                <c:if test='${validation.containsKey("confirdPassword")}'>
                    <span class="glyphicon glyphicon-exclamation-sign  form-control-feedback"></span>
                    <span class="help-block"><small><fmt:message bundle="${errors}"
                                                                 key='${validation.get("confirdPassword")}'/></small></span>
                </c:if>
            </div>
        </div>
            <%-- end of CONFIRM PASSWORD input --%>

            <%-- FIRSTNAME input --%>
        <div class="form-group input-group-lg<c:if test='${validation.containsKey("firstName")}'> has-error has-feedback </c:if>">
            <fmt:message var="firstName" bundle="${view}" key="registration.firstName"/>
            <label class="col-md-3 control-label" for=firstName>${firstName}
            </label>
            <div class="col-md-6">
                <input id="firstName" type="text" name="firstName" class="form-control"
                       placeholder="${firstName}" value="<c:out value="${param.firstName}"/>">
                <c:if test='${validation.containsKey("firstName")}'>
                    <span class="glyphicon glyphicon-exclamation-sign  form-control-feedback"></span>
                    <span class="help-block"><small><fmt:message bundle="${errors}"
                                                                 key='${validation.get("firstName")}'/></small></span>
                </c:if>
            </div>
        </div>
            <%-- end of FIRSTNAME input --%>
            <%-- LASTNAME input --%>
        <div class="form-group<c:if test='${validation.containsKey("lastName")}'> has-error has-feedback </c:if>">
            <fmt:message var="lastName" bundle="${view}" key="registration.lastName"/>
            <label class="col-md-3 control-label" for=lastName>${lastName}
            </label>
            <div class="col-md-6">
                <input id="lastName" type="text" name="lastName" class="form-control"
                       placeholder="${lastName}" value="<c:out value="${param.lastName}"/>">
                <c:if test='${validation.containsKey("lastName")}'>
                    <span class="glyphicon glyphicon-exclamation-sign  form-control-feedback"></span>
                    <span class="help-block"><small><fmt:message bundle="${errors}"
                                                                 key='${validation.get("lastName")}'/></small></span>
                </c:if>
            </div>
        </div>
            <%--end of LASTNAME input--%>
            <%-- GENDER INPUT --%>
        <div class="form-group <c:if test='${validation.containsKey("gender")}'> has-error has-feedback </c:if>">
            <fmt:message var="gender" bundle="${view}" key="registration.gender"/>
            <label class="col-md-3 control-label" for="gender">${gender}
            </label>
            <div class="col-md-6">
                <select class="form-control" id="gender" name="gender" placeholder="koko">
                    <option value="" hidden>${gender}</option>
                    <option value="male"
                            <c:if test='${param.gender=="male"}'>selected</c:if>><fmt:message bundle="${view}"
                                                                                              key="registration.gender.male"/></option>
                    <option value="female"
                            <c:if test='${param.gender=="female"}'>selected</c:if>><fmt:message bundle="${view}"
                                                                                                key="registration.gender.female"/></option>
                </select>
                <c:if test='${validation.containsKey("gender")}'>
                    <span class="glyphicon glyphicon-exclamation-sign form-control-feedback"></span>
                    <span class="help-block">
                        <small><fmt:message bundle="${errors}" key='${validation.get("gender")}'/></small>
                    </span>
                </c:if>
            </div>
        </div>
            <%-- end of GENDER input --%>
        <button type="submit" class="btn btn-primary col-md-offset-4">
            <fmt:message bundle="${view}" key="registration.title"/></button>
    </form>
</tags:mainMenu>

