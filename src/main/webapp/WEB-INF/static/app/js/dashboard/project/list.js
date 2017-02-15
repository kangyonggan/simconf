$(function () {
    updateState("project");

    var $table = $('#project-table');

    $table.on('click', 'a[data-role=project-delete]', function () {
        var $trigger = $(this);
        var url = $trigger.data('url');

        var title = $trigger.attr("title");

        $.messager.confirm("提示", "确定" + title + "吗?", function () {
            $.get(url).success(function (html) {
                var $tr = $(html);
                $('#' + $tr.attr('id')).replaceWith($tr);
                Message.success("操作成功");
            }).error(function () {
                Message.error("网络错误，请稍后重试");
            })
        });
    });

    $table.on('click', 'a[data-role=project-remove]', function () {
        var $trigger = $(this);
        var url = $trigger.data('url');

        var title = $trigger.attr("title");

        $.messager.confirm("提示", "确定" + title + "吗?", function () {
            $.get(url).success(function () {
                window.location.reload();
            }).error(function () {
                Message.error("网络错误，请稍后重试");
            })
        });
    });
});