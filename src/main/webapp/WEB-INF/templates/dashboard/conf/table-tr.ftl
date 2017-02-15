<#assign ctx="${(rca.contextPath)!''}">

<tr id="conf-${conf.id}">
    <td>${project.name}[${project.code}]</td>
    <td><#include "env.ftl"/></td>
    <td>${conf.name}</td>
    <td>${conf.value}</td>
    <td><#include "delete.ftl"></td>
    <td><@c.relative_date datetime=conf.createdTime/></td>
    <td>
        <div class="btn-group">
            <a data-toggle="modal" class="btn btn-xs btn-inverse" href="${ctx}/dashboard/conf/${conf.id}/edit"
               data-target="#myModal">编辑</a>

        <#if conf.isDeleted == 1>
            <button data-toggle="dropdown" class="btn btn-xs btn-inverse dropdown-toggle">
                <span class="ace-icon fa fa-caret-down icon-only"></span>
            </button>

            <ul class="dropdown-menu dropdown-menu-right dropdown-inverse">
                <li>
                    <a href="javascript:" data-role="conf-remove" title="彻底删除配置"
                       data-url="${ctx}/dashboard/conf/${conf.id}/remove">
                        物理删除
                    </a>
                </li>
            </ul>
        </#if>
        </div>
    </td>
</tr>