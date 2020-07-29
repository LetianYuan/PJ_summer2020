document.getElementById("collect").addEventListener("click", function()
{
    let xhr = createXHR();
    xhr.onreadystatechange = function()
    {
        if(xhr.readyState === 4)
        {
            if((xhr.status >= 200 && xhr.status < 300) || xhr.status === 304)
            {
                document.getElementById("collect_info").innerHTML = xhr.responseText;
            }
            else
            {
                alert("Request was unsuccessful: " + xhr.status);
            }
        }
    };
    let url = "CollectServlet";
    url = addURLParam(url, "imageID", getQueryVariable("imageID"));
    xhr.open("get", url, true);
    xhr.send(null);
});