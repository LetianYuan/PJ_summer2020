<%@ taglib prefix="nav" uri="navigator" %>
<%@ page import="databaseRelated.Picture" %>
<%@ page import="java.util.List" %>
<%@ page import="databaseRelated.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%
        User he = (User) request.getAttribute("he");
    %>
    <title><%=he.getUserName()%>收藏</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/favourite.css">
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
    <%@include file="jsp/welcomeTip.jsp" %>
    <script defer src="js/getQueryVariable.js"></script>
    <script defer src="js/favourite_event.js"></script>
</head>
<body>
<nav:nav para="NONE"/>
<main>
    <ul class="container">
        <li class="title">
            <%=he.getUserName()%>的收藏
        </li>
        <li class="content">
            <ul class="pictures">
                <%
                    if(request.getAttribute("notAllowed") != null)
                    {
                %>
                <li>
                    <h1>很抱歉，你没有权限查看<%=he.getUserName()%>的收藏。</h1>
                </li>
                <%
                }
                else if(request.getAttribute("notFriend") != null)
                {
                %>
                <li>
                    <h1>很抱歉，你还不是<%=he.getUserName()%>的好友。</h1>
                </li>
                <%
                }
                else
                {
                    List<Picture> pictures = (List<Picture>) request.getAttribute("pictures");
                    int presentPage = (int) request.getAttribute("presentPage");
                    int totalPage = (int) request.getAttribute("totalPage");
                    int per = (int) request.getAttribute("per");
                    if(pictures.size() == 0)
                    {
                %>
                <li>
                    <h1>很抱歉，他还没有收藏任何图片。</h1>
                </li>
                <%
                }
                else
                {
                    for(int i = (presentPage - 1) * per; i < presentPage * per && i < pictures.size(); i++)
                    {
                        Picture e = pictures.get(i);
                %>
                <li>
                    <a href="pictureDetails?imageID=<%=e.getImageID()%>">
                        <img src="img/travel_images/small/<%=e.getPath()%>"
                             title="<%=e.getTitle()%>"
                             alt="<%=e.getPath()%>">
                    </a>
                    <span class="image_right_area">
                        <span class="image_description">
                            <strong class="image_title"><%=e.getTitle()%></strong><br/>
                            <%=e.getDescription()%>
                        </span>
                        <br/>
                    </span>
                </li>
                <%
                    }
                %>

                <div class="page_number">
                    <a href="favourite?uid=<%=he.getUID()%>&page=<%=Math.max(1,presentPage-1)%>" title="向前一页"><<</a>
                    <%
                        for(int i = 1; i <= totalPage; i++)
                        {
                    %>
                    <a href="favourite?uid=<%=he.getUID()%>&page=<%=i%>"
                       title="<%=i%>" <%=(i == presentPage ? " class='active'" : "")%>><%=i%>
                    </a>
                    <%
                        }
                    %>
                    <a href="favourite?uid=<%=he.getUID()%>&page=<%=Math.min(totalPage,presentPage+1)%>"
                       title="向后一页">>></a>
                </div>
                <%
                        }
                    }
                %>
            </ul>
        </li>
    </ul>
</main>
<footer class="copyright">
    <span class="copyright">Copyright &copy;2020 Letian Yuan. (旦)-学习性-2020-0001&nbsp;&nbsp;&nbsp;旦公网安备19302010019号&nbsp;&nbsp;&nbsp;旦ICP证000019号</span>
</footer>
</body>
</html>