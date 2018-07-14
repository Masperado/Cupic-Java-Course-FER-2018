<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Registracija</title>

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


<c:choose>

    <c:when test="${sessionScope[\"current.user.id\"]==null}">
        <h2>Registriraj se</h2>

        <form action="register" method="post">
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

            <div>
                <div>
                    <span class="formLabel">Ime</span><input type="text" name="firstName" value='<c:out
                value="${form.firstName}"/>' size="20">
                </div>
                <c:if test="${form.hasError('firstName')}">
                    <div class="error"><c:out value="${form.getError('firstName')}"/></div>
                </c:if>
            </div>

            <div>
                <div>
                    <span class="formLabel">Prezime</span><input type="text" name="lastName" value='<c:out
                value="${form.lastName}"/>' size="20">
                </div>
                <c:if test="${form.hasError('lastName')}">
                    <div class="error"><c:out value="${form.getError('lastName')}"/></div>
                </c:if>
            </div>

            <div>
                <div>
                    <span class="formLabel">E-Mail</span><input type="email" name="email" value='<c:out
                value="${form.email}"/>' size="20">
                </div>
                <c:if test="${form.hasError('email')}">
                    <div class="error"><c:out value="${form.getError('email')}"/></div>
                </c:if>
            </div>

            <div class="formControls">
                <span class="formLabel">&nbsp;</span>
                <input type="submit" name="method" value="Registiraj se">
            </div>

        </form>
    </c:when>

    <c:otherwise>
        <h2>Uspješno ste se registrirali.
            <br>
            <a href="main">Kliknite za povratak na početnu stranicu.</a></h2>
    </c:otherwise>

</c:choose>
</body>
</html>
