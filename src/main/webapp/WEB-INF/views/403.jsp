<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Access denied!</title>
</head>
<body>
<c:import url="imports.jsp"></c:import>
<div class="container">
    <div class="row">
        <div class="col-lg-12">
            <div class="jumbotron">
                <h1>Error 403 (Access denied)</h1>

                <p>A web server may return a 403 Forbidden HTTP status code in response to a request from a client for a
                    web page or resource to indicate that the server can be reached and understood the request, but
                    refuses to take any further action. Status code 403 responses are the result of the web server being
                    configured to deny access, for some reason, to the requested resource by the client.</p>

                <p><a class="btn btn-primary btn-lg" role="button"
                      href="${pageContext.request.contextPath}/home">Home</a>
                </p>
            </div>
        </div>
        <!-- /.col-lg-12 -->
    </div>
</div>
</body>
</html>
