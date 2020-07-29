$(function()
{
    if(getQueryVariable("size") === "false")
        showTips('上传失败，文件过大(<=20MB)', TIP_DANGER);
    else if(getQueryVariable("pic") === "false")
        showTips('上传失败，文件格式不正确', TIP_DANGER);
    else if(getQueryVariable("error") === "unknown")
        showTips('上传失败，未知错误', TIP_DANGER);
});

$(function()
{
    document.getElementById("upload").onsubmit = function(e)
    {
        let pic = document.getElementById("pic_fileBtn").files[0];
        if(pic == undefined && getQueryVariable("imageID") == false)
        {
            showTips('请选择图片', TIP_WARNING);
            e.preventDefault();
        }
        else if(pic != undefined && pic.size > 1024 * 1024 * 20)
        {
            showTips('上传失败，文件过大(<=20MB)', TIP_DANGER);
            e.preventDefault();
        }
        else if(!/^.*\.(gif|jpg|png|jpeg)$/.test(pic.name))
        {
            showTips('上传失败，文件格式不正确', TIP_DANGER);
            e.preventDefault();
        }
        else if(($("#country option:selected")).val() == "none")
        {
            showTips('请选择国家', TIP_WARNING);
            e.preventDefault();
        }
        else if(($("#city option:selected")).val() == "none")
        {
            showTips('请选择城市', TIP_WARNING);
            e.preventDefault();
        }
        else
        {
            if(!confirm("请确认信息无误"))
            {
                e.preventDefault();
            }
        }
    };
});