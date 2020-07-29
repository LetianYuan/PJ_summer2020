function decodeHTMLElement(str)
{
    while(str.indexOf("&lt;") >= 0)
    {
        str = str.replace("&lt;", "<");
    }
    while(str.indexOf("&gt;") >= 0)
    {
        str = str.replace("&gt;", ">");
    }
    while(str.indexOf("&amp;") >= 0)
    {
        str = str.replace("&amp;", "&");
    }
    while(str.indexOf("&quot;") >= 0)
    {
        str = str.replace("&quot;", "\"");
    }
    return str;
}