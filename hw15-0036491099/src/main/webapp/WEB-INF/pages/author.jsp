<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>${author.nick}</title>
</head>


<body>

<header>
    <c:if test="${sessionScope[\"current.user.id\"]!=null}">
        <h3>Vaše ime: ${sessionScope["current.user.fn"]}</h3>
        <h3>Vaše prezime: ${sessionScope["current.user.ln"]}</h3>
        <h3><a href="<% out.write(request.getContextPath());%>/servleti/logout">Odlogiraj se</a></h3>
        <br>
        <br>
    </c:if>
    <h3><a href="<% out.write(request.getContextPath());%>/index.jsp">Početna stranica </a></h3>
    <hr>
</header>


<h1>Nadimak autora: ${author.nick}</h1>
<h1>Ime i prezime autora: ${author.firstName} ${author.lastName}</h1>
<h1>E-mail autora: ${author.email}</h1>

<br>
<hr>
<h2>Popis postova: </h2>
<ul>
<c:forEach items="${entries}" var="entry">
    <li><a href="<%=request.getContextPath()%>/servleti/author/${author.nick}/${entry.id}">${entry.title}</a></li>
</c:forEach>
</ul>

<c:if test="${sessionScope[\"current.user.id\"].equals(author.id)}">
    <br>
    <hr>
    <h1><a href="<%=request.getContextPath()%>/servleti/author/${author.nick}/new">Kreiraj novi post</a></h1>
</c:if>

</body>
</html>



