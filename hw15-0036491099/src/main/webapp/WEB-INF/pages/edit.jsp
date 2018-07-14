<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Uređivanje</title>

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

<h1>Uređivanje posta</h1>
<form action="${action}" method="post">
    <div>
        <div>
            <span class="formLabel">Naziv</span><input type="text" name="title" value='<c:out
                value="${form.title}"/>' size="20">
        </div>
        <c:if test="${form.hasError('title')}">
            <div class="error"><c:out value="${form.getError('title')}"/></div>
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
        <input type="submit" name="method" value="Pohrani post">
    </div>

</form>
</body>
</html>
