package websocket.chat;

import databaseRelated.Message;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat/{userName}")
public class Chat
{
    //concurrent包的线程安全Set，用来存放每个客户端对应的Chat对象。
    //若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static ConcurrentHashMap<String, Chat> webSocketSet = new ConcurrentHashMap<>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session websocketSession;
    private String fromUser = "";
    private String toUser = "";

    public String getFromUser()
    {
        return fromUser;
    }

    public String getToUser()
    {
        return toUser;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(@PathParam(value = "userName") String param, Session websocketSession, EndpointConfig config) throws IOException
    {
        //当前发消息的人员
        fromUser = param.split("＃")[0];
        toUser = param.split("＃")[1];
        this.websocketSession = websocketSession;
        webSocketSet.put(fromUser, this);//加入map中
        List<Message> messages = Message.getAndDeleteHistoryMessages(toUser, fromUser);
        if(messages.size() > 0)
        {
            for(Message e : messages)
            {
                webSocketSet.get(this.fromUser).sendMessage(e.getFromUser() + "＃" + e.getTimeString() + "＃＃" + e.getContent());
            }
            webSocketSet.get(this.fromUser).sendMessage("＃＃");//##表示以上是历史消息
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose()
    {
        if(!fromUser.equals(""))
        {
            webSocketSet.remove(fromUser);  //从set中删除
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @SuppressWarnings("unused")
    public void onMessage(String message, Session session)
    {
        //给指定的人发消息
        sendToUser(message);
    }

    /**
     * 给指定的人发送消息
     */
    @OnMessage
    public void sendToUser(String message)
    {
        String sendUserName = message.split("＃")[0];
        String sendMessage = message.split("＃")[1];
        String now = getNowTime();
        try
        {
            if(webSocketSet.get(sendUserName) != null && webSocketSet.get(sendUserName).getToUser().equals(fromUser))
            {
                webSocketSet.get(sendUserName).sendMessage(this.fromUser + "＃" + now + "＃＃" + sendMessage);
            }
            else
            {
                Message.addHistoryMessages(fromUser, toUser, sendMessage,
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                webSocketSet.get(this.fromUser).sendMessage("＃");//#表示对方不在线
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前时间
     */
    private String getNowTime()
    {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error)
    {
        error.printStackTrace();
    }

    /**
     * 发送消息
     */
    public void sendMessage(String message) throws IOException
    {
        this.websocketSession.getBasicRemote().sendText(message);
    }
}
