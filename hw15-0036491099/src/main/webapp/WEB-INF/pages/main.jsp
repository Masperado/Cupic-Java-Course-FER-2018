<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Blog</title>

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


<c:if test="${sessionScope[\"current.user.id\"]==null}">

    <h2>Logiraj se: </h2>
    <form action="main" method="post">
        <div>
            <div>
                <span class="formLabel">Nadimak</span><input type="text" name="nick" value='<c:out
                value="${form.nick}"/>' size="20">
            </div>
            <c:if test="${form.hasError('nick')}">
                <div class="error"><c:out value="${form.getError('nick')}"/></div>
            </c:if>
        </div>

        <div>
            <div>
                <span class="formLabel">Lozinka</span><input type="password" name="password" value='' size="20">
            </div>
            <c:if test="${form.hasError('password')}">
                <div class="error"><c:out value="${form.getError('password')}"/></div>
            </c:if>
        </div>

        <div class="formControls">
            <span class="formLabel">&nbsp;</span>
            <input type="submit" name="method" value="Logiraj se">
        </div>

    </form>

    <br>
    <hr>
    <h2><a href="register"> Registriraj se</a></h2>
    <br>
    <hr>
</c:if>


<h3>Popis autora:</h3>
<ul>
<c:forEach items="${authors}" var="author">
    <li><a href="author/${author.nick}">${author.firstName} ${author.lastName}</a></li>
</c:forEach>
</ul>

</body>
</html>
