<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if(session.getAttribute("loginSuccess") != null)
    {
%>
<script>
    $(function()
    {
        showTips('欢迎，' + $.cookie('userName'), TIP_SUCCESS);
    });
</script>
<%
        session.removeAttribute("loginSuccess");
    }
%>