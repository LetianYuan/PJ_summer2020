package servlets;

import databaseRelated.DAO;
import databaseRelated.Picture;
import databaseRelated.User;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "UploadServlet", urlPatterns = {"/upload"})
public class UploadServlet extends HttpServlet
{
    static DiskFileItemFactory factory = new DiskFileItemFactory();
    static ServletFileUpload upload = new ServletFileUpload(factory);

    static
    {
        factory.setSizeThreshold(1024 * 1024);//如果文件超过1MB，直接写入临时文件夹中
        File file = new File("C:\\summer2020_pj_pic_tmp");
        factory.setRepository(file);
        upload.setSizeMax(1024 * 1024 * 20);//文件不允许超过20MB
        upload.setHeaderEncoding("UTF-8");
    }

    private boolean isValidContentType(String tp)
    {
        return "image/gif".equals(tp) || "image/jpeg".equals(tp) || "image/jpg".equals(tp) || "image/png".equals(tp);
    }

    private String getFileName(String contentType, String size, HttpSession session, Date now)
    {
        return getServletContext().getRealPath("/") + "/img/travel_images/" + size + "/" +
                session.getAttribute("userName") + "-" + new SimpleDateFormat("yyyyMMddHHmmss").format(now) +
                "." + contentType.split("/")[1];
    }

    private double getCompressedRate(long size)
    {
        if(size > 1024 * 1024 * 10)
            return 0.1;
        else if(size > 1024 * 1024 * 2)
            return 0.2;
        else if(size > 1024 * 200)
            return 0.4;
        else
            return 0.8;
    }

    protected synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        HttpSession session = request.getSession();
        Date now = new Date();
        try
        {
            List<FileItem> items = upload.parseRequest(request); //得到FileItem集合
            String fileName = "";
            HashMap<String, String> map = new HashMap<>();
            boolean flag = true;
            //遍历items
            for(FileItem e : items)
            {
                if(e.isFormField())//如果是表单域
                {
                    String name = e.getFieldName();
                    String value = util.HTMLFilter.filter(e.getString("UTF-8"));
                    map.put(name, value);
                }
                else if(e.getSize() > 0)//如果有文件
                {
                    flag = false;
                    if(e.getSize() >= 1024 * 1024 * 20)
                    {
                        response.sendRedirect("upload?size=false");//如果文件过大
                        return;
                    }
                    String contentType = e.getContentType();
                    if(!isValidContentType(contentType))
                    {
                        response.sendRedirect("upload?pic=false");
                        return;
                    }
                    fileName = getFileName(contentType, "large", request.getSession(), now);
                    if(!new File(fileName).exists())
                        new File(fileName.substring(0, fileName.lastIndexOf("/"))).mkdir();
                    e.write(new File(fileName));
                    String compressedFileName = getFileName(contentType, "small", request.getSession(), now);
                    Thumbnails.of(new File(fileName)).scale(getCompressedRate(e.getSize()))
                            .toFile(new File(compressedFileName));
                }
            }
            if(flag)
            {
                response.sendRedirect("upload?error=unknown");
                return;
            }
            String title = map.get("title");
            String country = map.get("country");
            String content = map.get("content");
            String city = map.get("city");
            String description = map.get("description");
            if(title == null) title = "未知";
            if(country == null) country = "Andorra";
            if(city == null) city = "`Ayn Halaqim";
            if(content == null) content = "scenery";
            if(description == null) description = "未知";
            String sql = "SELECT ISO From geocountries WHERE CountryName=?";
            String countryCodeISO = DAO.getForValue(sql, country);
            sql = "SELECT GeoNameID From geocities WHERE AsciiName=? AND CountryCodeISO=?";
            Number cityCode = DAO.getForValue(sql, city, countryCodeISO);
            sql = "SELECT Latitude From geocities WHERE AsciiName=? AND CountryCodeISO=?";
            Number latitude = DAO.getForValue(sql, city, countryCodeISO);
            sql = "SELECT Longitude From geocities WHERE AsciiName=? AND CountryCodeISO=?";
            Number longitude = DAO.getForValue(sql, city, countryCodeISO);
            int uid = (int) session.getAttribute("UID");

            boolean isModifying = (map.get("modifyFlag") != null);

            if(!isModifying)
            {
                sql = "INSERT INTO travelimage(Title, Description, Latitude, Longitude, CityCode, CountryCodeISO, UID, PATH, Content, UpdateTime)" +
                        "VALUES (?,?,?,?,?,?,?,?,?,?)";
                DAO.update(sql, title, description, latitude, longitude, cityCode, countryCodeISO, uid, fileName.substring(fileName.lastIndexOf("/") + 1), content, new Timestamp(now.getTime()));
            }
            else// if(isModifying)
            {
                int imageID = 0;
                try
                {
                    imageID = Integer.parseInt(map.get("modifyFlag"));
                }
                catch(Exception e)
                {
                    response.sendRedirect("upload?error=unknown");
                    e.printStackTrace();
                    return;
                }
                Picture pic = Picture.getPicture(imageID);
                if("".equals(fileName))//如果没有修改过图片
                {
                    if(pic != null && session.getAttribute("userName").equals(pic.getAuthor()))//验证图片是否是自己的
                    {
                        sql = "UPDATE travelimage SET Title=?, Description=?, Latitude=?, Longitude=?, CityCode=?, CountryCodeISO=?," +
                                " UID=?, Content=?, UpdateTime=?" +
                                " WHERE imageID=?";
                        DAO.update(sql, title, description, latitude, longitude, cityCode, countryCodeISO, uid,
                                content, new Timestamp(now.getTime()), imageID);
                    }
                }
                else//如果修改过图片
                {
                    if(pic != null && session.getAttribute("userName").equals(pic.getAuthor()))//验证图片是否是自己的
                    {
                        sql = "UPDATE travelimage SET Title=?, Description=?, Latitude=?, Longitude=?, CityCode=?, CountryCodeISO=?," +
                                " UID=?, PATH=?, Content=?, UpdateTime=?" +
                                " WHERE imageID=?";
                        DAO.update(sql, title, description, latitude, longitude, cityCode, countryCodeISO, uid,
                                fileName.substring(fileName.lastIndexOf("/") + 1), content, new Timestamp(now.getTime()), imageID);
                    }
                }
            }
        }
        catch(FileUploadBase.SizeLimitExceededException e)
        {
            response.sendRedirect("upload?size=false");//如果文件过大
            return;
        }
        catch(Exception e)
        {
            response.sendRedirect("upload?error=unknown");
            e.printStackTrace();
            return;
        }
        response.sendRedirect("myPhoto?upload=success");//上传成功
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException
    {
        HttpSession session = request.getSession();
        if(request.getParameter("imageID") != null)
        {
            int imageID = 0;
            try
            {
                imageID = Integer.parseInt(request.getParameter("imageID"));
            }
            catch(Exception ignored)
            {

            }
            Picture pic = Picture.getPicture(imageID);
            if(pic != null && pic.getAuthor().equals(session.getAttribute("userName")))
            {
                request.setAttribute("picture", pic);
            }
        }
        request.getRequestDispatcher("upload.jsp").forward(request, response);
    }
}
