const TIP_SUCCESS = '#28a745';
const TIP_DANGER = '#dc3545';
const TIP_WARNING = '#d39e00';

function showTips(tip, status)
{
    let div = document.createElement('div');
    div.setAttribute('style', 'background-color:' + status + ';color:#ffffff;z-index:9999;position:fixed;padding:5px 10px 5px 10px;min-width:250px;text-align:center;border-radius:8px;right:30px;bottom:30px;font-size:17pt;box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.5);');
    div.setAttribute('class', 'f-up');
    div.textContent = tip;
    document.body.appendChild(div);
    let timeout = setTimeout(function()
    {
        document.body.removeChild(div);
    }, 5000);
}


