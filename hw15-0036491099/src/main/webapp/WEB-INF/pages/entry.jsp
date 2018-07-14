<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>${entry.title}</title>
    <style type="text/css">
        .error {
            font-family: fantasy;
            font-weight: bold;
            font-size: 0.9em;
            color: #FF0000;
            padding-left: 110px;
        }

        .formLabel {
            display: inline-block;
            width: 100px;
            font-weight: bold;
            text-align: right;
            padding-right: 10px;
        }

        .formControls {
            margin-top: 10px;
        }
    </style>
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

<h1>
    <div>Naziv posta:</div>
    <div>${entry.title}</div>
</h1>

<h3>
    <div>Tekst posta:</div>
    <div>${entry.text}</div>
</h3>

<br>

<h2>Komentari: </h2>
<c:forEach items="${entry.comments}" var="comment">
    <h4>${comment.usersEMail}</h4>
    <h5>${comment.postedOn}</h5>
    <div>${comment.message}</div>
    <hr>
</c:forEach>

<br>

<h2>Dodaj novi komentar:</h2>
<form action="<%out.write(request.getContextPath());%>/servleti/author/${author.nick}/${entry.id}" method="post">
    <div>
        <div>
            <span class="formLabel">Email</span><input type="email" name="email" value='<c:out
                value="${sessionScope[\"current.user.email\"]}"/>' size="20">
        </div>
        <c:if test="${form.hasError('email')}">
            <div class="error"><c:out value="${form.getError('email')}"/></div>
        </c:if>
    </div>

    <div>
        <div>
            <span class="formLabel">Tekst</span><textarea type="text" name="text" rows="15"
                                                          cols="50">${form.text}</textarea>
        </div>
        <c:if test="${form.hasError('text')}">
            <div class="error"><c:out value="${form.getError('text')}"/></div>
        </c:if>
    </div>

    <div class="formControls">
        <span class="formLabel">&nbsp;</span>
        <input type="submit" name="method" value="Komentiraj">
    </div>

</form>

<br>

<c:if test="${sessionScope[\"current.user.id\"].equals(author.id)}">
    <h1><a href="<%=request.getContextPath()%>/servleti/author/${author.nick}/edit/${entry.id}">Editiraj post</a>
    </h1>
</c:if>

</body>
</html>



