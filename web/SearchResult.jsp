<%@ page import="databaseRelated.Picture" %>
<%@ page import="java.util.List" %><%--用户正常情况不应该访问此页面--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<li class="content">
    <ul class="pictures">
        <%
            List<Picture> pictures = (List<Picture>) request.getAttribute("pictures");
            int presentPage = (int) request.getAttribute("presentPage");
            int totalPage = (int) request.getAttribute("totalPage");
            int per = (int) request.getAttribute("per");

            if(pictures.size() == 0)
            {
        %>
        <li>
            <h1>很抱歉，没有查找到相关的图片。</h1>
        </li>
        <%
        }
        else
        {
            for(int i = (presentPage - 1) * per; i < presentPage * per && i < pictures.size(); i++)
            {
                Picture e = pictures.get(i);
        %>
        <li>
            <a href="pictureDetails?imageID=<%=e.getImageID()%>">
                <img src="img/travel_images/small/<%=e.getPath()%>"
                     title="<%=e.getTitle()%>"
                     alt="<%=e.getPath()%>">
            </a>
            <span class="image_description"><strong class="image_title"><%=e.getTitle()%></strong>
                <strong class="author"><%=e.getAuthor()%></strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=e.getUpdateTimeString()%>
                <br/><em
                        class="hot_index">热度:<%=e.getFavors()%></em>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=e.getContent()%></span>
        </li>
        <%
            }
        %>
        <div class="page_number">
            <a href="javascript:" onclick="page=<%=Math.max(1,presentPage-1)%>;btn_click();" title="向前一页"><<</a>
            <%
                for(int i = 1; i <= totalPage; i++)
                {
            %>
            <a href="javascript:" onclick="page=<%=i%>;btn_click();"
               title="<%=i%>" <%=(i == presentPage ? " class='active'" : "")%>><%=i%>
            </a>
            <%
                }
            %>
            <a href="javascript:" onclick="page=<%=Math.min(totalPage,presentPage+1)%>;btn_click();" title="向后一页">>></a>
        </div>
        <%
            }
        %>
    </ul>
</li>