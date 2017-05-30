<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="inputType" required="true" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="labelProp" required="true" %>
<%@ attribute name="initValue" required="true" %>
<%@ attribute name="errors" required="false"
              type="java.util.Map<java.lang.String,com.epam.study.snet.enums.FormErrors>" %>
<%@ attribute name="inline" required="false" type="java.lang.Boolean" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.view" var="view"/>
<fmt:setBundle basename="i18n.errors" var="errorMessages"/>
<fmt:message var="label" bundle="${view}" key="${labelProp}"/>

<div class="form-group<c:if test="${errors.containsKey(name)}"> has-error has-feedback </c:if>">
    <label class="<c:if test="${inline==true}">col-md-3 </c:if>control-label" for="${name}">
        ${label}
    </label>
    <div class="<c:if test="${inline==true}">col-md-6 </c:if>">
        <input id="${name}" type="${inputType}" name="${name}" class="form-control"
               placeholder="${label}" value="<c:out value="${initValue}"/>">
        <c:if test='${errors.containsKey(name)}'>
            <span class="glyphicon glyphicon-exclamation-sign  form-control-feedback"></span>
            <span class="help-block">
                <small>
                <fmt:message bundle="${errorMessages}" key="${errors.get(name)}"/>
                </small>
            </span>
        </c:if>
    </div>
</div>