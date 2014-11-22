<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">

<head>
</head>

<body>
<jsp:include page="navigation.jsp"></jsp:include>
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h2 class="page-header">All contracts</h2>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-10">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    All existing contracts
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                            <thead>
                            <tr>
                                <th>Firstname</th>
                                <th>Lastname</th>
                                <th>Phone number</th>
                                <th>Balance</th>
                                <th>Status</th>
                                <th>Add one more</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="client" items="${clientList}">
                                <c:forEach var="contract" items="${client.contracts}">
                                    <tr>
                                        <td>${client.firstName}</td>
                                        <td>${client.lastName}</td>
                                        <td>
                                            <a href="contract_details?id=${contract.id}">
                                                    ${contract.phoneNumber}
                                            </a>
                                        </td>
                                        <td>${contract.balance}</td>
                                        <c:choose>
                                            <c:when test="${contract.blockedByClient}">
                                                <td>Blocked by client</td>
                                            </c:when>
                                            <c:when test="${contract.blockedByOperator}">
                                                <td>Blocked by operator</td>
                                            </c:when>
                                            <c:otherwise>
                                                <td>Active</td>
                                            </c:otherwise>
                                        </c:choose>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/admin/contract/add_another?client_id=${client.id}">
                                                <i class="fa fa-plus fa-fw"></i>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <!-- /.table-responsive -->
                </div>
                <!-- /.panel-body -->
            </div>
            <!-- /.panel -->
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
</div>
<!-- /.row -->
</div>
<!-- /#page-wrapper -->

</div>
<script>
    $(document).ready(function () {
        $('#dataTables-example').dataTable();
    });
</script>

</body>

</html>
