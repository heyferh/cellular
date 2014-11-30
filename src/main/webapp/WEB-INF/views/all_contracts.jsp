<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>All contracts</title>
</head>

<body>
<jsp:include page="navigation.jsp"></jsp:include>
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h2 class="page-header">All contracts</h2>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-10">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    All existing contracts
                </div>
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
                                        <td style="cursor: pointer"
                                            onclick="javascript:location.href='contract_details?id=${contract.id}';">
                                                ${contract.phoneNumber}
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
                                        <td style="cursor: pointer"
                                            onclick="javascript:location.href='${pageContext.request.contextPath}/contract/add_another?client_id=${client.id}'">
                                            +
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        $('#dataTables-example').dataTable();
    });
</script>
</body>
</html>
