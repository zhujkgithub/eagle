<#if user?? && user?size gt 0>
<#list user as u>

<div class="lawyerItem">
    <img src="${rc.contextPath}/images/lawyer.jpg" alt="">
    <div class="info">
        <div class="name">
            ${u.username!''}
        </div>
    </div>
</div>

</#list>
</#if>
