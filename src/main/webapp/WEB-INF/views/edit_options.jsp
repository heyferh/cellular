<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>Edit option</title>
</head>

<body>
<jsp:include page="navigation.jsp"></jsp:include>
<div id="wrapper">
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h2 class="page-header">Manage option dependencies</h2>
            </div>
            <div class="row">
                <div class="col-lg-10">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            ${option.title}
                        </div>
                        <div class="panel-body">
                            <form action=${pageContext.request.contextPath}/option/manage method="POST">
                                <div class="col-lg-6">
                                    <div class="form-group">
                                        <label>Required options</label>
                                        <c:choose>
                                            <c:when test="${not empty optionList}">
                                                <c:forEach var="option" items="${optionList}">
                                                    <div class="checkbox required">
                                                        <label>
                                                            <input name="required" value="${option.id}" type="checkbox"
                                                                   onchange="check(${option.id}, '.incompatible')">${option.title}
                                                        </label>
                                                    </div>
                                                </c:forEach>
                                                <input type="hidden" name="option_id" value="${option.id}">
                                                <input type="submit" class="btn btn-primary" value="Submit">
                                            </c:when>
                                            <c:otherwise>
                                                <div>There is no options yet.</div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                                <div class="col-lg-6">
                                    <div class="form-group">
                                        <label>Incompatible options</label>
                                        <c:choose>
                                            <c:when test="${not empty optionList}">
                                                <c:forEach var="option" items="${optionList}">
                                                    <div class="checkbox incompatible">
                                                        <label>
                                                            <input name="incompatible" value="${option.id}"
                                                                   type="checkbox"
                                                                   onchange="check(${option.id},'.required')">${option.title}
                                                        </label>
                                                    </div>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <div>There is no options yet.</div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    <c:forEach var="option" items="${option.requiredOptions}">
    $(".required input[value=" + ${option.id} +"]").prop("checked", true);
    </c:forEach>
    <c:forEach var="option" items="${option.incompatibleOptions}">
    $(".incompatible input[value=" + ${option.id} +"]").prop("checked", true);
    </c:forEach>
</script>
</body>

</html>
