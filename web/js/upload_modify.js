//此文件必须在filter_onchange之后调用
//用于回显图片信息
$(function()
{
    let img = document.getElementById("pic_show");
    let contentInfo = document.getElementById('content');
    let countryInfo = document.getElementById('country');
    let titleInfo = document.getElementById('pic_title');
    let descriptionInfo = document.getElementById('pic_description');
    let picStatus = document.getElementById("pic_status");
    let btn = document.getElementById("submit");
    let form = document.getElementById("upload");
    if(content)//如果成功传参，回显图片信息
    {
        let modifyFlag = document.createElement("input");
        modifyFlag.setAttribute("type", "text");
        modifyFlag.setAttribute("name", "modifyFlag");
        modifyFlag.setAttribute("value", getQueryVariable("imageID"));
        modifyFlag.setAttribute("hidden", "true");
        modifyFlag.setAttribute("style", "display:none");
        form.appendChild(modifyFlag);
        img.src = path;
        contentInfo.value = content;
        picStatus.innerHTML = "";
        for(let e of countryInfo.children)
        {
            if(e.getAttribute('value') === countryName)
            {
                e.selected = true;
                break;
            }
            else
            {
                e.selected = false;
            }
        }
        for(let e of city[countryName])
        {
            let option_city = document.createElement('option');
            option_city.innerHTML = e;
            option_city.setAttribute('value', e);
            if(cityName === e)
            {
                option_city.selected = true;
            }
            select_city.appendChild(option_city);
        }
        descriptionInfo.value = decodeHTMLElement(description);
        titleInfo.value = title;
        btn.value = "确认修改";
    }
});