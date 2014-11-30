<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>
    <title>Login Page</title>
</head>
<body>
<c:import url="imports.jsp"></c:import>
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
                                <c:if test="${not empty error}">
                                    <div class="alert alert-danger">
                                            ${error}
                                    </div>
                                </c:if>
                                <input class="btn btn-lg btn-primary btn-block" type="submit" name="submit"
                                       value="Login">
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
