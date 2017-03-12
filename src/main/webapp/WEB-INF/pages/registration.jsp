<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:mainPage>
    <hr>
    <form action="registration" method="post">
        <input type="text" name="username" placeholder="Username" value="<c:out value="${param.username}"/>"><br>
        <c:if test='${errors.containsKey("username")}'>${errors.get("username")}<br></c:if>
        <input type="password" name="password" placeholder="Password"><br>
        <c:if test='${errors.containsKey("password")}'>${errors.get("password")}<br></c:if>
        <hr>
        <input type="text" name="firstName" placeholder="First name">
        <br>
        <input type="text" name="lastName" placeholder="Last name"><br>
        <hr>
        <input type="submit" value="Registration">

    </form>
</tags:mainPage>

