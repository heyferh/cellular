<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <script>
        function removeTariffOption(id) {
            $.ajax({
                url: 'disable',
                type: 'GET',
                data: {id: ${tariff.id}, optionID: id},
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        location.href = "edit?id=" +${tariff.id};
                    } else {
                        $('#deleteError').html(data).show();
                    }

                }
            });
        }
        function addTariffOption(id) {
            $.ajax({
                url: 'enable',
                type: 'GET',
                data: {id: ${tariff.id}, optionID: id},
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        location.href = "edit?id=" +${tariff.id};
                    } else {
                        $('#deleteError').html(data).show();
                    }

                }
            });
        }
    </script>
</head>

<body>
<jsp:include page="navigation.jsp"></jsp:include>
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h2 class="page-header">Manage tariff options</h2>
        </div>

        <div class="row">
            <div class="col-lg-10">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        ${tariff.title}
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
                                            <th>Activation Cost</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="option" items="${tariff.options}">
                                            <tr>
                                                <td>
                                                    <a onclick="removeTariffOption(${option.id})">
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
                                        <c:forEach var="option" items="${availableOptions}">
                                            <tr>
                                                <td>
                                                    <a onclick="addTariffOption(${option.id})">
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

    </div>
    <!-- /#page-wrapper -->
</div>
</body>
</html>
