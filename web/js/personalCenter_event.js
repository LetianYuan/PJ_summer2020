let userNameOfTA = "";
$(function()
{
    let addUserBtn = document.getElementById("addUser");
    let userNameInput = document.getElementById("userNameInput");
    let result = document.getElementById("result");
    if(getQueryVariable('accept') === "success")
        showTips("添加成功！", TIP_SUCCESS);
    else if(getQueryVariable('refuse') === "success")
        showTips("拒绝成功！", TIP_SUCCESS);

    userNameInput.oninput = function()
    {
        setTimeout(function()
        {
            let xhr = createXHR();
            xhr.onreadystatechange = function()
            {
                if(xhr.readyState === 4)
                {
                    if((xhr.status >= 200 && xhr.status < 300) || xhr.status === 304)
                    {
                        result.innerHTML = xhr.responseText;
                        if(xhr.responseText.trim() === "找到该用户！")
                        {
                            userNameOfTA = userNameInput.value;
                            addUserBtn.disabled = false;
                            addUserBtn.removeAttribute("class");
                        }
                        else
                        {
                            addUserBtn.disabled = true;
                            addUserBtn.setAttribute("class", "disabled");
                        }
                    }
                    else
                    {
                        alert("Request was unsuccessful: " + xhr.status);
                    }
                }
            };
            let url = "CheckFriendsServlet";
            url = addURLParam(url, "userName", userNameInput.value);
            xhr.open("get", url, true);
            xhr.send(null);
        }, 100);
    };

    addUserBtn.onclick = function()
    {
        if(addUserBtn.disabled) return;

        let xhr = createXHR();
        xhr.onreadystatechange = function()
        {
            if(xhr.readyState === 4)
            {
                if((xhr.status >= 200 && xhr.status < 300) || xhr.status === 304)
                {
                    showTips("发送成功", TIP_SUCCESS);
                    userNameInput.value = "";
                    result.innerHTML = "";
                    addUserBtn.disabled = true;
                }
                else
                {
                    alert("Request was unsuccessful: " + xhr.status);
                }
            }
        };
        let url = "sendFriendRequest";
        url = addURLParam(url, "userName", userNameOfTA);
        xhr.open("get", url, true);
        xhr.send(null);
    };

    let favouriteAllowBox = document.getElementById("favouriteAllow");
    favouriteAllowBox.onclick = function()
    {
        let xhr = createXHR();
        xhr.onreadystatechange = function()
        {
            if(xhr.readyState === 4)
            {
                if((xhr.status >= 200 && xhr.status < 300) || xhr.status === 304)
                {
                    showTips("修改成功", TIP_SUCCESS);
                }
                else
                {
                    alert("Request was unsuccessful: " + xhr.status);
                }
            }
        };
        let url = "ModifyFavouriteAllowServlet";
        url = addURLParam(url, "allow", favouriteAllowBox.checked);
        xhr.open("get", url, true);
        xhr.send(null);
    };
});