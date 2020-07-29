package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "LoginJSPFilter", urlPatterns = {"/login.jsp"}, dispatcherTypes = DispatcherType.REQUEST)
public class LoginJSPFilter extends HttpFilter
{
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException
    {
        String queryString = request.getQueryString();
        if(queryString != null)
            queryString = "?" + queryString;
        else
            queryString = "";
        response.sendRedirect("login" + queryString);
    }
}
