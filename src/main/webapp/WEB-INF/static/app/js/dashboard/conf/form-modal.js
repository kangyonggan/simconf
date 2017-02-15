$(function () {

    var $form = $('#modal-form');
    var $btn = $("#submit");

    $form.validate({
        rules: {
            name: {
                required: true,
                maxlength: 64
            },
            value: {
                required: true,
                maxlength: 512
            },
            description: {
                required: false,
                maxlength: 512
            }
        },
        submitHandler: function (form, event) {
            event.preventDefault();
            $btn.button('loading');
            $(form).ajaxSubmit({
                dataType: 'json',
                success: function (response) {
                    if (response.errCode == 'success') {
                        window.location.reload();
                    } else {
                        Message.error(response.errMsg);
                        $btn.button('reset');
                    }
                },
                error: function () {
                    Message.error("服务器内部错误，请稍后再试。");
                    $btn.button('reset');
                }
            });
        },
        errorPlacement: function (error, element) {
            error.appendTo(element.parent());
        },
        errorElement: "div",
        errorClass: "error"
    });
});