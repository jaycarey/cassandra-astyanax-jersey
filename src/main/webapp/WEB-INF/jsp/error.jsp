<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="it" scope="request" type="com.jay.cassandraastyanax.dto.ExceptionDto"/>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
    <title>${it.message}</title>
</head>
<body>
<div class="jumbotron">
    <h1>Boom:<small>${it.message}</small></h1>
</div>

<div class="container">
    <ul class="">
        <c:forEach var="frame" items="${it.frames}">
            <li>${frame}</li>
        </c:forEach>
    </ul>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"/>
</body>
</html>