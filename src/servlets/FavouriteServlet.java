package servlets;

import databaseRelated.Picture;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "favourite", urlPatterns = {"/favourite"})
public class FavouriteServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");
        List<Picture> pictures = Picture.getAllFavoredPictureID(userName);
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
        request.getRequestDispatcher("favourite.jsp").forward(request, response);
    }
}
