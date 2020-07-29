package servlets;

import databaseRelated.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(name = "PersonalCenterServlet", urlPatterns = {"/personalCenter"})
public class PersonalCenterServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        String me = (String) session.getAttribute("userName");
        List<User> friendRequests = User.getAllFriendRequests(me);
        if(friendRequests != null && friendRequests.size() != 0)
            request.setAttribute("friendRequests", friendRequests);
        request.getRequestDispatcher("personalCenter.jsp").forward(request, response);
    }
}
