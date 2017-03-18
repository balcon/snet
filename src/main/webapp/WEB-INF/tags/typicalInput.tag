<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="type" required="true" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="labelProp" required="true" %>
<%@ attribute name="setupValue" required="true" %>
<%@ attribute name="validation" required="true" type="java.lang.Boolean" %>
<%@ attribute name="validationErrorProp" required="true" %>
<%@ attribute name="inline" type="java.lang.Boolean" required="true" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.view" var="view"/>
<fmt:setBundle basename="i18n.errors" var="errors"/>
<fmt:message var="label" bundle="${view}" key="${labelProp}"/>

<div class="form-group<c:if test="${validation}"> has-error has-feedback </c:if>">
    <label class="<c:if test="${inline==true}">col-md-3 </c:if>control-label" for="${name}">${label}
    </label>
    <div class="<c:if test="${inline==true}">col-md-6 </c:if>">
        <input id="${name}" type="${type}" name="${name}" class="form-control"
               placeholder="${label}" value="<c:out value="${setupValue}"/>">
        <c:if test='${validation}'>
            <span class="glyphicon glyphicon-exclamation-sign  form-control-feedback"></span>
            <span class="help-block"><small>
                <fmt:message bundle="${errors}" key="${validationErrorProp}"/></small></span>
        </c:if>
    </div>
</div>