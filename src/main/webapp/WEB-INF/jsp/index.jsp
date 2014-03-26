<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="it" scope="request" type="com.jay.cassandraastyanax.controller.RecipeAndIngredients"/>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
    <title>${it.name}</title>
</head>
<body>
<div class="jumbotron">
    <h1>${it.name}</h1>

    <ul>
        <c:forEach items="${it.ingredients}" var="ingredient">
            <li>${ingredient.quantity} ${ingredient.unit} of ${ingredient.name}</li>
        </c:forEach>
    </ul>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"/>
</body>
</html>