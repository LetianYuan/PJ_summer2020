package filters;

import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "ChatJSPFilter", urlPatterns = {"/chat.jsp"}, dispatcherTypes = DispatcherType.REQUEST)
public class ChatJSPFilter extends HttpFilter
{
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException
    {
        String queryString = request.getQueryString();
        if(queryString != null)
            queryString = "?" + queryString;
        else
            queryString = "";
        response.sendRedirect("chat" + queryString);
    }
}
