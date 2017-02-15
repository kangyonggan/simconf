<#if conf.isDeleted == 1>
<a href="javascript:" data-role="conf-delete" title="恢复配置"
   data-url="${ctx}/dashboard/conf/${conf.id}/undelete?projCode=${project.code}">
    <span class="label label-danger arrowed-in">已删除</span>
</a>
<#else>
<a href="javascript:" data-role="conf-delete" title="删除配置"
   data-url="${ctx}/dashboard/conf/${conf.id}/delete?projCode=${project.code}">
    <span class="label label-success arrowed-in">未删除</span>
</a>
</#if>