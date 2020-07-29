<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>登陆</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/login.css">
</head>
<body>
<%
    boolean error = false;
    String errorCSS = "";
    String errorUserName = "";
    String errorInfo = "";
    String confirmCode = "";
    if(session.getAttribute("loginError") != null)//如果登陆失败
    {
        error = true;
        errorCSS = " style='height:530px'";
        if(request.getAttribute("confirmCodeError") != null)
            errorInfo = "<p style=\"color: red;margin: 0;\">验证码错误！</p>";
        else
            errorInfo = "<p style=\"color: red;margin: 0;\">用户名或密码错误！</p>";
        errorUserName = " value='" + request.getAttribute("userName") + "'";
        confirmCode = "<label for='confirmCode'>验证码:</label><br/><input type='text' id='confirmCode' name='confirmCode' required>" +
                "<br/><img src='ConfirmCode' id='confirmCodeImg'></img>&nbsp;&nbsp;<a href='javascript:' id='changeConfirmCode'>看不清，换一张</a><br/><br/>";
    }
%>
<form action="login" method="post" id="loginForm">
    <fieldset <%= errorCSS %>>
        <legend>登陆</legend>
        <section>
            <label for="user_name">用户名:</label>
            <br/>
            <input type="text" id="user_name" name="userName" <%= errorUserName %> required>
            <br/>
            <label for="plain_password">密码:</label>
            <br/>
            <input type="password" id="plain_password" name="plain_password" required>
            <input type="password" id="password" name="password" style="display: none">
            <br/>
            <%= confirmCode%>
            <%= errorInfo %>
            <input type="submit" value="登陆">
            <br/>
            <p>没有账号? <a href="register">注册一个!</a></p>
        </section>
    </fieldset>
</form>
<script src="js/sha256.js"></script>
<script src="js/login_encryptPassword.js"></script>
<%
    if(error)
    {
%>
<script src="js/XHR.js"></script>
<script src="js/login_changeConfirmCode.js"></script>
<%
    }
%>

<footer class="copyright">
    <span class="copyright">Copyright &copy;2020 Letian Yuan. (旦)-学习性-2020-0001&nbsp;&nbsp;&nbsp;旦公网安备19302010019号&nbsp;&nbsp;&nbsp;旦ICP证000019号</span>
</footer>
<%--花里胡哨的动画，摘自 https://csdoker.com/ --%>
<script src="js/ribbons-effect.js"></script>
<script src="js/startRibbonsEffect.js"></script>
</body>

</html>