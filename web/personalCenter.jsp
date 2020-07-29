<%@ taglib prefix="nav" uri="navigator" %>
<%@ page import="databaseRelated.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>个人中心</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/personalCenter.css">
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
    <script src="js/XHR.js"></script>
    <script src="js/getQueryVariable.js"></script>
    <script defer src="js/personalCenter_event.js"></script>
</head>
<body>
<nav:nav para="NONE"/>
<main>
    <%
        User me = User.getUser((Number) session.getAttribute("UID"));
    %>
    <ul class="container">
        <li class="title">
            权限管理
        </li>
        <li class="content">
            <label><input type="checkbox" name="favouriteAllow" id="favouriteAllow"
                <%=me.getFavouriteOpen()==1?"checked":""%> >允许其他用户查看我的收藏列表</label>
        </li>
    </ul>

    <ul class="container">
        <li class="title">
            好友申请
        </li>
        <li class="content">
            <ul class="friends">
                <%
                    if(request.getAttribute("friendRequests") == null)
                    {
                %>
                <h1>暂时还没有用户向您发出好友申请。</h1>
                <%
                }
                else
                {
                    List<User> friendRequests = (List<User>) request.getAttribute("friendRequests");
                    for(User e : friendRequests)
                    {

                %>
                <li>
                    <span class="user_info">
                        <strong class="username"><%=e.getUserName()%>
                        </strong><br/>
                        <p class="email"><%=e.getEmail()%>
                        </p>
                        <p class="login_time"><%=e.getDateJoinedString()%>
                        </p>
                    </span>
                    <span>
                        <button class="refuse" id="refuse_<%=e.getUID()%>" value="拒绝">拒绝</button>
                        <br/>
                        <button class="accept" id="accept_<%=e.getUID()%>">同意</button>
                        <script>
                            document.getElementById('refuse_<%=e.getUID()%>').onclick = function()
                            {
                                window.location.href = 'refuseFriendRequest?userName=<%=e.getUserName()%>';
                            };
                            document.getElementById('accept_<%=e.getUID()%>').onclick = function()
                            {
                                window.location.href = 'addFriend?userName=<%=e.getUserName()%>';
                            };
                        </script>
                    </span>
                </li>
                <%
                        }
                    }
                %>
            </ul>
        </li>
    </ul>
    <ul class="container">
        <li class="title">
            添加好友
        </li>
        <li class="content">
            <input id="userNameInput" type="text" placeholder="输入用户名以查找"/>
            <span id="result"></span>
            <br/>
            <button id="addUser" value="发送请求" disabled class="disabled">发送请求</button>
        </li>
    </ul>

</main>
<footer class="copyright">
    <span class="copyright">Copyright &copy;2020 Letian Yuan. (旦)-学习性-2020-0001&nbsp;&nbsp;&nbsp;旦公网安备19302010019号&nbsp;&nbsp;&nbsp;旦ICP证000019号</span>
</footer>
</body>
</html>