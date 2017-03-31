<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.view" var="view"/>
<fmt:setBundle basename="i18n.errors" var="errors"/>

<tags:mainMenu active="profile">
    <div class="page-header col-md-offset-4 text-center">
        <h3><fmt:message bundle="${view}" key="titles.profile"/></h3>
    </div>
    <div class="col-md-3">
        <%--<c:set var="companionImageId"--%>
               <%--value="${sessionScope.loggedUser.getPhoto().getId()}"/> &lt;%&ndash; TODO : do somethin with User Session&ndash;%&gt;--%>
        <img src="<c:url value="${sessionScope.loggedUser.getPhoto().getSourcePath()}"/>" class="rounded img-thumbnail">
            <form class="text-center" action="<c:url value="/main/image"/>" method="post" enctype="multipart/form-data">
            <button class="btn btn-primary btn-file btn-sm">
                <div id="imageBtnLabel">
                    <span class="glyphicon glyphicon-folder-open"></span>
                    <fmt:message bundle="${view}" key="upload.browse"/>
                </div>
                <input type="file" name="imageFile" id="image"/>
            </button>
            <button type="submit" class="btn btn-primary btn-sm">
                <span class="glyphicon glyphicon-upload"></span>
                <fmt:message bundle="${view}" key="upload.upload"/>
            </button>
        </form>
    </div>
    <div class="col-md-6">
        <form>
            <tags:typicalInput type="text"
                               name="firstName"
                               labelProp="user.firstName"
                               setupValue="${param.firstName}"
                               validation='${validation.containsKey("firstName")}'
                               validationErrorProp='${validation.get("firstName")}'
                               inline="false"/>
            <tags:typicalInput type="text"
                               name="lastName"
                               labelProp="user.lastName"
                               setupValue="${param.lastName}"
                               validation='${validation.containsKey("lastName")}'
                               validationErrorProp='${validation.get("lastName")}'
                               inline="false"/>


        </form>
    </div>
    <script type="text/javascript">
        (function ($) {
            $(function () {
                $('.btn-file').each(function () {
                    $('input[type=file]', this).change(function () {
                        var value = $(this).val();
                        document.getElementById("imageBtnLabel").innerHTML =
                            value.substring(value.lastIndexOf('\\') + 1, value.length);
                    });
                });
            });
        })(jQuery);
    </script>
</tags:mainMenu>

