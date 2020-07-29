package servlets;

import databaseRelated.Picture;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "index", urlPatterns = {"/index"})
public class IndexServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Picture> hottestPictures = Picture.getHottestPicture(5);
        request.setAttribute("hottestPictures", hottestPictures);
        List<Picture> latestPictures = Picture.getLatestPicture(8);
        request.setAttribute("latestPictures", latestPictures);

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
