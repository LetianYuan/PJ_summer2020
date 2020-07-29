package databaseRelated;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Picture
{
    private int imageID;
    private String title;
    private String description;
    private double latitude;
    private double longitude;
    private int cityCode;
    private String countryCodeISO;
    private int uid;
    private String path;
    private String content;
    private Timestamp updateTime;

    public Picture()
    {
    }

    @Override
    public String toString()
    {
        return "Image{" +
                "imageID=" + imageID +
                ", title='" + title + '\'' +
                ", descirption='" + description + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", cityCode=" + cityCode +
                ", countryCodeISO='" + countryCodeISO + '\'' +
                ", UID=" + uid +
                ", PATH='" + path + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public Timestamp getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime)
    {
        this.updateTime = updateTime;
    }

    public String getUpdateTimeString()
    {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date(updateTime.getTime()));
    }

    public int getImageID()
    {
        return imageID;
    }

    public void setImageID(int imageID)
    {
        this.imageID = imageID;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        if(description != null)
            return description;
        else
            return "这个作者很懒，什么都没写。";
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public int getCityCode()
    {
        return cityCode;
    }

    public void setCityCode(int cityCode)
    {
        this.cityCode = cityCode;
    }

    public String getCountryCodeISO()
    {
        return countryCodeISO;
    }

    public void setCountryCodeISO(String countryCodeISO)
    {
        this.countryCodeISO = countryCodeISO;
    }

    public int getUid()
    {
        return uid;
    }

    public void setUid(int uid)
    {
        this.uid = uid;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    /**
     * 根据指定的imageID返回一张图片的所有信息
     *
     * @param imageID 图片ID
     * @return 如果找不到图片，返回null
     */
    public static Picture getPicture(int imageID)
    {
        String sql = "SELECT imageID, title, description, latitude, longitude, cityCode, countryCodeISO, uid, path, content, updateTime" +
                " FROM travelimage" +
                " WHERE imageID = ?";
        return DAO.get(Picture.class, sql, imageID);
    }

    /**
     * 根据UID找到Author的username
     *
     * @return 作者名字
     */
    public String getAuthor()
    {
        String sql = "SELECT userName FROM traveluser WHERE UID=?";
        return DAO.getForValue(sql, uid);
    }

    /**
     * 根据CountryCodeISO找到国家名字
     *
     * @return 国家名字
     */
    public String getCountry()
    {
        if(countryCodeISO != null)
        {
            String sql = "SELECT countryName FROM geocountries WHERE ISO=?";
            return DAO.getForValue(sql, countryCodeISO);
        }
        else return "未知";
    }

    /**
     * 根据CityCode找到城市名字
     *
     * @return 城市名字
     */
    public String getCity()
    {
        if(cityCode != 0)
        {
            String sql = "SELECT AsciiName FROM geocities WHERE GeoNameID=?";
            return DAO.getForValue(sql, cityCode);
        }
        else return "未知";
    }

    /**
     * 根据imageID获取点赞数
     *
     * @return 点赞数
     */
    public Number getFavors()
    {
        String sql = "SELECT COUNT(travelimagefavor.ImageID) AS Favors FROM travelimagefavor WHERE" +
                " ImageID=?";
        return DAO.getForValue(sql, imageID);
    }

    /**
     * 返回此图片是否被该用户收藏
     *
     * @param userName 用户名
     * @return 如果被收藏，返回true
     */
    public boolean isFavored(String userName)
    {
        int uid = (int) User.getUid(userName);
        String sql = "SELECT favorID FROM travelimagefavor WHERE uid=? AND imageID=?";
        return DAO.getForValue(sql, uid, imageID) != null;
    }

    /**
     * 返回用户收藏的所有图片
     *
     * @param userName 用户名
     * @return 图片列表
     */
    public static List<Picture> getAllFavoredPictureID(String userName)
    {
        int uid = (int) User.getUid(userName);
        String sql = "SELECT travelimage.ImageID AS imageID, title, description, latitude, longitude, cityCode, countryCodeISO, travelimage.UID AS uid, path, content, updateTime" +
                " FROM travelimagefavor" +
                " LEFT JOIN travelimage ON travelimage.ImageID=travelimagefavor.ImageID" +
                " WHERE travelimagefavor.UID=?";
        return DAO.getForList(Picture.class, sql, uid);
    }

    /**
     * 取得收藏数最多的图片
     *
     * @param count 图片数量
     * @return 图片列表
     */
    public static List<Picture> getHottestPicture(int count)
    {
        String sql = "SELECT travelimage.ImageID AS imageID, title, description, latitude, longitude, cityCode, countryCodeISO, travelimage.UID AS uid, path, content, updateTime FROM travelimagefavor" +
                " LEFT JOIN travelimage ON travelimage.ImageID=travelimagefavor.ImageID" +
                " GROUP BY ImageID" +
                " ORDER BY count(travelimage.ImageID) DESC" +
                " LIMIT ?";
        return DAO.getForList(Picture.class, sql, count);
    }

    /**
     * 取得最新上传的图片
     *
     * @param count 图片数量
     * @return 图片列表
     */
    public static List<Picture> getLatestPicture(int count)
    {
        String sql = "SELECT travelimage.ImageID AS imageID, title, description, latitude, longitude, cityCode, countryCodeISO, travelimage.UID AS uid, path, content, updateTime FROM travelimage" +
                " ORDER BY updatetime DESC" +
                " LIMIT ?";
        return DAO.getForList(Picture.class, sql, count);
    }

    /**
     * 获得该用户上传的所有图片
     *
     * @param userName 用户名
     * @return 图片列表
     */
    public static List<Picture> getAllPicturesOfWhom(String userName)
    {
        String sql = "SELECT travelimage.ImageID AS imageID, title, description, latitude, longitude, cityCode, countryCodeISO, travelimage.UID AS uid, path, content, updateTime FROM travelimage" +
                " WHERE uid=?";
        Number uid = User.getUid(userName);
        return DAO.getForList(Picture.class, sql, uid);
    }
}
