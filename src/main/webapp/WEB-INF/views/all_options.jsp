<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <script>
        function addOption() {
            if ($("#cost").val().length == 0) {
                $("#costError").html("may not be empty").show();
            }
            if ($("#activationCost").val().length == 0) {
                $("#activationCostError").html("may not be empty").show();
            }
            if ($("#cost").val().length > 0 &&
                    $("#activationCost").val().length > 0) {
                this.submit();
            }
        }
        function deleteOption(id) {
            $.ajax({
                url: 'delete?id=' + id,
                type: 'GET',
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        location.href = "all";
                    } else {
                        $('#deleteError').html(data).show();
                    }

                }
            });
        }
        function check(id, className) {
            $(className + " input[value=" + id + "]").prop("checked", false);
        }
    </script>
</head>

<body>
<jsp:include page="navigation.jsp"></jsp:include>
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-10">
            <h2 class="page-header">All Options</h2>
        </div>
        <!-- /.col-lg-12 -->
        <div class="row">
            <div class="col-lg-10">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        Existing options
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <div id="deleteError" class="alert alert-danger alert-dismissable" style="display: none">
                        </div>
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>Status</th>
                                    <th>Title</th>
                                    <th>Cost</th>
                                    <th>Activation cost</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="option" items="${optionList}">
                                    <tr>
                                        <td>
                                            <a class="deleteOption" onclick="deleteOption(${option.id})">
                                                <i class="fa fa-times fa-fw"></i>
                                            </a>
                                        </td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/option/edit?id=${option.id}">
                                                    ${option.title}
                                            </a>
                                        </td>
                                        <td>${option.cost}</td>
                                        <td>${option.activationCost}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <!-- /.table-responsive -->
                    </div>
                    <!-- /.panel-body -->
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-10">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        Add new option
                    </div>
                    <!-- /.panel-heading -->
                    <form:form action="${pageContext.request.contextPath}/option/all" commandName="optionBean"
                               onsubmit="addOption();return false;">
                        <div class="panel-body">
                            <div class="col-lg-6">
                                <div class="form-group">
                                    Title:
                                    <form:errors path="title" cssClass="error"/>
                                    <form:input path="title" name="title" type="text" class="form-control"
                                                placeholder="Enter title"
                                                id="title"/>
                                </div>
                                <div class="form-group">
                                    Cost:
                                    <span id="costError" class="error" style="display: none"></span>
                                    <form:errors path="cost" cssClass="error"/>
                                    <form:input path="cost" name="cost" type="number" class="form-control"
                                                placeholder="Enter cost"
                                                id="cost"/>
                                </div>
                                <div class="form-group">
                                    Activation Cost:
                                    <span id="activationCostError" class="error" style="display: none"></span>
                                    <form:errors path="activationCost" cssClass="error"/>
                                    <form:input path="activationCost" name="activationCost" type="number"
                                                class="form-control"
                                                placeholder="Enter activation cost"
                                                id="activationCost"/>
                                </div>
                                <input id="submit" type="submit" class="btn btn-primary" value="OK">
                            </div>
                            <div class="col-lg-3">
                                <div class="form-group">
                                    <label>Required options</label>
                                    <c:forEach var="option" items="${optionList}">
                                        <div class="checkbox required">
                                            <label>
                                                <input name="requiredOptions" value="${option.id}"
                                                       type="checkbox"
                                                       onchange="check(${option.id}, '.incompatible')">${option.title}
                                            </label>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                            <div class="col-lg-3">
                                <div class="form-group">
                                    <label>Incompatible options</label>
                                    <c:forEach var="option" items="${optionList}">
                                        <div class="checkbox incompatible">
                                            <label>
                                                <input name="incompatibleOptions" value="${option.id}"
                                                       type="checkbox"
                                                       onchange="check(${option.id}, '.required')">${option.title}
                                            </label>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
        <!-- /#page-wrapper -->
    </div>
    <!-- /#wrapper -->
    <script>
        $(document).ready(function () {
            $('#dataTables-example').dataTable();
        });
    </script>
</div>
</body>

</html>
