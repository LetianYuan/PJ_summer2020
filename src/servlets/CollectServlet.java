package servlets;

import databaseRelated.DAO;
import databaseRelated.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "CollectServlet", urlPatterns = {"/CollectServlet"})
public class CollectServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        HttpSession session = request.getSession();
        int imageID;
        try
        {
            imageID = Integer.parseInt(request.getParameter("imageID"));//防注入
        }
        catch(Exception e)
        {
            imageID = 0;
        }
        int uid = (int) session.getAttribute("UID");//filter过滤过了，所以可以保证用户一定是登陆状态
        String sql = "SELECT favorID FROM travelimagefavor WHERE uid=? AND imageID=?";
        if(DAO.getForValue(sql, uid, imageID) == null)
        {
            //如果没有收藏
            session.setAttribute("collectStatus", "收藏成功");
            sql = "INSERT INTO travelimagefavor(uid,imageID) VALUES (?,?)";
        }
        else
        {
            //如果用户收藏了
            session.setAttribute("collectStatus", "取消收藏成功");
            sql = "DELETE FROM travelimagefavor WHERE uid=? AND imageID=?";
        }
        DAO.update(sql, uid, imageID);
        if(request.getParameter("fromFavorite") == null)
            response.sendRedirect("pictureDetails?imageID=" + imageID);
        else
        {
            session.removeAttribute("collectStatus");
            response.sendRedirect("favourite?cancel=success");
        }
    }
}
