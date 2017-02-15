<#assign ctx="${(rca.contextPath)!''}">

<div class="space-10"></div>

<form id="form" method="post" action="${ctx}/dashboard/password" class="form-horizontal">
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right">新密码<span class="red">*</span></label>

        <div class="col-sm-9">
            <input type="password" id="password" name="password" class="form-control"
                   placeholder="密码:6至20位的字母数字组合" autocomplete="off"/>
        </div>
    </div>

    <div class="space-4"></div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right">确认密码<span class="red">*</span></label>

        <div class="col-sm-9">
            <input type="password" name="rePassword" class="form-control" placeholder="密码:6至20位的字母数字组合"
                   autocomplete="off"/>
        </div>
    </div>

    <div class="clearfix form-actions">
        <div class="col-xs-offset-3">
            <button id="submit" class="btn btn-inverse" data-loading-text="正在提交...">
                <i class="ace-icon fa fa-check"></i>
            <@spring.message "app.button.save"/>
            </button>
        </div>
    </div>

</form>

<script src="${ctx}/static/app/js/dashboard/password.js"></script>

