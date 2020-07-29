(function()
{
    const RIGHT = true;
    const LEFT = false;
    let currentPicture = 0;//当前图片的下标，范围为0~4
    let imgs = document.getElementsByClassName("wrap")[0].children;
    let btns = document.getElementsByClassName("buttons")[0].children;

    function slide(from, to, direction)//从第from张图片切向第to张图片，范围为0~4
    {
        for(let i = 0; i < 5; i++)
        {
            imgs[i].removeAttribute("class");
            btns[i].removeAttribute("class");
        }
        if(direction)//向右切，即按了向左按钮
        {
            imgs[from].classList.add("active");
            imgs[to].classList.add("active");
            imgs[from].classList.add("from-right");//from-right动画声明于CSS文件中
            imgs[to].classList.add("to-left");
        }
        else
        {
            imgs[from].classList.add("active");
            imgs[to].classList.add("active");
            imgs[from].classList.add("from-left");
            imgs[to].classList.add("to-right");
        }
        currentPicture = to;
        btns[currentPicture].classList.add("on");
    }

    let rightArrow = document.getElementsByClassName("arrow_right")[0];
    let leftArrow = document.getElementsByClassName("arrow_left")[0];
    rightArrow.addEventListener("click", function()
    {
        if(currentPicture === 4)
        {
            slide(4, 0, LEFT);
        }
        else
        {
            slide(currentPicture, currentPicture + 1, LEFT);
        }
    });
    leftArrow.addEventListener("click", function()
    {
        if(currentPicture === 0)
        {
            slide(0, 4, RIGHT);
        }
        else
        {
            slide(currentPicture, currentPicture - 1, RIGHT);
        }
    });
    let container = document.getElementsByClassName("carousel_container")[0];

    function start()
    {
        return setInterval(function()
        {
            if(currentPicture === 4)
            {
                slide(4, 0, LEFT);
            }
            else
            {
                slide(currentPicture, currentPicture + 1, LEFT);
            }
        }, 5000);
    }

    let interval = start();
    document.addEventListener("load", function()
    {
        interval = start();
    });
    container.addEventListener("mouseover", function()
    {
        clearInterval(interval);
    });
    container.addEventListener("mouseout", function()
    {
        interval = start();
    });
    for(let i = 0; i < 5; i++)
    {
        btns[i].addEventListener("click", function()
        {
            if(i === currentPicture) return;
            slide(currentPicture, i, currentPicture < i ? LEFT : RIGHT);
        });
    }
})();