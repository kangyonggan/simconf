<#if project.isDeleted == 1>
<a href="javascript:" data-role="project-delete" title="恢复项目"
   data-url="${ctx}/dashboard/project/${project.id}/undelete">
    <span class="label label-danger arrowed-in">已删除</span>
</a>
<#else>
<a href="javascript:" data-role="project-delete" title="删除项目"
   data-url="${ctx}/dashboard/project/${project.id}/delete">
    <span class="label label-success arrowed-in">未删除</span>
</a>
</#if>