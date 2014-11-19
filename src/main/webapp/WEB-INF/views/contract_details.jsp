<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <script>
        function enableOption(contract_id, option_id) {
            $.ajax({
                url: 'enable_option',
                type: 'GET',
                data: {contract_id: contract_id, option_id: option_id},
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        location.href = "contract_details?id=" + contract_id;
                    } else {
                        $('#deleteError').html(data).show();
                    }
                }
            });
        }
        function disableOption(contract_id, option_id) {
            $.ajax({
                url: 'disable_option',
                type: 'GET',
                data: {contract_id: contract_id, option_id: option_id},
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        location.href = "contract_details?id=" + contract_id;
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
            console.log(options);
            console.log(options[0] + " " + options[1] + " " + options[2]);
            $.ajax({
                traditional: true,
                url: 'change_tariff',
                type: 'POST',
                data: {
                    tariff_id: $("input[name='tariff_id']:checked").val(),
                    contract_id:${contract.id},
                    option_id: options
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
<c:import url="navigation.jsp"></c:import>
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
                    </div>
                    <div class="col-lg-4">
                        <p>${contract.phoneNumber}</p>

                        <p>${contract.balance}</p>

                        <p>
                            <c:choose>
                                <c:when test="${contract.blockedByOperator}">
                                    Blocked <a href="unblock?id=${contract.id}"><i class="fa fa-unlock fa-fw"></i></a>
                                </c:when>
                                <c:otherwise>
                                    Active <a href="block?id=${contract.id}"><i class="fa fa-lock fa-fw"></i></a>
                                </c:otherwise>
                            </c:choose>

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
                                        <th></th>
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
                                                <a onclick="disableOption(${contract.id},${option.id})">
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
                                        <th></th>
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
                                                <a onclick="enableOption(${contract.id},${option.id})">
                                                    <i class="fa fa-plus fa-fw"></i>
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
                        <input id="submit" type="submit" class="btn btn-primary" value="Create"
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
