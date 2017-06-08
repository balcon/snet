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
    <jsp:useBean id="formValidation" scope="request"
                 class="com.epam.study.snet.model.FormValidation"/>
    <form class="form-horizontal" action="registration" method="post">

        <tags:typicalInput inputType="text"
                           name="username"
                           labelProp="user.username"
                           initValue="${param.username}"
                           errors="${formValidation.errors}"
                           inline="true"/>

        <tags:typicalInput inputType="password"
                           name="password"
                           labelProp="user.password"
                           initValue="${param.password}"
                           errors="${formValidation.errors}"
                           inline="true"/>

        <tags:typicalInput inputType="password"
                           name="confirmPassword"
                           labelProp="user.confirmPassword"
                           initValue="${param.confirmPassword}"
                           errors="${formValidation.errors}"
                           inline="true"/>

        <tags:typicalInput inputType="text"
                           name="firstName"
                           labelProp="user.firstName"
                           initValue="${param.firstName}"
                           errors="${formValidation.errors}"
                           inline="true"/>

        <tags:typicalInput inputType="text"
                           name="lastName"
                           labelProp="user.lastName"
                           initValue="${param.lastName}"
                           errors="${formValidation.errors}"
                           inline="true"/>

        <%--COUNTRY INPUT--%>
        <div class="form-group <c:if test='${formValidation.errors.containsKey("country")}'> has-error has-feedback </c:if>">
            <fmt:message var="countryTitle" bundle="${view}" key="user.country"/>
            <label class="col-md-3 control-label" for="country">${countryTitle}
            </label>
            <jsp:useBean id="countries" scope="request" type="com.epam.study.snet.model.Countries"/>
            <div class="col-md-6">
                <select class="form-control" id="country" name="country">
                    <option value="" hidden>${countryTitle}</option>
                    <c:forEach var="oneCountry" items="${countries.list}">
                        <option value="${oneCountry.key}"
                                <c:if test='${param.country==oneCountry.key}'>selected</c:if>>
                                ${oneCountry.value}</option>
                    </c:forEach>
                </select>
                <c:if test='${formValidation.errors.containsKey("country")}'>
                    <span class="glyphicon glyphicon-exclamation-sign form-control-feedback"></span>
                    <span class="help-block">
                        <small><fmt:message bundle="${errors}" key='${formValidation.errors.get("gender")}'/></small>
                    </span>
                </c:if>
            </div>
        </div>
            <%-- end of COUNTRY input --%>

        <tags:typicalInput inputType="date"
                           name="birthday"
                           labelProp="user.birthday"
                           initValue="${param.birthday}"
                           errors="${formValidation.errors}"
                           inline="true"/>

            <%-- GENDER INPUT --%>
        <div class="form-group <c:if test='${formValidation.errors.containsKey("gender")}'> has-error has-feedback </c:if>">
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
                <c:if test='${formValidation.errors.containsKey("gender")}'>
                    <span class="glyphicon glyphicon-exclamation-sign form-control-feedback"></span>
                    <span class="help-block">
                        <small><fmt:message bundle="${errors}" key='${formValidation.errors.get("gender")}'/></small>
                    </span>
                </c:if>
            </div>
        </div>
            <%-- end of GENDER input --%>
        <button type="submit" class="btn btn-primary col-md-offset-4">
            <fmt:message bundle="${view}" key="titles.registration"/></button>
    </form>
</tags:mainMenu>

