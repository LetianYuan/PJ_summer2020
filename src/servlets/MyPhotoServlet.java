package servlets;

import databaseRelated.Picture;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "MyPhotoServlet", urlPatterns = {"/myPhoto"})
public class MyPhotoServlet extends HttpServlet
{

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");
        List<Picture> pictures = Picture.getAllPicturesOfWhom(userName);
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
        request.getRequestDispatcher("myPhoto.jsp").forward(request, response);
    }
}
