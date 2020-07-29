package servlets;

import databaseRelated.Picture;
import databaseRelated.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SeekFavouriteServlet", urlPatterns = {"/seekFavourite"})
public class SeekFavouriteServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        String me = (String) session.getAttribute("userName");
        int uid = 1;
        try
        {
            uid = Integer.parseInt(request.getParameter("uid"));
        }
        catch(Exception ignored)
        {
        }
        User he = User.getUser(uid);
        if(he == null) he = User.getUser(1);
        if(User.isFriends(me, he.getUserName()))//如果我和他是好友
        {
            if(he.getFavouriteOpen() == 1)//如果允许被查看
            {
                List<Picture> pictures = Picture.getAllFavoredPictureID(he.getUserName());
                String pageQuery = request.getParameter("page");
                int presentPage = 1;
                int per = 5;//每页5张图片
                int totalPage = Math.min((pictures.size() % per == 0) ? (pictures.size() / per) : (pictures.size() / per + 1), 10);
                if(pageQuery != null)
                {
                    try
                    {
                        presentPage = Integer.parseInt(pageQuery);
                    }
                    catch(Exception e)
                    {
                        presentPage = 1;
                    }
                }
                if(presentPage > totalPage)
                {
                    presentPage = totalPage;
                }
                request.setAttribute("pictures", pictures);
                request.setAttribute("presentPage", presentPage);
                request.setAttribute("totalPage", totalPage);
                request.setAttribute("per", per);
            }
            else
            {
                request.setAttribute("notAllowed", true);
            }
        }
        else
        {
            request.setAttribute("notFriend", true);
        }
        request.setAttribute("he", he);
        request.getRequestDispatcher("seekFavourite.jsp").forward(request, response);
    }
}
