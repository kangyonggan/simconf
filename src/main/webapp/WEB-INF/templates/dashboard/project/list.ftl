<#assign ctx="${(rca.contextPath)!''}">
<#assign name = RequestParameters.name!'' />

<div class="page-header">
    <h1>
        项目列表
        <small class="pull-right">
            <a href="${ctx}/dashboard/project/create" class="btn btn-sm btn-inverse" data-toggle="modal" data-target="#myModal"
               data-backdrop="static">添加</a>
        </small>
    </h1>
</div>

<div class="space-10"></div>

<form class="form-inline" method="get">
    <div class="form-group">
        <input type="text" class="form-control" name="name" value="${name}" placeholder="项目名称"
               autocomplete="off"/>
    </div>

    <button class="btn btn-sm btn-inverse" data-toggle="search-submit">
        搜索
        <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
    </button>
</form>

<div class="space-10"></div>

<table id="project-table" class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th>项目代码</th>
        <th>项目名称</th>
        <th>负责人</th>
        <th>推送地址</th>
        <th>逻辑删除</th>
        <th>创建时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <#if page.list?size gt 0>
        <#list page.list as project>
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
<@c.pagination url="#project" param="name=${name}"/>

<script src="${ctx}/static/app/js/dashboard/project/list.js"></script>
