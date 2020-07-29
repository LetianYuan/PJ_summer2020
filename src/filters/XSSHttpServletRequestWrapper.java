package filters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XSSHttpServletRequestWrapper extends HttpServletRequestWrapper
{
    public XSSHttpServletRequestWrapper(HttpServletRequest request)
    {
        super(request);
    }

    @Override
    public String getParameter(String name)
    {
        String value = super.getParameter(xssEncode(name));
        if(value != null)
        {
            value = xssEncode(value);
        }
        return value;
    }

    @Override
    public String getHeader(String name)
    {

        String value = super.getHeader(xssEncode(name));
        if(value != null)
        {
            value = xssEncode(value);
        }
        return value;
    }

    /**
     * 将容易引起xss漏洞的半角字符直接替换成全角字符
     *
     * @param s 未处理之前的字符
     * @return 防XSS处理之后的字符
     */
    private static String xssEncode(String s)
    {
        if(s == null || s.isEmpty())
        {
            return s;
        }
        return util.HTMLFilter.filter(s);
//        StringBuilder sb = new StringBuilder(s.length() + 16);
//        for(int i = 0; i < s.length(); i++)
//        {
//            char c = s.charAt(i);
//            switch(c)
//            {
//                case '>':
//                    sb.append('＞');//全角大于号
//                    break;
//                case '<':
//                    sb.append('＜');//全角小于号
//                    break;
//                case '\'':
//                    sb.append('‘');//全角单引号
//                    break;
//                case '\"':
//                    sb.append('“');//全角双引号
//                    break;
//                case '&':
//                    sb.append('＆');//全角
//                    break;
//                case '\\':
//                    sb.append('＼');//全角斜线
//                    break;
//                case '#':
//                    sb.append('＃');//全角井号
//                    break;
//                default:
//                    sb.append(c);
//                    break;
//            }
//        }
//        return sb.toString();
    }
}
