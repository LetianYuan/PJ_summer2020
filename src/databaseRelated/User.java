package databaseRelated;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class User
{
    private int UID;
    private String email;
    private String userName;
    private Timestamp dateJoined;
    private Timestamp dateLastModified;
    private Object tmp;//临时绑定一些数据之用
    private String footprint;
    private int favouriteOpen;

    public int getFavouriteOpen()
    {
        return favouriteOpen;
    }

    public void setFavouriteOpen(int favouriteOpen)
    {
        this.favouriteOpen = favouriteOpen;
    }

    public String getFootprint()
    {
        return footprint;
    }

    public void setFootprint(String footprint)
    {
        this.footprint = footprint;
    }

    public Object getTmp()
    {
        return tmp;
    }

    public void setTmp(Object tmp)
    {
        this.tmp = tmp;
    }

    public int getUID()
    {
        return UID;
    }

    public void setUID(int UID)
    {
        this.UID = UID;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public Timestamp getDateJoined()
    {
        return dateJoined;
    }

    public String getDateJoinedString()
    {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date(dateJoined.getTime()));
    }

    public void setDateJoined(Timestamp dateJoined)
    {
        this.dateJoined = dateJoined;
    }

    public Timestamp getDateLastModified()
    {
        return dateLastModified;
    }

    public String getDateLastModifiedString()
    {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date(dateLastModified.getTime()));
    }

    public void setDateLastModified(Timestamp dateLastModified)
    {
        this.dateLastModified = dateLastModified;
    }

    @Override
    public String toString()
    {
        return "User{" +
                "UID=" + UID +
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", dateJoined=" + getDateJoinedString() +
                ", dateLastModified=" + getDateLastModifiedString() +
                '}';
    }

    /**
     * 尝试登录
     *
     * @param userName 用户名
     * @param password 密码
     * @return 如果输入的信息正确，返回该用户所对应的User对象，否则返回null。
     */
    public static User tryLogin(String userName, String password)
    {
        String sql = "SELECT salt FROM traveluser WHERE userName=?";
        String salt = DAO.getForValue(sql, userName);
        password = SHA256.getSHA256(password + salt);//前端传输过来的password实际上是 SHA256.getSHA256(password + userName)
        sql = "SELECT UID, userName, email, dateJoined, dateLastModified, footprint, favouriteOpen From traveluser" +
                " WHERE userName=? AND password=?";
        return DAO.get(User.class, sql, userName, password);
    }

    /**
     * 根据userName获取uid
     *
     * @param userName 用户名
     * @return uid
     */
    public static Number getUid(String userName)
    {
        String sql = "SELECT uid FROM traveluser WHERE userName=?";
        return DAO.getForValue(sql, userName);
    }

    /**
     * 根据uid获取User
     *
     * @param uid uid
     * @return User
     */
    public static User getUser(Number uid)
    {
        String sql = "SELECT UID, userName, email, dateJoined, dateLastModified, footprint, favouriteOpen From traveluser" +
                " WHERE UID=?";
        return DAO.get(User.class, sql, uid);
    }

    /**
     * 根据uid获取他的所有朋友
     *
     * @param uid uid
     * @return 朋友列表
     */
    public static List<User> getAllFriends(Number uid)
    {
        String sql = "SELECT traveluser.UID AS UID, userName, email, dateJoined, dateLastModified, footprint, favouriteOpen From traveluser" +
                " RIGHT JOIN friend ON friend.friendUID = traveluser.UID" +
                " WHERE friend.UID=?";
        return DAO.getForList(User.class, sql, uid);
    }

    /**
     * 添加用户
     *
     * @param userName 用户名
     * @param password 加密后的密码
     * @param email    邮箱
     */
    public static void addUser(String userName, String password, String email)
    {
        String salt = SHA256.getSHA256("" + ((new Random()).nextInt(1000000)));
        password = SHA256.getSHA256(password + salt);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        String sql = "INSERT INTO traveluser (Email, UserName, Password, State, DateJoined, Salt, DateLastModified, favouriteOpen) VALUES" +
                " (?,?,?,1,?,?,?,1)";
        DAO.update(sql, email, userName, password, now, salt, now);
    }

    /**
     * 添加我的足迹，足迹格式类似于"1,2,3,4,5,"
     *
     * @param imageID 图片id
     */
    public void addFootprint(int imageID)
    {
        String footprintString = getFootprint();
        if(footprintString == null)
            footprintString = "";
        String[] footprintStrings = footprintString.split(",");
        if(footprintString.matches(".*((?<=(^)|(,))" + imageID + ",).*"))//如果足迹里已经有该图片了
        {
            footprintString = footprintString.replaceAll("(?<=(^)|(,))" + imageID + ",", "");
        }
        else
        {
            if(footprintStrings.length >= 10)//如果已经有10张图片了
            {
                footprintString = footprintString.substring(footprintString.indexOf(",") + 1);
            }
        }
        footprintString += imageID + ",";
        setFootprint(footprintString);
        DAO.update("UPDATE traveluser SET Footprint=? WHERE UID=?", footprintString, getUID());
    }

    /**
     * 判断两个人是否是好友
     *
     * @param userName1 用户1
     * @param userName2 用户2
     * @return 如果是好友返回true
     */
    public static boolean isFriends(String userName1, String userName2)
    {
        String sql = "SELECT friendID FROM friend WHERE UID=? AND friendUID=?";
        if(getUid(userName1).equals(getUid(userName2))) return false;
        return DAO.getForValue(sql, getUid(userName1), getUid(userName2)) != null;
    }

    /**
     * 将两个人添加为好友
     *
     * @param userName1 用户1
     * @param userName2 用户2
     */
    public static void addFriend(String userName1, String userName2)
    {
        Number uid1 = getUid(userName1);
        Number uid2 = getUid(userName2);
        if(uid1 == null || uid2 == null) return;
        if(isFriends(userName1, userName2)) return;
        String sql = "INSERT INTO friend (UID, friendUID) VALUES(?,?)";
        DAO.update(sql, uid1, uid2);
        DAO.update(sql, uid2, uid1);
    }

    /**
     * 修改用户观察的权限
     *
     * @param userName 用户名
     * @param status   用户状态
     */
    public static void modifyFavouriteAllow(String userName, int status)
    {
        String sql = "UPDATE traveluser SET FavouriteOpen=? WHERE userName=?";
        DAO.update(sql, status, userName);
    }

    /**
     * 删除好友
     *
     * @param userName1 用户1
     * @param userName2 用户2
     * @return 删除成功返回true
     */
    public static boolean removeFriend(String userName1, String userName2)
    {
        Number uid1 = getUid(userName1);
        Number uid2 = getUid(userName2);
        if(uid1 == null || uid2 == null) return false;
        if(!isFriends(userName1, userName2)) return false;
        String sql = "DELETE FROM friend  WHERE UID=? AND friendUID=?";
        DAO.update(sql, uid1, uid2);
        DAO.update(sql, uid2, uid1);
        return true;
    }

    /**
     * 得到该用户的所有好友申请
     *
     * @param userName 该用户的用户名
     * @return 向他发出好友申请的所有人
     */
    public static List<User> getAllFriendRequests(String userName)
    {
        String sql = "SELECT traveluser.UID AS UID, userName, email, dateJoined, dateLastModified, footprint, favouriteOpen From traveluser" +
                " RIGHT JOIN friendrequest ON fromUID = UID" +
                " WHERE toUID=?";
        return DAO.getForList(User.class, sql, getUid(userName));
    }


    /**
     * 前者向后者发送好友申请
     *
     * @param userName1 用户1
     * @param userName2 用户2
     */
    public static void sendFriendRequest(String userName1, String userName2)
    {
        Number uid1 = getUid(userName1);
        Number uid2 = getUid(userName2);
        if(uid1 == null || uid2 == null) return;
        if(isFriends(userName1, userName2)) return;
        if(uid1.equals(uid2)) return;
        //过滤掉已经发送过的请求
        Number tmp1 = DAO.getForValue("SELECT friendRequestID FROM friendrequest WHERE fromUID=? AND toUID=?", uid1, uid2);
        Number tmp2 = DAO.getForValue("SELECT friendRequestID FROM friendrequest WHERE fromUID=? AND toUID=?", uid2, uid1);
        if(tmp1 == null && tmp2 == null)
        {
            String sql = "INSERT INTO friendrequest (fromUID, toUID) VALUES (?,?)";
            DAO.update(sql, uid1, uid2);
        }
    }

    /**
     * 删除前者对后者的好友申请
     *
     * @param userName1 用户1
     * @param userName2 用户2
     */
    public static void deleteFriendRequest(String userName1, String userName2)
    {
        Number uid1 = getUid(userName1);
        Number uid2 = getUid(userName2);
        if(uid1 == null || uid2 == null) return;
        String sql = "DELETE FROM friendRequest WHERE fromUID=? AND toUID=?";
        DAO.update(sql, uid1, uid2);
    }
}
