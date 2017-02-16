<#assign ctx="${(rca.contextPath)!''}">

<tr id="project-${project.id}">
    <td>${project.code}</td>
    <td>${project.name}</td>
    <td>${project.createUsername}</td>
    <td><#include "delete.ftl"></td>
    <td><@c.relative_date datetime=project.createdTime/></td>
    <td>
        <div class="btn-group">
            <a class="btn btn-xs btn-inverse" href="#conf?projCode=${project.code}">配置</a>

            <button data-toggle="dropdown" class="btn btn-xs btn-inverse dropdown-toggle">
                <span class="ace-icon fa fa-caret-down icon-only"></span>
            </button>

            <ul class="dropdown-menu dropdown-menu-right dropdown-inverse">
                <li>
                    <a href="${ctx}/dashboard/project/${project.id}/edit" data-toggle="modal" data-target="#myModal"
                       data-backdrop="static">编辑</a>
                </li>
            <#if project.isDeleted==1>
                <li>
                    <a href="javascript:" data-role="project-remove" title="彻底删除项目"
                       data-url="${ctx}/dashboard/project/${project.id}/remove">物理删除</a>
                </li>
            </#if>
                <li>
                    <a href="${ctx}/dashboard/project/${project.id}/push" data-toggle="modal" data-target="#myModal"
                       data-backdrop="static">推送配置</a>
                </li>
            </ul>
        </div>
    </td>
</tr>