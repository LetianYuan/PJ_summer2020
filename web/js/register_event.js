(function()
{
    let userNameInput = document.getElementById("user_name");
    let emailInput = document.getElementById("email");
    let passwordInput = document.getElementById("plain_password");
    let passwordConfirmInput = document.getElementById("plain_password_confirm");
    let confirmCodeInput = document.getElementById("confirmCode");
    let passwordSafetyImg = document.getElementById("password_safety_img");
    let passwordSafetyWord = document.getElementById("password_safety_word");
    let errorLabel = document.getElementById("error_info");

    function showError()
    {
        let userName = userNameInput.value;
        let password = passwordInput.value;
        let passwordConfirm = passwordConfirmInput.value;
        // let confirmCode = confirmCodeInput.value;
        let email = emailInput.value;
        if(userName.length < 4 || userName.length > 15)
        {
            errorLabel.innerHTML = "用户名长度不正确(需<=15且>=4个字符)";
        }
        else if(userName.indexOf("<") !== -1 ||//主要目的是防止XSS
            userName.indexOf("＃") !== -1 ||
            userName.indexOf(">") !== -1 ||
            userName.indexOf("/") !== -1 ||
            userName.indexOf("\\") !== -1 ||
            userName.indexOf(" ") !== -1 ||
            userName.indexOf("%") !== -1 ||
            userName.indexOf("@") !== -1 ||
            userName.indexOf("*") !== -1 ||
            userName.indexOf("&") !== -1)
        {
            errorLabel.innerHTML = "用户名含有特殊字符(@,%,&,*,&lt;,&gt;等)";
        }
        else if(password.length < 6 || password.length > 12)
        {
            errorLabel.innerHTML = "密码长度不正确(需<=12且>=6个字符)";
        }
        else if(password != passwordConfirm)
        {
            errorLabel.innerHTML = "两次输入的密码不一致";
        }
        else if(!/^([^\x01-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f]+|)(\x2e([^\x01-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f]+|))*\x40([^\x01-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f]+|)(\x2e([^\x01-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f]+|))*(\.\w{2,})+$/.test(email))
        {
            errorLabel.innerHTML = "邮箱格式不正确";
        }
        else
        {
            let xhr = createXHR();
            xhr.onreadystatechange = function()
            {
                if(xhr.readyState === 4)
                {
                    if((xhr.status >= 200 && xhr.status < 300) || xhr.status === 304)
                    {
                        if(xhr.responseText.trim() !== '')
                        {
                            errorLabel.innerHTML = xhr.responseText;
                        }
                        else
                        {
                            errorLabel.innerHTML = "";
                        }
                    }
                    else
                    {
                        alert("Request was unsuccessful: " + xhr.status);
                    }
                }
            };
            let url = "CheckUserNameServlet";
            url = addURLParam(url, "userName", userNameInput.value);
            xhr.open("get", url, true);
            xhr.send(null);
        }
    }

    userNameInput.addEventListener("input", showError);
    emailInput.addEventListener("input", showError);
    passwordInput.addEventListener("input", showError);
    passwordConfirmInput.addEventListener("input", showError);
    confirmCodeInput.addEventListener("input", showError);

    function changeSafety()
    {
        let password = passwordInput.value;
        let level = 0;
        if(/[A-Z]/.test(password))
            level++;
        if(/[a-z]/.test(password))
            level++;
        if(/[0-9]/.test(password))
            level++;
        if(/[^0-9a-zA-Z]/.test(password))
            level++;
        if(level === 4)
        {
            passwordSafetyImg.src = "img/password_safe.png";
            passwordSafetyWord.innerHTML = "强";
            passwordSafetyWord.style.color = 'green';
        }
        else if(level === 3)
        {
            passwordSafetyImg.src = "img/password_risk.png";
            passwordSafetyWord.innerHTML = "中";
            passwordSafetyWord.style.color = 'orange';
        }
        else
        {
            passwordSafetyImg.src = "img/password_danger.png";
            passwordSafetyWord.innerHTML = "弱";
            passwordSafetyWord.style.color = 'red';
        }
    }

    document.onkeyup = changeSafety;
})();


(function()
{
    let registerForm = document.getElementById('registerForm');
    let passwordInput = document.getElementById('plain_password');
    let passwordOutput = document.getElementById('password');
    let passwordConfirmInput = document.getElementById('plain_password_confirm');
    let passwordConfirmOutput = document.getElementById('password_confirm');
    let userNameInput = document.getElementById('user_name');
    registerForm.addEventListener('submit', function(e)
    {
        if(document.getElementById("error_info").innerHTML.trim() !== '')
            e.preventDefault();
        //阻止明文传输
        passwordOutput.value = sha256_digest(passwordInput.value + userNameInput.value);
        passwordInput.value = '';
        passwordConfirmOutput.value = sha256_digest(passwordConfirmInput.value + userNameInput.value);
        passwordConfirmInput.value = '';
    });
})();

(function()
{
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
                }
                else
                {
                    alert("Request was unsuccessful: " + xhr.status);
                }
            }
        };
        let url = "ConfirmCode";
        xhr.open("get", url, true);
        xhr.send(null);
    };
})();
