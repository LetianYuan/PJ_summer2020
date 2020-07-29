<%@ taglib prefix="nav" uri="navigator" %>
<%@ page import="databaseRelated.Picture" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>图片详情</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/picture_details.css">
    <script src="https://cdn.staticfile.org/jquery/3.4.0/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
    <script>
        if(typeof jQuery == 'undefined')//如果cdn无法访问
        {
            document.write(unescape("%3Cscript src='js/jquery-3.4.1.min.js' %3E%3C/script%3E"));
            document.write(unescape("%3Cscript src='js/jquery.cookie.js' %3E%3C/script%3E"));
        }
    </script>
    <script defer src="js/showTips.js"></script>
    <%@include file="jsp/collectSuccessTip.jsp" %>
</head>
<body>
<nav:nav para="NONE"/>
<%
    Picture pic = (Picture) request.getAttribute("pic");
    String userName = (String) request.getAttribute("userName");
    if(pic != null)
    {
%>
<div class="vague_border">
    <img src="img/travel_images/large/<%=pic.getPath()%>"
         title="<%=pic.getTitle()%>"
         alt="<%=pic.getPath()%>"
         class="picture">
</div>
<table class="picture_description">
    <caption>图片详情</caption>
    <tr>
        <th>标题</th>
        <td><%=pic.getTitle()%>
        </td>
    </tr>
    <tr>
        <th>作者</th>
        <td><%=pic.getAuthor()%>
        </td>
    </tr>
    <tr>
        <th>主题</th>
        <td><%=pic.getContent()%>
        </td>
    </tr>
    <tr>
        <th>时间</th>
        <td><%=pic.getUpdateTimeString()%>
        </td>
    </tr>
    <tr>
        <th>国家</th>
        <td><%=pic.getCountry()%>
        </td>
    </tr>
    <tr>
        <th>城市</th>
        <td><%=pic.getCity()%>
        </td>
    </tr>
    <tr>
        <th>热度</th>
        <td><%=pic.getFavors()%>
        </td>
    </tr>
    <tr>
        <th>描述</th>
        <td><%=pic.getDescription()%>
        </td>
    </tr>
</table>
<div class="interact">
    <a href="CollectServlet?imageID=<%=pic.getImageID()%>" class="collect" id="collect"><img src="img/empty.png"
                                                                                             alt="收藏" title="收藏">
        <span id="collect_info">
            <%=((userName != null && pic.isFavored(userName)) ? "已" : "")%>收藏 <%=pic.getFavors()%></span></a>
</div>

<%
}
else
{
%>
<h1>Image Not Found</h1>
<%
    }
%>
<footer class="copyright">
    <span class="copyright">Copyright &copy;2020 Letian Yuan. (旦)-学习性-2020-0001&nbsp;&nbsp;&nbsp;旦公网安备19302010019号&nbsp;&nbsp;&nbsp;旦ICP证000019号</span>
</footer>
</body>
</html>