package servlets;

import databaseRelated.DAO;
import databaseRelated.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        HttpSession session = request.getSession();
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("passwordConfirm");
        String confirmCodeRequest = request.getParameter("confirmCode");
        if(isValid(userName, email, password, passwordConfirm, confirmCodeRequest))
        {
            String confirmCodeSession = (String) session.getAttribute("confirmCode");
            if(confirmCodeRequest.toLowerCase().equals(confirmCodeSession))
            {
                User.addUser(userName, password, email);
                Cookie userNameCookie = new Cookie("userName", userName);
                userNameCookie.setMaxAge(-1);
                userNameCookie.setPath("/");
                session.setAttribute("loginSuccess", true);
                session.setAttribute("userName", userName);
                session.setAttribute("UID", User.getUid(userName));
                response.addCookie(userNameCookie);
                response.sendRedirect("index");
            }
            else
            {
                request.setAttribute("registerError", true);
                request.setAttribute("userName", userName);
                request.setAttribute("email", email);
                request.setAttribute("confirmCodeError", true);
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
            return;
        }
        request.setAttribute("registerError", true);
        request.setAttribute("userName", userName);
        request.setAttribute("email", email);
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    private boolean isValid(String userName, String email, String password, String passwordConfirm, String confirmCode)
    {
        if(userName == null || "".equals(userName.trim()) ||
                email == null || "".equals(email.trim()) ||
                password == null || "".equals(password.trim()) ||
                passwordConfirm == null || "".equals(passwordConfirm.trim()) ||
                confirmCode == null || "".equals(confirmCode.trim()))
            return false;//如果发生以上情况，说明用户试图攻击
        else
        {
            if(userName.length() < 4 || userName.length() > 15)
                return false;
            else if(userName.contains("<") ||//主要目的是防止XSS
                    userName.contains("＃") ||
                    userName.contains(">") ||
                    userName.contains("/") ||
                    userName.contains("\\") ||
                    userName.contains(" ") ||
                    userName.contains("%") ||
                    userName.contains("@") ||
                    userName.contains("*") ||
                    userName.contains("&"))
                return false;
            else if(!password.equals(passwordConfirm))
                return false;
            else if(!email.matches("^([^\\x01-\\x20\\x22\\x28\\x29\\x2c\\x2e\\x3a-\\x3c\\x3e\\x40\\x5b-\\x5d\\x7f]+|)(\\x2e([^\\x01-\\x20\\x22\\x28\\x29\\x2c\\x2e\\x3a-\\x3c\\x3e\\x40\\x5b-\\x5d\\x7f]+|))*\\x40([^\\x01-\\x20\\x22\\x28\\x29\\x2c\\x2e\\x3a-\\x3c\\x3e\\x40\\x5b-\\x5d\\x7f]+|)(\\x2e([^\\x01-\\x20\\x22\\x28\\x29\\x2c\\x2e\\x3a-\\x3c\\x3e\\x40\\x5b-\\x5d\\x7f]+|))*(\\.\\w{2,})+$"))
                return false;
            else if(User.getUid(userName) != null)
                return false;
        }
        return true;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }
}
