package servlets;

import databaseRelated.DAO;
import databaseRelated.Picture;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SearchResult", urlPatterns = {"/SearchResult"})
public class SearchResult extends HttpServlet
{

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String para1 = request.getParameter("para1");
        String para2 = request.getParameter("para2");
        String wd = request.getParameter("wd");
        if(para1 == null) para1 = "title";
        if(para2 == null) para2 = "hot";
        if(wd == null) wd = "";
        wd = wd.replaceAll("'", "");
        String[] wds = wd.split(" ");
        String sql;
        if("hot".equals(para2))
        {
            sql = "SELECT travelimage.ImageID AS imageID, title, description, latitude, longitude, cityCode, countryCodeISO, travelimage.UID AS uid, path, content, updateTime FROM travelimagefavor" +
                    " LEFT JOIN travelimage ON travelimage.ImageID = travelimagefavor.ImageID";
        }
        else
        {
            sql = "SELECT travelimage.ImageID AS imageID, title, description, latitude, longitude, cityCode, countryCodeISO, travelimage.UID AS uid, path, content, updateTime FROM travelimage";
        }
        if("title".equals(para1))
        {
            for(int i = 0; i < wds.length; i++)
            {
                if(i == 0)
                    sql += " WHERE title LIKE concat(";
                else
                    sql += " AND title LIKE concat(";
                sql += "'%','" + wds[i] + "','%')";
            }
        }
        else
        {
            for(int i = 0; i < wds.length; i++)
            {
                if(i == 0)
                    sql += " WHERE content LIKE concat(";
                else
                    sql += " AND content LIKE concat(";
                sql += "'%','" + wds[i] + "','%')";
            }
        }
        if("hot".equals(para2))
        {
            sql += " GROUP BY ImageID" +
                    " ORDER BY count(travelimage.ImageID) DESC";
        }
        else
        {
            sql += " ORDER BY updatetime DESC";
        }
        //System.out.println(sql);
        List<Picture> pictures = DAO.getForList(Picture.class, sql);
        if("hot".equals(para2))
        {
            sql = "SELECT travelimage.ImageID AS imageID, title, description, latitude, longitude, cityCode, countryCodeISO, travelimage.UID AS uid, path, content, updateTime FROM travelimagefavor" +
                    " RIGHT JOIN travelimage ON travelimage.ImageID = travelimagefavor.ImageID WHERE travelimagefavor.ImageID IS NULL";
            if("title".equals(para1))
            {
                for(int i = 0; i < wds.length; i++)
                {
                    sql += " AND title LIKE concat(";
                    sql += "'%','" + wds[i] + "','%')";
                }
            }
            else
            {
                for(int i = 0; i < wds.length; i++)
                {
                    sql += " AND content LIKE concat(";
                    sql += "'%','" + wds[i] + "','%')";
                }
            }
            pictures.addAll(DAO.getForList(Picture.class, sql));
        }
        String pageQuery = request.getParameter("page");
        int presentPage = 1;
        int per = 5;//每页5张图片
        int totalPage = Math.min((pictures.size() % per == 0) ? (pictures.size() / per) : (pictures.size() / per + 1), 10);
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
        request.setAttribute("pictures", pictures);
        request.setAttribute("presentPage", presentPage);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("per", per);
        request.getRequestDispatcher("SearchResult.jsp").forward(request, response);
    }
}
