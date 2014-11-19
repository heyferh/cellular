<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html lang="en">
<head>
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
        function addContract() {
            $.ajax({
                traditional: true,
                url: 'add_another',
                type: 'POST',
                data: {
                    client_id: ${client_id},
                    phoneNumber: $("#phoneNumber").val(),
                    tariff_id: $("input[name='tariff_id']").val(),
                    option_id: $("input[name='option_id']:checked").map(function () {
                        return parseInt($(this).val());
                    }).get()
                },
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        location.href = "all";
                    } else {
                        $('#addContractError').html(data).show();
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
            <h2 class="page-header">Add another contract</h2>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-10">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    Step 1. Choose phone number
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <div id="addContractError" class="alert alert-danger alert-dismissable" style="display: none">
                    </div>
                    <div class="col-lg-6">
                        <div class="form-group">
                            Phone number: <span id="phoneError" class="error"></span>
                            <input name="phoneNumber" id="phoneNumber" class="form-control" placeholder=""
                                   type="number" onchange="checkNumber()"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-10">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    Step 2. Choose tariff and options
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body">
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
                        <input id="submit" type="submit" class="btn btn-primary" value="Create" onclick="addContract()">
                        <input type="hidden" name="client_id" value="${client_id}">
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
</body>
</html>
