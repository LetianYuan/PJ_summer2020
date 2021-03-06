package servlets;

import databaseRelated.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "SendFriendRequestServlet", urlPatterns = {"/sendFriendRequest"})
public class SendFriendRequestServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        String userName1 = (String) session.getAttribute("userName");
        String userName2 = request.getParameter("userName");
        User.sendFriendRequest(userName1, userName2);
    }
}
