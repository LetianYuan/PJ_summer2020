package servlets;

import databaseRelated.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CheckFriendsServlet", urlPatterns = {"/CheckFriendsServlet"})
public class CheckFriendsServlet extends HttpServlet
{

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        response.setContentType("text/html;charset=utf-8");
        String userName = request.getParameter("userName");
        if(userName == null) userName = "";
        PrintWriter out = response.getWriter();
        String me = (String) session.getAttribute("userName");
        if(User.getUid(userName) != null)
        {
            if(User.isFriends(userName, me))
            {
                out.print("TA已经是我的好友了！");
            }
            else if(userName.equals(me))
            {
                out.print("你不能添加自己为好友！");
            }
            else
            {
                out.print("找到该用户！");
            }
        }
        else
        {
            out.print("查无此人！");
        }
    }
}
