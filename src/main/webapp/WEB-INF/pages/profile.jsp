<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.view" var="view"/>
<fmt:setBundle basename="i18n.errors" var="errors"/>

<jsp:useBean id="loggedUser" scope="session" type="com.epam.study.snet.entity.User"/>

<tags:mainMenu active="profile">
    <div class="col-xs-offset-4">
        <h3><fmt:message bundle="${view}" key="titles.profile"/> <u>${loggedUser.username}</u></h3>
    </div>
    <c:if test='${changed.equals("prof")}'>
        <div class="alert alert-success" role="alert">
            <fmt:message bundle="${view}" key="changes.message.prof"/>
        </div>
    </c:if>
    <c:if test='${changed.equals("pass")}'>
        <div class="alert alert-success" role="alert">
            <fmt:message bundle="${view}" key="changes.message.pass"/>
        </div>
    </c:if>
    <div class="col-md-3">
        <img src="<c:url value="${loggedUser.photo.sourcePath}"/>" class="rounded img-thumbnail">
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
            ${param.changed}
        <form class="form-horizontal" action="<c:url value="/main/profile"/>" method="post">
            <tags:typicalInput type="text"
                               name="firstName"
                               labelProp="user.firstName"
                               setupValue="${loggedUser.firstName}"
                               validation='${validation.containsKey("firstName")}'
                               validationErrorProp='${validation.get("firstName")}'
                               inline="false"/>
            <tags:typicalInput type="text"
                               name="lastName"
                               labelProp="user.lastName"
                               setupValue="${loggedUser.lastName}"
                               validation='${validation.containsKey("lastName")}'
                               validationErrorProp='${validation.get("lastName")}'
                               inline="false"/>

            <tags:typicalInput type="date"
                               name="birthday"
                               labelProp="user.birthday"
                               setupValue="${loggedUser.birthday}"
                               validation='${validation.containsKey("birthday")}'
                               validationErrorProp='${validation.get("birthday")}' inline="false"/>

                <%-- GENDER INPUT --%>
            <div class="form-group">
                <fmt:message var="gender" bundle="${view}" key="user.gender"/>
                <label class="control-label" for="gender">${gender}
                </label>
                <select class="form-control" id="gender" name="gender">
                    <option value="" hidden>${gender}</option>
                    <option value="MALE"
                            <c:if test='${loggedUser.gender=="MALE"}'>selected</c:if>>
                        <fmt:message bundle="${view}" key="user.gender.male"/></option>
                    <option value="FEMALE"
                            <c:if test='${loggedUser.gender=="FEMALE"}'>selected</c:if>>
                        <fmt:message bundle="${view}" key="user.gender.female"/></option>
                </select>
            </div>
                <%-- end of GENDER input --%>
            <input type="hidden" name="changed" value="prof">
            <button type="submit" class="btn btn-primary col-md-offset-3">
                <fmt:message bundle="${view}" key="changes.save"/></button>
        </form>

        <form class="form-horizontal" action="<c:url value="/main/profile"/>" method="post">
            <tags:typicalInput type="password"
                               name="password"
                               labelProp="user.password"
                               setupValue="${param.password}"
                               validation='${validation.containsKey("password")}'
                               validationErrorProp='${validation.get("password")}'
                               inline="false"/>

            <tags:typicalInput type="password"
                               name="confirmPassword"
                               labelProp="user.confirmPassword"
                               setupValue="${param.confirmPassword}"
                               validation='${validation.containsKey("confirmPassword")}'
                               validationErrorProp='${validation.get("confirmPassword")}'
                               inline="false"/>

            <input type="hidden" name="changed" value="pass">
            <button type="submit" class="btn btn-primary col-md-offset-3">
                <fmt:message bundle="${view}" key="changes.password"/></button>
        </form>
        <br>
        <div class="panel-group">
            <div class="panel panel-danger">
                <div class="panel-heading">
                    <fmt:message bundle="${view}" key="remove.title"/>
                </div>
                <div class="panel-body">
                    <form class="form-horizontal" action="<c:url value="/main/removeUser"/>" method="post">
                        <div class="checkbox">
                            <label><input id="check1" type="checkbox" onchange="enableRemoveButton()">
                                <fmt:message bundle="${view}" key="remove.want"/>
                            </label>
                        </div>
                        <div class="checkbox">
                            <label><input id="check2" type="checkbox" onchange="enableRemoveButton()" disabled>
                                <fmt:message bundle="${view}" key="remove.shure"/>
                            </label>
                        </div>
                        <br>
                        <button type="submit" id="removeBtn" class="btn btn-danger col-md-offset-4" disabled>
                            <fmt:message bundle="${view}" key="remove.button"/>
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        function enableRemoveButton() {
            if (check1.checked) {
                check2.disabled = false;
                removeBtn.disabled = !check2.checked;
            }
            else {
                check2.disabled = true;
                removeBtn.disabled = true;
            }
        }
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

