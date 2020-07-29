package servlets;

import databaseRelated.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "DeleteFriendServlet", urlPatterns = {"/DeleteFriendServlet"})
public class DeleteFriendServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        String userName1 = request.getParameter("userName");
        String userName2 = (String) session.getAttribute("userName");
        if(userName1 == null) return;
        if(User.removeFriend(userName1, userName2))
            response.sendRedirect("friends?remove=success");
        else
            response.sendRedirect("friends");
    }
}
