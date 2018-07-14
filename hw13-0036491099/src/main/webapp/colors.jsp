<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Colors</title>
    <style>body {
        background-color: <c:choose><c:when test="${pickedBgCol!=null}">${pickedBgCol}</c:when><c:otherwise>white</c:otherwise></c:choose>;
    }
    </style>
</head>
<body>
<a href="/webapp2">Home</a>
<hr>
<ul>
    <li><a href="setcolor?color=white">WHITE</a></li>
    <li><a href="setcolor?color=red">RED</a></li>
    <li><a href="setcolor?color=green">GREEN</a></li>
    <li><a href="setcolor?color=cyan">CYAN</a></li>
    <ul>
</body>
</html>
