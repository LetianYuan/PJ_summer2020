let chatBtns = document.getElementsByClassName("chat");
let iframeContainer = document.getElementById("iframe_container");
let iframe = document.getElementById("iframe");
let iframeTitle = document.getElementById("iframe_title");
let closeBtn = document.getElementById("iframe_close");
//let messageCount = document.getElementById("messageCount");
for(let e of chatBtns)
{
    e.addEventListener("click", function()
    {
        iframeContainer.hidden = false;
        iframe.src = e.value;
        iframeTitle.innerHTML = e.value.split('=')[1];
        //messageCount.innerHTML = '';
    });
}
closeBtn.addEventListener("click", function()
{
    iframeContainer.hidden = true;
    iframe.src = "";
});