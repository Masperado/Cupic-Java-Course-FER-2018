<%@ page import="java.util.Random" %>
<%@ page import="java.awt.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Funny</title>
    <style>body {
        background-color: <c:choose><c:when test="${pickedBgCol!=null}">${pickedBgCol}</c:when><c:otherwise>white</c:otherwise></c:choose>;
        color: <%
        Random ra = new Random();
        int r, g, b;
        r = ra.nextInt(255);
        g = ra.nextInt(255);
        b = ra.nextInt(255);
        Color color = new Color(r, g, b);
        String hex = Integer.toHexString(color.getRGB() & 0xffffff);
        if (hex.length() < 6) {
            hex = "0" + hex;
        }
        hex = "#" + hex;
        out.write(hex);
        %>;
        -webkit-text-stroke-width: 1px;
        -webkit-text-stroke-color: black;
        text-align: center;
        font-size: 130%;
    }
    </style>
</head>
<body>
<a href="/webapp2">Home</a>
<hr>
<h2>Upregnimo kočije blještavih snova</h2>
<h2>Nek započne pomamni trk </h2>
<h2>Brže od snježne vijavice bile</h2>
<h2>Vrti se razigrani životni zvrk</h2>
<h2>Da li je itko svog cilja svjestan?</h2>
<h2>Kako se ciljevi mogu naći?</h2>
<h2>Juri suluda kočijo i zavij noć</h2>
<h2>Šta noćas najveći je stisak</h2>
<h2>I kad stignemo do posljednje postaje snene</h2>
<h2>Istresimo džepove do pare</h2>


</body>
</html>
