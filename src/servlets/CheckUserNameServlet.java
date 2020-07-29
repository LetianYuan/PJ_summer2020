package servlets;

import databaseRelated.User;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CheckUserNameServlet", urlPatterns = {"/CheckUserNameServlet"})
public class CheckUserNameServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        response.setContentType("text/html;charset=utf-8");
        String userName = request.getParameter("userName");
        if(userName == null) userName = "";
        PrintWriter out = response.getWriter();
        if(User.getUid(userName) != null)
        {
            out.print("用户名已存在");
        }
        else
        {
            out.print("");
        }
    }
}
