package filters;

import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "ChatFilter", urlPatterns = {"/chat"}, dispatcherTypes = DispatcherType.REQUEST)
public class ChatFilter extends HttpFilter
{
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        if(session.getAttribute("chatTo") == null)//如果为null，那么久自己给自己发消息
        {
            session.setAttribute("chatTo", session.getAttribute("userName"));
        }
        chain.doFilter(request, response);
    }
}
