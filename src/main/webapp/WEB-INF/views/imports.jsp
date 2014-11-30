<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link href="${pageContext.request.contextPath}/app/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/app/css/plugins/metisMenu/metisMenu.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/app/css/plugins/dataTables.bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/app/css/sb-admin-2.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/app/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet"
          type="text/css">
    <script src="${pageContext.request.contextPath}/app/js/jquery-1.11.0.js"></script>
    <script src="${pageContext.request.contextPath}/app/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/app/js/plugins/metisMenu/metisMenu.min.js"></script>
    <script src="${pageContext.request.contextPath}/app/js/plugins/dataTables/jquery.dataTables.js"></script>
    <script src="${pageContext.request.contextPath}/app/js/plugins/dataTables/dataTables.bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/app/js/sb-admin-2.js"></script>
    <script src="${pageContext.request.contextPath}/app/js/my.js"></script>
    <style>
        .error {
            color: red;
            font-weight: bold;
        }
    </style>
    <script>
        function disableOption(contract_id, option_id) {
            console.log("With client");
            $.ajax({
                url: 'disable_option',
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
            console.log("With client");
            var options = $("input[name='option_id']:checked").map(function () {
                return parseInt($(this).val());
            }).get();
            $.ajax({
                traditional: true,
                url: 'change_tariff',
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

</body>
</html>
