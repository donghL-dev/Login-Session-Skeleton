$(`.sign-up`).click(function () {
    const jsonData = JSON.stringify({
        email: $(`#email`).val(),
        password: $(`#pwd`).val()
    });

    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url: "/sign-up",
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        dataType: "json",
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            alert(data['msg']);
            location.href = '/';
        },
        error: function (data) {
            alert(data.responseText);
        }
    });
});