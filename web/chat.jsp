<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>Apache Tomcat WebSocket Examples: Echo</title>
    <link rel="stylesheet" href="css/chat.css"/>
    <script src="js/dateFormat.js"></script>
</head>
<body>
<div id="console">
</div>
<textarea id="message" placeholder="按下Enter发送消息"></textarea>
</body>
<script>
    const SYSTEM = 1;
    const USER_INFO = 2;
    const MINE = 3;
    const CONTENT = 4;
    const NOT_MINE = 5;
    let ws = null;

    function connect()
    {
        if(target === '')
        {
            alert("未知错误，请重试重启浏览器");
            return;
        }
        if('WebSocket' in window)
        {
            ws = new WebSocket(target);
        }
        else if('MozWebSocket' in window)
        {
            ws = new MozWebSocket(target);
        }
        else
        {
            alert('浏览器不支持WebSocket，请尝试更换浏览器');
            return;
        }
        ws.onopen = function()
        {
            log('系统消息：连接成功，与您对话的是${sessionScope.chatTo}', SYSTEM);
        };
        ws.onmessage = function(event)
        {
            if(event.data === '＃')
            {
                log('系统消息：${sessionScope.chatTo}目前不在线', SYSTEM);
            }
            else if(event.data === '＃＃')
            {
                log('以上是历史消息', SYSTEM);
            }
            else
            {
                log(event.data.split('＃＃')[0], USER_INFO);
                log(event.data.split('＃＃')[1], CONTENT);
            }
        };
        ws.onclose = function(event)
        {
            log('系统消息：连接已断开，代码 ' + event.code + (event.reason == "" ? "" : ", 原因: " + event.reason), SYSTEM);
        };
    }

    function sendMessage()
    {
        if(ws != null)
        {
            let message = document.getElementById('message').value;
            if(message.trim() !== '')
            {
                log('${sessionScope.userName}＃' + dateFormat("YYYY/mm/dd HH:MM:SS", new Date()), USER_INFO, MINE);
                log(message, CONTENT, MINE);
                ws.send('${sessionScope.chatTo}＃' + message);
            }
        }
        else
        {
            alert('连接失效，请尝试重启浏览器');
        }
    }

    function log(message, status, isMine = NOT_MINE)
    {
        let console = document.getElementById('console');
        let p = document.createElement('p');
        if(isMine === MINE)
            p.classList.add('mine');
        switch(status)
        {
            case SYSTEM:
                p.classList.add('system');
                break;
            case CONTENT:
                p.classList.add('content');
                break;
            case USER_INFO:
                p.classList.add('user_info');
                break;
            default:
        }
        if(status === USER_INFO)
        {
            let span = document.createElement('span');
            span.classList.add('time');
            p.appendChild(document.createTextNode(message.split('＃')[0]));
            span.appendChild(document.createTextNode(message.split('＃')[1]));
            p.appendChild(span);
        }
        else
            p.appendChild(document.createTextNode(message));
        console.appendChild(p);
        // while(console.childNodes.length > 25)
        // {
        //     console.removeChild(console.firstChild);
        // }
        console.scrollTop = console.scrollHeight;
    }

    let target = "";
    if(window.location.protocol == 'http:')
    {
        target = 'ws://' + window.location.host + "<%=request.getContextPath()%>/chat/${sessionScope.userName}＃${sessionScope.chatTo}";
    }
    else
    {
        target = 'wss://' + window.location.host + "<%=request.getContextPath()%>/chat/${sessionScope.userName}＃${sessionScope.chatTo}";
    }

    connect();
    window.onbeforeunload = function()
    {
        ws.close();
    };
    let messageBox = document.getElementById('message');
    messageBox.onkeyup = function(event)
    {
        if(event.keyCode === 13)
        {
            sendMessage();
            messageBox.value = '';
        }
    };
</script>
</html>