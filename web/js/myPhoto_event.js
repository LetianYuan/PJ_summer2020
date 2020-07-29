$(function()
{
    if(getQueryVariable("upload") == "success")
        showTips('上传成功', TIP_SUCCESS);
    else if(getQueryVariable("delete") == "success")
        showTips("删除成功", TIP_SUCCESS);
});