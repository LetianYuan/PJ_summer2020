package servlets;

import databaseRelated.DAO;
import databaseRelated.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "DeletePictureServlet", urlPatterns = {"/DeletePictureServlet"})
public class DeletePictureServlet extends HttpServlet
{

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        //首先验证这张图片是否是自己的以保证网络安全
        int uid = (int) session.getAttribute("UID");
        int imageID = 0;
        try
        {
            imageID = Integer.parseInt(request.getParameter("imageID"));
        }
        catch(Exception ignored)
        {

        }
        String sql = "SELECT imageID FROM travelimage WHERE uid=? AND imageID=?";
        if(DAO.getForValue(sql, uid, imageID) != null)
        {
            //如果这张图片是自己的，那么删除
            sql = "DELETE FROM travelimage WHERE travelimage.ImageID=?";
            DAO.update(sql, imageID);
            sql = "DELETE FROM travelimagefavor WHERE travelimagefavor.ImageID=?";
            DAO.update(sql, imageID);
            sql = "SELECT traveluser.UID AS UID, userName, email, dateJoined, dateLastModified, footprint, favouriteOpen From traveluser" +
                    " WHERE footprint LIKE concat('%' , ? , ',%')";
            List<User> users = DAO.getForList(User.class, sql, imageID);
            for(User e : users)
            {
                sql = "UPDATE traveluser SET footprint=? WHERE uid=?";
                DAO.update(sql, e.getFootprint().replaceAll("(?<=(^)|(,))" + imageID + ",", ""), e.getUID());
            }
            response.sendRedirect("myPhoto?delete=success");
            return;
        }
        response.sendRedirect("myPhoto");
    }
}