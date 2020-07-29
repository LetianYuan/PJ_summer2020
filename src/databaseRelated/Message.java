package databaseRelated;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Message//用户聊天所用
{
    private int messageID;
    private String fromUser;
    private String toUser;
    private String content;
    private Timestamp time;

    public Message()
    {
    }

    @Override
    public String toString()
    {
        return "Message{" +
                "messageID=" + messageID +
                ", fromUser='" + fromUser + '\'' +
                ", toUser='" + toUser + '\'' +
                ", content='" + content + '\'' +
                ", time=" + getTimeString() +
                '}';
    }

    public int getMessageID()
    {
        return messageID;
    }

    public void setMessageID(int messageID)
    {
        this.messageID = messageID;
    }

    public String getFromUser()
    {
        return fromUser;
    }

    public void setFromUser(String fromUser)
    {
        this.fromUser = fromUser;
    }

    public String getToUser()
    {
        return toUser;
    }

    public void setToUser(String toUser)
    {
        this.toUser = toUser;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public Timestamp getTime()
    {
        return time;
    }

    public String getTimeString()
    {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date(time.getTime()));
    }

    public void setTime(Timestamp time)
    {
        this.time = time;
    }

    /**
     * 获得并删除所有的历史消息
     *
     * @param fromUser fromUser
     * @param toUser   toUser
     * @return 历史消息的列表
     */
    public static List<Message> getAndDeleteHistoryMessages(String fromUser, String toUser)
    {
        String sql = "SELECT messageID, fromUser, toUser, content, time FROM historymessage" +
                " WHERE fromUser = ? AND toUser = ?";
        List<Message> result = DAO.getForList(Message.class, sql, fromUser, toUser);
        sql = "DELETE FROM historymessage" +
                " WHERE fromUser = ? AND toUser = ?";
        DAO.update(sql, fromUser, toUser);
        return result;
    }

    /**
     * @param fromUser fromUser
     * @param toUser   toUser
     * @return 获得从fromUser到toUser有多少条未读消息
     */
    public static Number getHistoryMessageCount(String fromUser, String toUser)
    {
        String sql = "SELECT count(messageID) FROM historymessage" +
                " WHERE fromUser = ? AND toUser = ?";
        return DAO.getForValue(sql, fromUser, toUser);
    }

    /**
     * @param fromUser fromUser
     * @param toUser   toUser
     * @param content  内容
     * @param time     时间
     */
    public static void addHistoryMessages(String fromUser, String toUser, String content, String time)
    {
        String sql = "INSERT INTO historymessage(fromUser, toUser, content, time) VALUES" +
                " (?,?,?,?)";
        DAO.update(sql, fromUser, toUser, content, time);
    }
}
