<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Glasanje</title>
    <style>body {
        background-color: <c:choose><c:when test="${pickedBgCol!=null}">${pickedBgCol}</c:when><c:otherwise>white</c:otherwise></c:choose>;


    </style>
</head>
<body>
<a href="/webapp2">Home</a>
<br>

<br>
<h3>Rezultati glasanja</h3>
<table border="1">
    <tr>
        <th>Bend</th>
        <th>Broj glasova</th>
    </tr>
    <c:forEach var="result" items="${results}">
        <tr>
            <td>${result.name}</td>
            <td>${result.result}</td>
        </tr>
    </c:forEach>
</table>

<br>
<h3>Grafički prikaz rezultata</h3>
<img alt="Pie-chart" src="glasanje-grafika" width="400" height="400"/>
<br>
<h3>Rezultati u XLS formatu</h3>
<p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a></p>
<br>
<h3>Najbolji bendovi po izboru polaznika vještine Osnove programskog jezika Java</h3>
<ul>
    <c:forEach var="winner" items="${winners}">
        <li><a href="${winner.link}" target="_blank">${winner.name}</a></li
        >
    </c:forEach>
</ul>

</body>
</html>
