<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Info</title>
    <style>body {
        background-color: <c:choose><c:when test="${pickedBgCol!=null}">${pickedBgCol}</c:when><c:otherwise>white</c:otherwise></c:choose>;
    }
    </style>
</head>
<body>
<a href="/webapp2">Home</a>
<hr>
<h1>Time elapsed: <%
    long startupTime = (long) request.getServletContext().getAttribute("startup");
    long timeElapsed = System.currentTimeMillis() - startupTime;
    StringBuilder sb = new StringBuilder();
    long days = timeElapsed / (1000 * 60 * 60 * 24);
    timeElapsed -= days * 1000 * 60 * 60 * 24;
    long hours = timeElapsed / (1000 * 60 * 60);
    timeElapsed -= hours * 1000 * 60 * 60;
    long minutes = timeElapsed / (1000 * 60);
    timeElapsed -= minutes * 1000 * 60;
    long seconds = timeElapsed / (1000);
    timeElapsed -= seconds * 1000;
    sb.append(days).append(" days ").append(hours).append(" hours ").append(minutes).append(" minutes ").append
            (seconds).append(" seconds ").append(timeElapsed).append(" miliseconds ");
    out.write(sb.toString());
%></h1>
</body>
</html>
