<%@ page import="databaseRelated.Picture" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="nav" uri="navigator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <title>首页</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/index.css">
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
</head>

<body>
<nav:nav para="HOME"/>
<div class="float_button">
    <img src="img/back_to_top.png" title="回到页面顶部" id="back_to_top" class="back_to_top" alt="back_to_top">
    <script>
        var timer = null;
        back_to_top.onclick = function()
        {
            cancelAnimationFrame(timer);
            timer = requestAnimationFrame(function fn()
            {
                var oTop = document.body.scrollTop || document.documentElement.scrollTop;
                if(oTop > 0)
                {
                    document.body.scrollTop = document.documentElement.scrollTop = oTop - 50;
                    timer = requestAnimationFrame(fn);
                }
                else
                {
                    cancelAnimationFrame(timer);
                }
            });
        }
    </script>
</div>

<%
    List<Picture> hottestPictures = (List<Picture>) request.getAttribute("hottestPictures");
%>
<div class="carousel_container">
    <div class="wrap">
        <%
            for(int i = 0; i < hottestPictures.size(); i++)
            {
                Picture e = hottestPictures.get(i);
        %>
        <img src="img/travel_images/large/<%=e.getPath()%>" alt="<%=i%>" <%=i==0?" class='active'":""%>
             onclick="window.location.href='pictureDetails?imageID=<%=e.getImageID()%>'">
        <%
            }
        %>
    </div>
    <div class="buttons">
        <span class="on">1</span>
        <span>2</span>
        <span>3</span>
        <span>4</span>
        <span>5</span>
    </div>
    <a href="javascript:" class="arrow arrow_left">&lt;</a>
    <a href="javascript:" class="arrow arrow_right">&gt;</a>
</div>
<script src="js/index_carousel.js"></script>
<script async src="js/home_picture_box_appear.js"></script>
<div class="hot_image_area">
    <%
        List<Picture> latestPictures = (List<Picture>) request.getAttribute("latestPictures");
        for(Picture e : latestPictures)
        {
    %>
    <div class="hot_image">
        <a href="pictureDetails?imageID=<%=e.getImageID()%>">
            <img src="img/travel_images/small/<%=e.getPath()%>"
                 title="<%=e.getTitle()%>"
                 alt="<%=e.getPath()%>">
        </a>
        <p class="image_title"><%=e.getTitle()%>
        </p>
        <p class="image_description"><span class="author"><%=e.getAuthor()%></span>
            <span><%=e.getUpdateTimeString()%></span><br/><span><%=e.getContent()%></span></p>
    </div>
    <%
        }
    %>
</div>
<footer class="copyright">
    <img src="img/weixin.jpg" alt="微信" title="联系我们">
    <span class="information">联系我们:19302010019@fudan.edu.cn<br/>
                              Copyright &copy;2020 Letian Yuan. All rights reserved.<br/>
                              (旦)-学习性-2020-0001&nbsp;&nbsp;&nbsp;旦公网安备19302010019号&nbsp;&nbsp;&nbsp;旦ICP证000019号</span>
</footer>
</body>
</html>