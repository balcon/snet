<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.view" var="view"/>
<fmt:setBundle basename="i18n.errors" var="errors"/>

<tags:mainMenu>
    <div class="page-header col-md-offset-4">
        <h3><fmt:message bundle="${view}" key="titles.registration"/></h3>
    </div>
    <form class="form-horizontal" action="registration" method="post">
        <tags:typicalInput type="text"
                           name="username"
                           labelProp="user.username"
                           setupValue="${param.username}"
                           validation='${validation.containsKey("username")}'
                           validationErrorProp='${validation.get("username")}' inline="true"/>

        <tags:typicalInput type="password"
                           name="password"
                           labelProp="user.password"
                           setupValue="${param.password}"
                           validation='${validation.containsKey("password")}'
                           validationErrorProp='${validation.get("password")}' inline="true"/>

        <tags:typicalInput type="password"
                           name="confirmPassword"
                           labelProp="user.confirmPassword"
                           setupValue="${param.confirmPassword}"
                           validation='${validation.containsKey("confirmPassword")}'
                           validationErrorProp='${validation.get("confirmPassword")}' inline="true"/>

        <tags:typicalInput type="text"
                           name="firstName"
                           labelProp="user.firstName"
                           setupValue="${param.firstName}"
                           validation='${validation.containsKey("firstName")}'
                           validationErrorProp='${validation.get("firstName")}' inline="true"/>

        <tags:typicalInput type="text"
                           name="lastName"
                           labelProp="user.lastName"
                           setupValue="${param.lastName}"
                           validation='${validation.containsKey("lastName")}'
                           validationErrorProp='${validation.get("lastName")}' inline="true"/>

            <%-- COUNTRY INPUT--%>
        <div class="form-group <c:if test='${validation.containsKey("country")}'> has-error has-feedback </c:if>">
            <fmt:message var="countryTitle" bundle="${view}" key="user.country"/>
            <label class="col-md-3 control-label" for="country">${countryTitle}
            </label>
            <jsp:useBean id="countries" scope="request" type="java.util.Map"/>
            <div class="col-md-6">
                <select class="form-control" id="country" name="country">
                    <option value="" hidden>${countryTitle}</option>
                    <c:forEach var="oneCountry" items="${countries}">
                        <option value="${oneCountry.key}"
                                <c:if test='${param.country==oneCountry.key}'>selected</c:if>>
                                ${oneCountry.value}</option>
                    </c:forEach>
                </select>
                <c:if test='${validation.containsKey("gender")}'>
                    <span class="glyphicon glyphicon-exclamation-sign form-control-feedback"></span>
                    <span class="help-block">
                        <small><fmt:message bundle="${errors}" key='${validation.get("gender")}'/></small>
                    </span>
                </c:if>
            </div>
        </div>
            <%-- end of COUNTRY input --%>

        <tags:typicalInput type="date"
                           name="birthday"
                           labelProp="user.birthday"
                           setupValue="${param.birthday}"
                           validation='${validation.containsKey("birthday")}'
                           validationErrorProp='${validation.get("birthday")}' inline="true"/>

            <%-- GENDER INPUT --%>
        <div class="form-group <c:if test='${validation.containsKey("gender")}'> has-error has-feedback </c:if>">
            <fmt:message var="gender" bundle="${view}" key="user.gender"/>
            <label class="col-md-3 control-label" for="gender">${gender}
            </label>
            <div class="col-md-6">
                <select class="form-control" id="gender" name="gender">
                    <option value="" hidden>${gender}</option>
                    <option value="MALE"
                            <c:if test='${param.gender=="MALE"}'>selected</c:if>><fmt:message bundle="${view}"
                                                                                              key="user.gender.male"/></option>
                    <option value="FEMALE"
                            <c:if test='${param.gender=="FEMALE"}'>selected</c:if>><fmt:message bundle="${view}"
                                                                                                key="user.gender.female"/></option>
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
            <fmt:message bundle="${view}" key="titles.registration"/></button>
    </form>
</tags:mainMenu>

