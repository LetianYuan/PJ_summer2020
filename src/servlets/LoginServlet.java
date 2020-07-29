package servlets;

import databaseRelated.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "login", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        String previousURL = "index";
        Cookie[] cookies = request.getCookies();
        Cookie previousURLCookie = null;
        //获取传输过来的账号密码
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String confirmCode = request.getParameter("confirmCode");
        if(userName == null)//防止用户删除了html代码然后提交了没有username的表单
            userName = "";
        if(password == null)
            password = "";
        if(confirmCode == null)
            confirmCode = "";
        Cookie userNameCookie = new Cookie("userName", userName);
        userNameCookie.setMaxAge(-1);
        userNameCookie.setPath("/");
        for(Cookie e : cookies)
        {
            if("previousURL".equals(e.getName()))
            {
                previousURL = e.getValue();//如果用户是访问了别的页面跳转过来的
                previousURLCookie = e;
                break;
            }
        }
        if(session.getAttribute("loginError") == null)//如果用户不需要输入验证码
        {

            //链接数据库并比较
            User user = User.tryLogin(userName, password);
            if(user == null)//如果用户登录失败
            {
                session.setAttribute("loginError", true);
                request.setAttribute("userName", userName);
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
            else//登陆成功
            {
                session.setAttribute("loginSuccess", true);
                session.setAttribute("userName", user.getUserName());
                session.setAttribute("UID", user.getUID());
                if(previousURLCookie != null)
                {
                    previousURLCookie.setMaxAge(0);
                    previousURLCookie.setPath("/");
                    response.addCookie(previousURLCookie);//删除previousURL
                }
                response.addCookie(userNameCookie);
                response.sendRedirect(request.getContextPath() + "/" + previousURL);
            }
        }
        else //如果用户需要输入验证码
        {
            if(!confirmCode.toLowerCase().equals(session.getAttribute("confirmCode")))//如果验证码错误
            {
                request.setAttribute("userName", userName);
                request.setAttribute("confirmCodeError", true);
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
            else //验证码正确
            {
                //链接数据库并比较
                User user = User.tryLogin(userName, password);
                if(user == null)//如果用户登录失败
                {
                    session.setAttribute("loginError", true);
                    request.setAttribute("userName", userName);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
                else//登陆成功
                {
                    session.setAttribute("loginSuccess", true);
                    session.setAttribute("userName", user.getUserName());
                    session.setAttribute("UID", user.getUID());
                    session.removeAttribute("loginError");
                    if(previousURLCookie != null)
                    {
                        previousURLCookie.setMaxAge(0);
                        previousURLCookie.setPath("/");
                        response.addCookie(previousURLCookie);//删除previousURL
                    }
                    response.addCookie(userNameCookie);
                    response.sendRedirect(request.getContextPath() + "/" + previousURL);
                }
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException//如果用户第一次访问登录页面
    {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
