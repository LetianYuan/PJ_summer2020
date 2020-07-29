<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if(session.getAttribute("collectStatus") != null)
    {
%>
<script>
    let status = "<%=session.getAttribute("collectStatus")%>";
    $(function()
    {
        showTips(status, TIP_SUCCESS);
    });
</script>
<%
        session.removeAttribute("collectStatus");
    }
%>