<%@ taglib prefix="nav" uri="navigator" %>
<%@ page import="java.util.List" %>
<%@ page import="databaseRelated.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>好友列表</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/friends.css">
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
    <script defer src="js/friends_removeSuccess.js"></script>
</head>
<body>
<nav:nav para="NONE"/>
<main>
    <ul class="container">
        <li class="title">
            好友列表
        </li>
        <li class="content">
            <ul class="friends">
                <%
                    List<User> users = (List<User>) request.getAttribute("users");
                    int presentPage = (int) request.getAttribute("presentPage");
                    int totalPage = (int) request.getAttribute("totalPage");
                    int per = (int) request.getAttribute("per");
                    if(users.size() == 0)
                    {
                %>
                <li>
                    <h1>很抱歉，您还没有添加任何好友。</h1>
                </li>
                <%
                }
                else
                {
                    for(int i = (presentPage - 1) * per; i < presentPage * per && i < users.size(); i++)
                    {
                        User e = users.get(i);
                %>
                <li>
                    <span class="user_info">
                        <strong class="username"
                                onclick="window.location.href='seekFavourite?uid=<%=e.getUID()%>'"><%=e.getUserName()%>
                        </strong><br/>
                        <p class="email"><%=e.getEmail()%>
                        </p>
                        <p class="login_time"><%=e.getDateJoinedString()%>
                        </p>
                    </span>
                    <span>
                        <button class="delete" id="delete_<%=e.getUID()%>" value="删除">删除</button>
                        <script>
                            document.getElementById('delete_<%=e.getUID()%>').onclick = function()
                            {
                                if(confirm("你确定要删除该好友吗？"))
                                {
                                    window.location.href = 'DeleteFriendServlet?userName=<%=e.getUserName()%>';
                                }
                            };
                        </script>
                        <br/>
                        <button class="chat" value="chat?chatTo=<%=e.getUserName()%>">聊天<sup
                                style="color: red;font-weight: bold;"><%
                            int historyMessagesCount = ((Number) e.getTmp()).intValue();
                            if(historyMessagesCount != 0) out.print(historyMessagesCount);
                        %></sup>
                        </button>
                    </span>
                </li>
                <%
                    }
                %>
                <div class="page_number">
                    <a href="friends?page=<%=Math.max(1,presentPage-1)%>" title="向前一页"><<</a>
                    <%
                        for(int i = 1; i <= totalPage; i++)
                        {
                    %>
                    <a href="friends?page=<%=i%>" title="<%=i%>" <%=(i == presentPage ? " class='active'" : "")%>><%=i%>
                    </a>
                    <%
                        }
                    %>
                    <a href="friends?page=<%=Math.min(totalPage,presentPage+1)%>" title="向后一页">>></a>
                </div>
                <%
                    }
                %>
            </ul>
        </li>
    </ul>
</main>
<footer class="copyright">
    <span class="copyright">Copyright &copy;2020 Letian Yuan. (旦)-学习性-2020-0001&nbsp;&nbsp;&nbsp;旦公网安备19302010019号&nbsp;&nbsp;&nbsp;旦ICP证000019号</span>
</footer>
<div id="iframe_container" hidden>
    <div id="iframe_head"><span id="iframe_title">Title</span><img src="img/close.png" id="iframe_close" alt="关闭"/>
    </div>
    <iframe src="" id="iframe">
    </iframe>
</div>
<script src="js/friends_showChat.js"></script>
</body>
</html>