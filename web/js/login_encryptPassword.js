(function()
{
    let loginForm = document.getElementById('loginForm');
    let passwordInput = document.getElementById('plain_password');
    let passwordOutput = document.getElementById('password');
    let userNameInput = document.getElementById('user_name');
    loginForm.addEventListener('submit', function()
    {
        passwordOutput.value = sha256_digest(passwordInput.value + userNameInput.value);
        passwordInput.value = '';//阻止明文传输
    });
})();


