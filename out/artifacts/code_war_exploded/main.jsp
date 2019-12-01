<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="database.GuideDao" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <c:url var="url_1" value="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${url_1}"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <c:url var="url_2" value="styles/main_style.css"/>
    <link rel="stylesheet" href="${url_2}">
    <c:url var="url_3" value="styles/table_style.css"/>
    <link rel="stylesheet" href="${url_3}"/>
    <c:url var="image_url" value="images/marlboro.png"/>
    <link rel="icon" href="${image_url}" type="images/x-icon">
    <title><c:out value="SmokPar"/></title>
</head>
<body>
<div id="wrap">
    <header>
        <div class="title"><c:out value="SmokPar"/></div>
        <form method="post" action="Guides">
            <div class="input-form">
                <div class="input-group">
                    <input type="hidden" name="action" value="search_guide">
                    <input type="text" title="Enter name of the guide" class="form-control"
                           pattern="[A-Z]{1}[a-zA-Z-]*" name="guide_name" maxlength="35"
                           placeholder="Name of the guide" required>
                    <div class="input-group-btn">
                        <button title="Search guide" class="btn btn-info" type="submit">Search</button>
                    </div>
                </div>
            </div>
        </form>
    </header>
</div>
<main>
    <div id="button_back">
        <form action="Guides" method="post">
            <input type="hidden" name="action" value="back_user">
            <button class="btn btn-info" title=""><c:out value="Back"/></button>
        </form>
    </div>
    <div id="button_all">
        <form action="Guides" method="post">
            <input type="hidden" name="action" value="all">
            <button title="Show all guides" class="btn btn-info"><c:out value="All"/></button>
        </form>
    </div>
    <div id="basic_content">
        <c:set var="dao" value="${GuideDao}"/>
        <c:set var="start_guides" value="${dao.select}"/>
        <table class="table_blur">
            <thead>
            <tr>
                <th><c:out value="Name"/></th>
                <th><c:out value="Description"/></th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${guides == null}">
            <c:forEach var="guide" items="${start_guides}">
            <tr>
                <td><c:out value="${guide.getName()}"/></td>
                <td><c:out value="${guide.getDescription()}"/></td>
            </tr>
            </c:forEach>
            </c:if>
            <c:if test="${guides != null}">
            <c:forEach var="guide" items="${guides}">
            <tr>
                <td><c:out value="${guide.getName()}"/></td>
                <td><c:out value="${guide.getDescription()}"/></td>
            </tr>
            </c:forEach>
            </c:if>
        </table>
    </div>
</main>
</body>
</html>