<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:mainPage>
<hr>
    <form action="registration" method="post">
        <input type="text" name="username" placeholder="Username"><br>
        <input type="password" name="password" placeholder="Password"><br>
        <hr>
        <input type="text" name="firstName" placeholder="First name">
        <c:if test="${fncorrect==false}">
            Can-t be empty
        </c:if>
        <br>
        <input type="text" name="lastName" placeholder="Last name"><br>
        <hr>
        <input type="submit" value="Registration">

    </form>
</tags:mainPage>

