<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Glasanje</title>
</head>
<body>
<a href="index.html">Home</a>
<br>

<br>
<h3>Rezultati glasanja</h3>
<table border="1">
    <tr>
        <th>Pjesma</th>
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
<img alt="Pie-chart" src="glasanje-grafika?pollID=${pollID}" width="400" height="400"/>
<br>
<h3>Rezultati u XLS formatu</h3>
<p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls?pollID=${pollID}">ovdje</a></p>
<br>
<h3>Pobjednici:</h3>
<ul>
    <c:forEach var="winner" items="${winners}">
        <li><a href="${winner.link}" target="_blank">${winner.name}</a></li
        >
    </c:forEach>
</ul>

</body>
</html>
