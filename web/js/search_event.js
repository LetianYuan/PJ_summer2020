let page;
let title = document.getElementById("title");
let content = document.getElementById("content");
let hot = document.getElementById("hot");
let time = document.getElementById("time");
let wd = document.getElementById("search_filter");
let searchResult = document.getElementById("searchResult");
let form = document.getElementById("searchForm");

function btn_click()
{
    let para1;
    let para2;
    if(title.checked)
        para1 = "title";
    else
        para1 = "content";
    if(hot.checked)
        para2 = "hot";
    else
        para2 = "time";
    let xhr = createXHR();
    xhr.onreadystatechange = function()
    {
        if(xhr.readyState === 4)
        {
            if((xhr.status >= 200 && xhr.status < 300) || xhr.status === 304)
            {
                searchResult.innerHTML = xhr.responseText;
            }
            else
            {
                alert("Request was unsuccessful: " + xhr.status);
            }
        }
    };
    let url = "SearchResult";
    url = addURLParam(url, "para1", para1);
    url = addURLParam(url, "para2", para2);
    url = addURLParam(url, "wd", wd.value);
    url = addURLParam(url, "page", page);
    xhr.open("get", url, true);
    xhr.send(null);
}

(function()
{
    window.onload = function()
    {
        let para1;
        let para2;
        if(title.checked)
            para1 = "title";
        else
            para1 = "content";
        if(hot.checked)
            para2 = "hot";
        else
            para2 = "time";
        let xhr = createXHR();
        xhr.onreadystatechange = function()
        {
            if(xhr.readyState === 4)
            {
                if((xhr.status >= 200 && xhr.status < 300) || xhr.status === 304)
                {
                    searchResult.innerHTML = xhr.responseText;
                }
                else
                {
                    alert("Request was unsuccessful: " + xhr.status);
                }
            }
        };
        let url = "SearchResult";
        url = addURLParam(url, "para1", para1);
        url = addURLParam(url, "para2", para2);
        url = addURLParam(url, "wd", wd.value);
        xhr.open("get", url, true);
        xhr.send(null);
    };

    form.onsubmit = function(e)
    {
        let para1;
        let para2;
        if(title.checked)
            para1 = "title";
        else
            para1 = "content";
        if(hot.checked)
            para2 = "hot";
        else
            para2 = "time";
        let xhr = createXHR();
        xhr.onreadystatechange = function()
        {
            if(xhr.readyState === 4)
            {
                if((xhr.status >= 200 && xhr.status < 300) || xhr.status === 304)
                {
                    searchResult.innerHTML = xhr.responseText;
                }
                else
                {
                    alert("Request was unsuccessful: " + xhr.status);
                }
            }
        };
        let url = "SearchResult";
        url = addURLParam(url, "para1", para1);
        url = addURLParam(url, "para2", para2);
        url = addURLParam(url, "wd", wd.value);
        xhr.open("get", url, true);
        xhr.send(null);
        e.preventDefault();
    };
})();
