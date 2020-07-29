package filters;

import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "UploadJSPFilter", urlPatterns = {"/upload.jsp"}, dispatcherTypes = DispatcherType.REQUEST)
public class UploadJSPFilter extends HttpFilter
{
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException
    {
        response.sendRedirect("upload");
    }
}
