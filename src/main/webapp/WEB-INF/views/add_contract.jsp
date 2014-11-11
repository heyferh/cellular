<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>Add contract</title>
</head>

<body>
<jsp:include page="navigation.jsp"></jsp:include>
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h2 class="page-header">Add contract</h2>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-10">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    Step 1. Set phone number
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <div class="col-lg-6">
                        <div class="form-group">
                            <label>Phone number</label>
                            <input class="form-control" placeholder="" type="number">
                        </div>
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
                    Step 2. Choose tariff
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <div class="form-group">
                        <label>Radio Buttons</label>

                        <div class="radio">
                            <label>
                                <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1">Radio 1
                            </label>
                        </div>
                        <div class="radio">
                            <label>
                                <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2">Radio 2
                            </label>
                        </div>
                        <div class="radio">
                            <label>
                                <input type="radio" name="optionsRadios" id="optionsRadios3" value="option3">Radio 3
                            </label>
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
                    Step 3. Choose options
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <div class="form-group">
                        <label>Checkboxes</label>

                        <div class="checkbox">
                            <label>
                                <input type="checkbox" value="">Checkbox 1
                            </label>
                        </div>
                        <div class="checkbox">
                            <label>
                                <input type="checkbox" value="">Checkbox 2
                            </label>
                        </div>
                        <div class="checkbox">
                            <label>
                                <input type="checkbox" value="">Checkbox 3
                            </label>
                        </div>
                        <input type="submit" class="btn btn-primary">
                    </div>
                </div>
            </div>
        </div>
        <!-- /.col-lg-10 -->
    </div>
</div>
<!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->
</body>

</html>
