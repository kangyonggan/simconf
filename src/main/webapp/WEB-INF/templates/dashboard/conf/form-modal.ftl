<#assign ctx="${(rca.contextPath)!''}">
<#assign projCode = RequestParameters.projCode!'' />
<#assign modal_title="${conf.id???string('编辑配置', '添加新配置')}" />

<@override name="modal-body">
<form class="form-horizontal" role="form" id="modal-form" method="post"
      action="${ctx}/dashboard/conf/${conf.id???string('update', 'save')}">
    <#if conf.id??>
        <input type="hidden" name="id" value="${conf.id}"/>
    <#else>
        <input type="hidden" name="projCode" value="${projCode}"/>
    </#if>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>环境<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <select name="env" class="form-control">
                    <#list envs as e>
                        <option value="${e.env}" <#if conf.id?? && conf.env==e.env>selected</#if>>${e.desc}[${e.env}]
                        </option>
                    </#list>
                </select>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>名<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "conf.name" 'class="form-control" placeholder="请输入名"'/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>值<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "conf.value" 'class="form-control" placeholder="请输入值"'/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>描述</label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "conf.description" 'class="form-control" placeholder="请输入配置描述"'/>
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

<script src="${ctx}/static/app/js/dashboard/conf/form-modal.js"></script>
</@override>

<@extends name="../../modal-layout.ftl"/>