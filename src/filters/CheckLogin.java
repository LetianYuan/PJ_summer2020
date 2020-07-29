package filters;

import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

@WebFilter(filterName = "###CheckLoginFilter",//取名为3个井号开头以保证这个filter永远是最先过滤的
        urlPatterns = {"/CollectServlet", "/favourite", "/chat", "/friends", "/upload", "/myPhoto", "/sendFriendRequest",
                "/personalCenter", "/addFriend", "/ModifyFavouriteAllowServlet", "/DeleteFriendServlet", "/seekFavourite",
                "/refuseFriendRequest"},
        dispatcherTypes = DispatcherType.REQUEST)
public class CheckLogin extends HttpFilter
{
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        String previousURL = request.getServletPath();
        if(request.getQueryString() != null)
        {
            previousURL += "?" + request.getQueryString();
        }
        previousURL = previousURL.substring(1);//去掉第一个"/"
        Object userName = session.getAttribute("userName");
        if(userName == null)//检查用户是否登陆
        {
            Cookie cookie = new Cookie("previousURL", previousURL);
            cookie.setMaxAge(-1);
            cookie.setPath("/");
            response.addCookie(cookie);
            response.sendRedirect("login");
        }
        else
            chain.doFilter(request, response);
    }
}
