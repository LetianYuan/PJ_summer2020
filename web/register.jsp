<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>注册</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/register.css">
</head>
<body>
<%
    boolean error = false;
    String errorUserName = "";
    String errorEmail = "";
    String errorConfirmCode = "";
    if(request.getAttribute("registerError") != null)//如果登陆失败
    {
        error = true;
        errorUserName = " value='" + request.getAttribute("userName") + "'";
        errorEmail = " value='" + request.getAttribute("email") + "'";
        if(request.getAttribute("confirmCodeError") != null)
        {
            errorConfirmCode = "验证码错误";
        }
    }
%>
<form action="register" method="post" id="registerForm">
    <fieldset>
        <legend>注册</legend>
        <section>
            <label for="user_name">用户名:</label>
            <br/>
            <input type="text" id="user_name" name="userName" <%= errorUserName %> required autocomplete="off">
            <br/>
            <label for="email">邮箱:</label>
            <br/>
            <input type="email" id="email" name="email" <%= errorEmail %> required autocomplete="off">
            <br/>
            <label for="password">密码:</label>
            <img src="img/password_danger.png" alt="密码强弱" title="密码强弱" id="password_safety_img">
            <span id="password_safety_word" style="color: red">弱</span>
            <br/>
            <input type="password" id="plain_password" name="plain_password" required autocomplete="off">
            <input type="password" id="password" name="password" style="display: none">
            <br/>
            <label for="password_confirm">确认密码:</label>
            <br/>
            <input type="password" id="plain_password_confirm" name="plain_password_confirm" required
                   autocomplete="off">
            <input type="password" id="password_confirm" name="passwordConfirm" style="display: none">
            <br/>
            <label for='confirmCode'>验证码:</label>
            <br/>
            <input type='text' id='confirmCode' name='confirmCode' required autocomplete="off">
            <br/>
            <img src='ConfirmCode' id='confirmCodeImg'/>&nbsp;&nbsp;<a
                href='javascript:' id='changeConfirmCode'>看不清，换一张</a>
            <br/>
            <p style="color: red;margin: 0;" id="error_info"><%= errorConfirmCode %>
            </p>
            <input type="submit" id="register_button" value="注册">
            <br/>
            <p>已有账号? <a href="login">去登陆吧!</a></p>
        </section>
    </fieldset>
    <script src="js/sha256.js"></script>
    <script src="js/XHR.js"></script>
    <script src="js/register_event.js"></script>
</form>
<%
    if(error)
    {
%>
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