document.getElementById("changeConfirmCode").onclick = function()
{
    let xhr = createXHR();
    xhr.onreadystatechange = function()
    {
        if(xhr.readyState === 4)
        {
            if((xhr.status >= 200 && xhr.status < 300) || xhr.status === 304)
            {
                document.getElementById("confirmCodeImg").src = "ConfirmCode?time=" + new Date().getTime();
            } else
            {
                alert("Request was unsuccessful: " + xhr.status);
            }
        }
    };
    let url = "ConfirmCode";
    xhr.open("get", url, true);
    xhr.send(null);
};