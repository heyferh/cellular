function getOptions(tariff_id) {
    $.ajax({
        url: '/cellular/option/get_options?tariff_id=' + tariff_id,
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
function getOptions(tariff_id) {
    $.ajax({
        url: '/cellular/option/get_options?tariff_id=' + tariff_id,
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
        url: '/cellular/contract/check_number',
        type: 'POST',
        data: {'number': $("#phoneNumber").val()},
        success: function (data) {
            if (!$.isEmptyObject(data)) {
                $("#phoneNumberError").text(data);
            } else {
                $("#phoneNumberError").empty();
            }
        }
    });
}
function removeTariffOption(id, tariff_id) {
    $.ajax({
        url: 'disable',
        type: 'GET',
        data: {id: tariff_id, optionID: id},
        success: function (data) {
            if ($.isEmptyObject(data)) {
                location.href = "edit?id=" + tariff_id;
            } else {
                $('#deleteError').html(data).show();
            }

        }
    });
}
function addTariffOption(id, tariff_id) {
    $.ajax({
        url: 'enable',
        type: 'GET',
        data: {id: tariff_id, optionID: id},
        success: function (data) {
            if ($.isEmptyObject(data)) {
                location.href = "edit?id=" + tariff_id;
            } else {
                $('#deleteError').html(data).show();
            }

        }
    });
}
function check(id, className) {
    $(className + " input[value=" + id + "]").prop("checked", false);
}
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
function changeTariff(contract_id) {
    var options = $("input[name='option_id']:checked").map(function () {
        return parseInt($(this).val());
    }).get();
    if ($("input[name='option_id']:checked").size() == 0) {
        $('#changeTariffError').html("Choose tariff and options").show();
    } else {
        $.ajax({
            traditional: true,
            url: 'change_tariff',
            type: 'POST',
            data: {
                tariff_id: $("input[name='tariff_id']:checked").val(),
                contract_id: contract_id,
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
}
function addTariff() {
    if ($("#cost").val().length == 0) {
        $("#costError").html("may not be empty").show();
    }
    if ($("#cost").val().length > 0) {
        this.submit();
    }
}
function deleteTariff(id) {
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
function addContract(client_id) {
    $.ajax({
        traditional: true,
        url: 'add_another',
        type: 'POST',
        data: {
            client_id: client_id,
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