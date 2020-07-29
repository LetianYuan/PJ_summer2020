package UI;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class Nav extends SimpleTagSupport
{
    public final static String HOME = "HOME";
    public final static String SEARCH = "SEARCH";
    public final static String NONE = "NONE";
    private PageContext pageContext;
    private String para;

    public String getPara()
    {
        return para;
    }

    public void setPara(String para)
    {
        this.para = para;
    }

    private String getNav(String para, HttpSession session)
    {
        return "<nav><ul class=\"toolbar\"><li><img src='img/icon.png'></img></li>" +
                "<li class=\"home\"><a href=" +
                (para.equals(HOME) ? "\"javascript:\"" : "\"index\"") +
                ">首页</a></li><li class=\"search\"><a href=" +
                (para.equals(SEARCH) ? "\"javascript:\"" : "\"search\"") +
                ">搜索</a></li><li class=\"user\">\n" +
                (session.getAttribute("userName") != null ?
                        ("<a href=\"javascript:\">" + session.getAttribute("userName") + "<span class=\"dropdown_caret\"></span></a><ul class=\"menu\"><li>" +
                                "<a href=\"upload\"><img src=\"img/upload.png\" alt=\"1.\">上传</a></li><li>" +
                                "<a href=\"myPhoto\"><img src=\"img/photo.png\" alt=\"2.\">我的照片</a></li><li>" +
                                "<a href=\"favourite\"><img src=\"img/favorite.png\" alt=\"3.\">我的收藏</a></li><li>" +
                                "<a href=\"friends\"><img src=\"img/friend.png\" alt=\"4.\">我的好友</a></li><li>"+
                                "<a href=\"personalCenter\"><img src=\"img/personalCenter.png\" alt=\"5.\">个人中心</a></li><li>" +
                                "<a href=\"logout\"><img src=\"img/exit.png\" alt=\"6.\">退出登录</a></li></ul>") :
                        ("<a href = 'login'>未登录</a>")) +
                "</li></ul></nav>";
    }

    @Override
    public void doTag() throws IOException
    {
        pageContext = (PageContext) getJspContext();
        JspWriter out = pageContext.getOut();
        HttpSession session = pageContext.getSession();
        out.print(getNav(para, session));
    }
}
