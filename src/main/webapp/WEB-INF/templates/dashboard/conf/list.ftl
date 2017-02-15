<#assign ctx="${(rca.contextPath)!''}">
<#assign env = RequestParameters.env!'' />
<#assign name = RequestParameters.name!'' />

<div class="page-header">
    <h1>
        配置列表
        <small class="pull-right">
            <a href="${ctx}/dashboard/conf/create?projCode=${project.code}" class="btn btn-sm btn-inverse" data-toggle="modal" data-target="#myModal"
               data-backdrop="static">添加</a>
        </small>
    </h1>
</div>

<div class="space-10"></div>

<form class="form-inline" method="get">
    <input type="hidden" name="projCode" value="${project.code}"/>
    <div class="form-group">
        <select class="form-control" name="env">
            <#list envs as e>
                <option value="${e.env}" <#if env==e.env>selected</#if>>${e.desc}[${e.env}]</option>
            </#list>
        </select>
    </div>
    <div class="form-group">
        <input type="text" class="form-control" name="name" value="${name}" placeholder="配置名称"
               autocomplete="off"/>
    </div>

    <button class="btn btn-sm btn-inverse" data-toggle="search-submit">
        搜索
        <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
    </button>
</form>

<div class="space-10"></div>

<table id="conf-table" class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th>项目</th>
        <th>环境</th>
        <th>名</th>
        <th>值</th>
        <th>逻辑删除</th>
        <th>创建时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <#if page.list?size gt 0>
        <#list page.list as conf>
            <#include "table-tr.ftl"/>
        </#list>
    <#else>
    <tr>
        <td colspan="20">
            <div class="empty">暂无查询记录</div>
        </td>
    </tr>
    </#if>
    </tbody>
</table>
<@c.pagination url="#conf" param="projCode=${project.code}&env=${env}&name=${name}"/>

<script src="${ctx}/static/app/js/dashboard/conf/list.js"></script>
