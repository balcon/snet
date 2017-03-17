<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="i18n.view" var="view"/>
<script type="text/javascript">
    var count = 9;
    var redirect = "<c:url value="/main"/>";
    function countDown() {
        var timer = document.getElementById("timer");
        if (count > 0) {
            count--;
            timer.innerHTML = count;
            setTimeout("countDown()", 1000);
        } else {
            window.location.href = redirect;
        }
    }
</script>
<tags:mainMenu active="main">

    <div class="alert alert-danger" role="alert">
        <strong><fmt:message bundle="${view}" key="error"/></strong><br>
        <fmt:message bundle="${view}" key="error.coundown"/>
        <span id="timer"><script type="text/javascript">countDown();</script></span>
    </div>

</tags:mainMenu>
