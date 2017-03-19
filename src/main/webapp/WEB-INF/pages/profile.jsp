<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.view" var="view"/>
<fmt:setBundle basename="i18n.errors" var="errors"/>
<tags:mainMenu active="profile">
    <div class="page-header col-md-offset-4">
        <h3><fmt:message bundle="${view}" key="profile.title"/></h3>
    </div>
    <div class="col-md-3">
        <c:set var="imageId" value="${sessionScope.user.getPhoto().getId()}"/> <%-- TODO : do somethin with User Session--%>
        <img src="<c:url value="/main/image?imageId=${imageId}"/>" class="rounded img-thumbnail">
        <form action="<c:url value="/main/image"/>" method="post" enctype="multipart/form-data">
            <input type="file" name="imageFile">
            <input type="submit" value="Upload">
        </form>
    </div>
    <div class="col-md-6">
        <form>
            <tags:typicalInput type="text"
                               name="firstName"
                               labelProp="profile.firstName"
                               setupValue="${param.firstName}"
                               validation='${validation.containsKey("firstName")}'
                               validationErrorProp='${validation.get("firstName")}'
                               inline="false"/>
            <tags:typicalInput type="text"
                               name="lastName"
                               labelProp="profile.lastName"
                               setupValue="${param.lastName}"
                               validation='${validation.containsKey("lastName")}'
                               validationErrorProp='${validation.get("lastName")}'
                               inline="false"/>


        </form>
    </div>
    </div>
</tags:mainMenu>