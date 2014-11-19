<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- Bootstrap Core CSS -->
    <link href="${pageContext.request.contextPath}/app/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="${pageContext.request.contextPath}/app/css/plugins/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- DataTables CSS -->
    <link href="${pageContext.request.contextPath}/app/css/plugins/dataTables.bootstrap.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/app/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="${pageContext.request.contextPath}/app/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet"
          type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>

    <![endif]-->
    <!-- jQuery Version 1.11.0 -->
    <script src="${pageContext.request.contextPath}/app/js/jquery-1.11.0.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="${pageContext.request.contextPath}/app/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="${pageContext.request.contextPath}/app/js/plugins/metisMenu/metisMenu.min.js"></script>

    <!-- DataTables JavaScript -->
    <script src="${pageContext.request.contextPath}/app/js/plugins/dataTables/jquery.dataTables.js"></script>
    <script src="${pageContext.request.contextPath}/app/js/plugins/dataTables/dataTables.bootstrap.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="${pageContext.request.contextPath}/app/js/sb-admin-2.js"></script>
    <style>
        .error {
            color: red;
            font-weight: bold;
        }
    </style>
    <script>
        function getOptions(tariff_id) {
            $.ajax({
                url: '${pageContext.request.contextPath}/option/get_options?tariff_id=' + tariff_id,
                type: 'GET',
                success: function (data) {
                    $(".options").empty();
                    data.forEach(function (elem, index, array) {
                        $(".options").append(
                                        "<div><label>" +
                                        "<input name='option_id' type='checkbox' value=" + elem.id + ">" + elem.title +
                                        ". Cost: " + elem.cost + ". Activation cost: " + elem.activationCost + "" +
                                        "</label></div>");
                    })

                }
            });
        }
        function checkNumber() {
            $.ajax({
                url: '${pageContext.request.contextPath}/contract/check_number',
                type: 'POST',
                data: {'number': $("#phoneNumber").val()},
                success: function (data) {
                    if (!$.isEmptyObject(data)) {
                        $("#phoneError").text(data);
                    } else {
                        $("#phoneError").empty();
                    }
                }
            });
        }
    </script>
</head>

<body>
<div id="wrapper">
    <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/contract/all">logo.png</a>
        </div>
        <!-- /.navbar-header -->

        <ul class="nav navbar-top-links navbar-right">
            <li>Username
            </li>
            <li><a href="${pageContext.request.contextPath}/logout"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
            </li>
        </ul>
        <!-- /.navbar-top-links -->

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
            <!-- /.sidebar-collapse -->
        </div>
        <!-- /.navbar-static-side -->
    </nav>
</div>
</body>
</html>
