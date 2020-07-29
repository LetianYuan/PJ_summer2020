package servlets;

import databaseRelated.Message;
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

@WebServlet(name = "FriendsServlet", urlPatterns = {"/friends"})
public class FriendsServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");
        List<User> users = User.getAllFriends(User.getUid(userName));
        for(User e : users)
        {
            e.setTmp(Message.getHistoryMessageCount(e.getUserName(), userName));
        }
        String pageQuery = request.getParameter("page");
        int presentPage = 1;
        int per = 5;//每页5个
        int totalPage = (users.size() % per == 0) ? (users.size() / per) : (users.size() / per + 1);
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
        request.setAttribute("users", users);
        request.setAttribute("presentPage", presentPage);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("per", per);
        request.getRequestDispatcher("friends.jsp").forward(request, response);
    }
}
