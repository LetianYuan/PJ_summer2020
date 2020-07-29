(function()
{
    function initRibbons()
    {
        new Ribbons({
            colorSaturation: "60%",
            colorBrightness: "50%",
            colorAlpha: 0.5,
            colorCycleSpeed: 5,
            verticalPosition: "random",
            horizontalSpeed: 200,
            ribbonCount: 3,
            strokeSize: 0,
            parallaxAmount: -0.2,
            animateSections: true,
        });
    }
    initRibbons();
})();

