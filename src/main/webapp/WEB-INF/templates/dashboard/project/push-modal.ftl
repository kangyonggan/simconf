<#assign ctx="${(rca.contextPath)!''}">
<#assign modal_title="推送配置" />

<@override name="modal-body">
<form class="form-horizontal" role="form" id="modal-form" method="post"
      action="${ctx}/dashboard/project/${project.id}/push">
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>推送项目</label>
            </div>
            <div class="col-md-7 controls">
                <input type="text" value="${project.name}[${project.code}]" class="form-control readonly" readonly/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>环境</label>
            </div>
            <div class="col-md-7 controls">
                <select name="env" class="form-control">
                    <#list envs as env>
                        <#if env.env != 'common'>
                            <option value="${env.env}">${env.desc}[${env.env}]</option>
                        </#if>
                    </#list>
                </select>
            </div>
        </div>
    </div>
</form>
</@override>

<@override name="modal-footer">
<button class="btn btn-sm" data-dismiss="modal">
    <i class="ace-icon fa fa-times"></i>
    <@spring.message "app.button.cancel"/>
</button>

<button class="btn btn-sm btn-inverse" id="submit" data-loading-text="正在保存..." data-toggle="form-submit"
        data-target="#modal-form">
    <i class="ace-icon fa fa-check"></i>
    <@spring.message "app.button.save"/>
</button>

<script src="${ctx}/static/app/js/dashboard/project/push-modal.js"></script>
</@override>

<@extends name="../../modal-layout.ftl"/>