<#list envs as e>
    <#if e.env==conf.env>
    ${e.desc}[${e.env}]
    </#if>
</#list>