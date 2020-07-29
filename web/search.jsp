<%@ taglib prefix="nav" uri="navigator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>搜索</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/search.css">
</head>
<body>
<nav:nav para="SEARCH"/>
<main>
    <form action="javascript:" id="searchForm">
        <fieldset>
            <ul>
                <li class="title">
                    搜索
                </li>
                <li class="content">
                    <input id="search_btn" type="submit" value="搜索">
                    <div id="para1">
                        <label><input type="radio" name="para1" value="title" id="title" checked>按标题搜索</label>
                        <label><input type="radio" name="para1" value="content" id="content">按主题搜索</label>
                    </div>
                    <div id="para2">
                        <label><input type="radio" name="para2" value="hot" id="hot" checked>按热度排序</label>
                        <label><input type="radio" name="para2" value="time" id="time">按时间排序</label>
                    </div>
                    <br/>
                    <input id="search_filter" type="text" name="wd" placeholder="输入搜索关键词">
                </li>
            </ul>
        </fieldset>
    </form>
    <ul class="result">
        <li class="title">
            结果
        </li>
        <li class="content" id="searchResult">

        </li>
    </ul>
</main>

<footer class="copyright">
    <span class="copyright">Copyright &copy;2020 Letian Yuan. (旦)-学习性-2020-0001&nbsp;&nbsp;&nbsp;旦公网安备19302010019号&nbsp;&nbsp;&nbsp;旦ICP证000019号</span>
</footer>

<script src="js/XHR.js"></script>
<script src="js/search_event.js"></script>
</body>
</html>