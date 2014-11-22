<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>
    <title>Login Page</title>
    <link href="${pageContext.request.contextPath}/app/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/app/css/plugins/metisMenu/metisMenu.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/app/css/plugins/dataTables.bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/app/css/sb-admin-2.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/app/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet"
          type="text/css">
    <script src="${pageContext.request.contextPath}/app/js/html5shiv.js"></script>
    <script src="${pageContext.request.contextPath}/app/js/respond.min.js"></script>
    <script src="${pageContext.request.contextPath}/app/js/jquery-1.11.0.js"></script>
    <script src="${pageContext.request.contextPath}/app/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/app/js/plugins/metisMenu/metisMenu.min.js"></script>
    <script src="${pageContext.request.contextPath}/app/js/plugins/dataTables/jquery.dataTables.js"></script>
    <script src="${pageContext.request.contextPath}/app/js/plugins/dataTables/dataTables.bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/app/js/sb-admin-2.js"></script>
</head>
<body>
<c:url var="loginUrl" value="/j_spring_security_check"></c:url>
<form action="${loginUrl}" method="POST">
    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Please Sign In</h3>
                    </div>
                    <div class="panel-body">
                        <form role="form">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="E-mail" name='email' type='email'
                                           autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Password" name='password' type='password'
                                           value="">
                                </div>
                                <!-- Change this to a button or input when using this as a form -->
                                <input class="btn btn-lg btn-primary btn-block" type="submit" name="submit" value="Login">
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>

</body>
</html>
