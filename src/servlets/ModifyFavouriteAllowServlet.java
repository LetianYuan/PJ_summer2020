package servlets;

import databaseRelated.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ModifyFavouriteAllowServlet", urlPatterns = {"/ModifyFavouriteAllowServlet"})
public class ModifyFavouriteAllowServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        if(request.getParameter("allow") == null) return;
        if(request.getParameter("allow").equals("true"))
        {
            User.modifyFavouriteAllow((String)session.getAttribute("userName"), 1);
        }
        else
        {
            User.modifyFavouriteAllow((String)session.getAttribute("userName"), 0);
        }
    }
}
