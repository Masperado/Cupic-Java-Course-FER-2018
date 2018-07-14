<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <style>body {
        background-color: <c:choose><c:when test="${pickedBgCol!=null}">${pickedBgCol}</c:when><c:otherwise>white</c:otherwise></c:choose>
    }
    </style>
</head>
<body>
<a href="setcolor">Background color chooser</a>
<hr>
<a href="trigonometric?a=0&b=90">Trigonometric</a>
<br>
<form action="trigonometric" method="GET">
    Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
    Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
    <input type="submit" value="Tabeliraj"><input type="reset" value="Reset">
</form>
<hr>
<a href="stories/funny.jsp">Funny story</a>
<hr>
<a href="reportImage">OS usage</a>
<hr>
<form action="powers" method="GET">
    a:<br><input type="number" name="a" min="-100" max="100" step="1" value="1"><br>
    b:<br><input type="number" name="b" min="-100" max="100" step="1" value="100"><br>
    n:<br><input type="number" name="n" min="1" max="5" step="1" value="3"><br>
    <input type="submit" value="Power"><input type="reset" value="Reset">
</form>
<hr>
<a href="appinfo.jsp">Info</a>
<hr>
<a href="glasanje">Glasanje</a>
</body>
</html>