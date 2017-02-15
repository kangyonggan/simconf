<#assign ctx="${(rca.contextPath)!''}">
<#assign modal_title="${project.id???string('编辑项目', '添加新项目')}" />

<@override name="modal-body">
<form class="form-horizontal" role="form" id="modal-form" method="post"
      action="${ctx}/dashboard/project/${project.id???string('update', 'save')}">
    <#if project.id??>
        <input type="hidden" name="id" value="${project.id}"/>
    </#if>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>项目代码<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "project.code" 'class="form-control" placeholder="请输入项目代码"'/>
                <input type="hidden" id="old-code" value="${project.code!''}"/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>项目名称<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "project.name" 'class="form-control" placeholder="请输入项目名称"'/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>推送地址</label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "project.pushUrl" 'class="form-control" placeholder="请输入推送地址"'/>
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

<script src="${ctx}/static/app/js/dashboard/project/form-modal.js"></script>
</@override>

<@extends name="../../modal-layout.ftl"/>