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

@WebServlet(name = "pictureDetails", urlPatterns = {"/pictureDetails"})
public class PictureDetailsServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        String userName = null;
        int uid = -1;
        Picture pic = null;
        if(session.getAttribute("userName") != null)
            userName = (String) session.getAttribute("userName");
        if(session.getAttribute("UID") != null)
            uid = (int) session.getAttribute("UID");
        if(request.getParameter("imageID") != null)
        {
            int imageID;
            try
            {
                imageID = Integer.parseInt(request.getParameter("imageID"));
            }
            catch(Exception e)
            {
                imageID = -1;
            }
            if(uid != -1)
            {
                User me = User.getUser(uid);
                me.addFootprint(imageID);
            }
            pic = Picture.getPicture(imageID);
        }
        request.setAttribute("pic", pic);
        request.setAttribute("userName", userName);
        request.getRequestDispatcher("pictureDetails.jsp").forward(request, response);
    }
}
