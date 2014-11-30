<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head></head>

<body>
<c:import url="imports.jsp"></c:import>
<div id="wrapper">
    <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <a href="${pageContext.request.contextPath}/home"><img src="/cellular/app/js/logo.png"></a>
        </div>
        <ul class="nav navbar-top-links navbar-right">
            <li>${pageContext.request.userPrincipal.name}
            </li>
            <li><a href="${pageContext.request.contextPath}/j_spring_security_logout"><i
                    class="fa fa-sign-out fa-fw"></i> Logout</a>
            </li>
        </ul>
        <div class="navbar-default sidebar" role="navigation">
            <div class="sidebar-nav navbar-collapse">
                <ul class="nav" id="side-menu">
                    <li>
                        <a href="${pageContext.request.contextPath}/contract/new">
                            <i class="fa fa-plus-circle fa-fw"></i> Create contract
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/contract/all">
                            <i class="fa fa-navicon fa-fw"></i> Contracts
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/tariff/all">
                            <i class="fa fa-bar-chart-o fa-fw"></i> Tariffs
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/option/all">
                            <i class="fa fa-gears fa-fw"></i> Options
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</div>
</body>
</html>
