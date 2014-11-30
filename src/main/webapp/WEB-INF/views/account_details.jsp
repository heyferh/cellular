<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <script>
        function pushOptions(contract_id, client_id) {
            $.ajax({
                url: 'push_options',
                type: 'GET',
                data: {contract_id: contract_id, client_id: client_id},
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        location.reload();
                    } else {
                        $('#deleteError').html(data).show();
                    }
                }
            });
        }
        function enableOption(contract_id, option_id) {
            $.ajax({
                url: '${pageContext.request.contextPath}/enable_option',
                type: 'GET',
                data: {contract_id: contract_id, option_id: option_id, client_id:${client.id}},
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        location.reload();
                    } else {
                        $('#deleteError').html(data).show();
                    }
                }
            });
        }
        function disableOption(contract_id, option_id) {
            $.ajax({
                url: '${pageContext.request.contextPath}/disable_option',
                type: 'GET',
                data: {contract_id: contract_id, option_id: option_id, client_id:${client.id}},
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        location.reload();
                    } else {
                        $('#deleteError').html(data).show();
                    }
                }
            });
        }
        function changeTariff() {
            var options = $("input[name='option_id']:checked").map(function () {
                return parseInt($(this).val());
            }).get();
            $.ajax({
                traditional: true,
                url: '${pageContext.request.contextPath}/change_tariff',
                type: 'POST',
                data: {
                    tariff_id: $("input[name='tariff_id']:checked").val(),
                    contract_id:${contract.id},
                    option_id: options,
                    client_id:${client.id}
                },
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        location.reload();
                    } else {
                        $('#changeTariffError').html(data).show();
                    }
                }
            });
        }
    </script>
</head>

<body>
<c:import url="imports.jsp"></c:import>
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
            <li>${pageContext.request.userPrincipal.name}
            </li>
            <li><a href="${pageContext.request.contextPath}/j_spring_security_logout"><i
                    class="fa fa-sign-out fa-fw"></i> Logout</a>
            </li>
        </ul>
        <!-- /.navbar-top-links -->
        <div class="navbar-default sidebar" role="navigation">
            <div class="sidebar-nav navbar-collapse">
                <ul class="nav" id="side-menu">
                    <div class="panel panel-green" style="margin: 30px 10px;">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-shopping-cart fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge">${fn:length(cartBean.optionSet)}</div>
                                    <div>Items!</div>
                                </div>
                            </div>
                        </div>
                        <div class="panel-body">
                            <div>
                                <div class="table-responsive">
                                    <table class="table table-hover">
                                        <tbody>
                                        <c:set var="totalAmount" value="0"></c:set>
                                        <c:forEach var="option" items="${cartBean.optionSet}">
                                            <c:set var="totalAmount"
                                                   value="${totalAmount+option.cost+option.activationCost}"></c:set>
                                            <tr>
                                                <td>
                                                    <a href="remove_from_cart?option_id=${option.id}&client_id=${client.id}&contract_id=${contract.id}">
                                                        <i class="fa fa-minus fa-fw"></i>
                                                    </a>
                                                </td>
                                                <td>${option.title}<br>
                                                    Cost: ${option.cost+option.activationCost}
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        <tr>
                                            Total cost: ${totalAmount}
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div class="panel-footer" onclick="pushOptions(${contract.id},${client.id})"
                             style="cursor: pointer">
                            <span class="pull-left">Submit</span>
                            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>

                            <div class="clearfix"></div>
                        </div>
                    </div>
                </ul>
            </div>
        </div>
    </nav>
</div>
<div id="page-wrapper">
<div class="row">
    <div class="col-lg-12">
        <h2 class="page-header">Contract details</h2>
    </div>
    <!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<div class="row">
    <div class="col-lg-10">
        <div class="panel panel-primary">
            <div class="panel-heading">
                Info
            </div>
            <!-- /.panel-heading -->
            <div class="panel-body">
                <div class="col-lg-2">
                    <p>Name:</p>

                    <p>Last name:</p>

                    <p>Address:</p>

                    <p>ID card:</p>

                    <p>Email:</p>
                </div>
                <div class="col-lg-4">
                    <p>${client.firstName}</p>

                    <p>${client.lastName}</p>

                    <p>${client.address}</p>

                    <p>${client.idCard}</p>

                    <p>${client.email}</p>
                </div>
                <div class="col-lg-2">
                    <p>Phone number:</p>

                    <p>Balance:</p>

                    <p>Status:</p>

                    <p>Contract:</p>
                </div>
                <div class="col-lg-4">
                    <p>${contract.phoneNumber}</p>

                    <p>${contract.balance}</p>

                    <p>
                        <c:choose>
                            <c:when test="${contract.blockedByOperator}">
                                Blocked
                            </c:when>
                            <c:otherwise>
                                <c:choose>
                                    <c:when test="${contract.blockedByClient}">
                                        Blocked <a href="unblock?id=${contract.id}&clientID=${client.id}"><i
                                            class="fa fa-unlock fa-fw"></i></a>
                                    </c:when>
                                    <c:otherwise>
                                        Active <a href="block?id=${contract.id}&clientID=${client.id}"><i
                                            class="fa fa-lock fa-fw"></i></a>
                                    </c:otherwise>
                                </c:choose>
                            </c:otherwise>
                        </c:choose>
                    </p>

                    <p>
                        <select class="form-control" onchange="javascript:location.href = this.value;">
                            <option selected disabled>choose contract</option>
                            <c:forEach var="contract" items="${client.contracts}">
                                <option value="?id=${contract.id}&clientID=${client.id}">${contract.phoneNumber}</option>
                            </c:forEach>
                        </select>
                    </p>
                </div>
            </div>
            <!-- /.panel -->
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
</div>
<!-- /.row -->
<div class="row">
    <div class="col-lg-10">
        <div class="panel panel-primary">
            <div class="panel-heading">
                Current tariff: ${contract.tariff.title}
            </div>
            <div class="panel-body">
                <div id="deleteError" class="alert alert-danger alert-dismissable" style="display: none">
                </div>
                <div class="col-lg-6">
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <div class="table-responsive">
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th>Disable</th>
                                    <th>Title</th>
                                    <th>Cost</th>
                                    <th>Activation cost</th>
                                </tr>
                                </thead>
                                <tbody>
                                Enabled options:
                                <c:forEach var="option" items="${contract.options}">
                                    <tr>
                                        <td>
                                            <a onclick="disableOption(${contract.id},${option.id})"
                                               style="cursor: pointer">
                                                <i class="fa fa-minus fa-fw"></i>
                                            </a>
                                        </td>
                                        <td>${option.title}</td>
                                        <td>${option.cost}</td>
                                        <td>${option.activationCost}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <!-- /.table-responsive -->
                    </div>
                </div>
                <div class="col-lg-6">
                    <div class="panel-body">
                        <div class="table-responsive">
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th>Add to cart</th>
                                    <th>Title</th>
                                    <th>Cost</th>
                                    <th>Activation cost</th>
                                </tr>
                                </thead>
                                <tbody>
                                Available options:
                                <c:forEach var="option" items="${optionList}">
                                    <tr>
                                        <td>
                                            <a href="add_to_cart?option_id=${option.id}&client_id=${client.id}&contract_id=${contract.id}">
                                                <i class="fa fa-shopping-cart fa-fw" style="color: #5cb85c"></i>
                                            </a>
                                        </td>
                                        <td>${option.title}</td>
                                        <td>${option.cost}</td>
                                        <td>${option.activationCost}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <!-- /.table-responsive -->
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- /.col-lg-10 -->
</div>
<div class="row">
    <div class="col-lg-10">
        <div class="panel panel-primary">
            <div class="panel-heading">
                Change tariff and options
            </div>
            <div class="panel-body">
                <div id="changeTariffError" class="alert alert-danger alert-dismissable" style="display: none">
                </div>
                <div class="col-lg-6">
                    <div class="form-group">
                        <c:forEach var="tariff" items="${tariffList}">
                            <div class="radio">
                                <label>
                                    <input type="radio" name="tariff_id" value="${tariff.id}"
                                           onclick="getOptions(${tariff.id})">${tariff.title}.
                                    Cost: ${tariff.cost}
                                </label>
                            </div>
                        </c:forEach>
                    </div>
                    <input id="submit" type="submit" class="btn btn-primary" value="Change"
                           onclick="changeTariff()">
                </div>
                <div class="col-lg-6">
                    <div class="checkbox options">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
</div>
<!-- /#page-wrapper -->
</body>

</html>