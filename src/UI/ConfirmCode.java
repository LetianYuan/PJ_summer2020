package UI;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * 摘自 https://www.cnblogs.com/lovetq520/p/11676333.html
 */
@WebServlet(name = "ConfirmCode", urlPatterns = {"/ConfirmCode"})
public class ConfirmCode extends HttpServlet
{
    private char[] codeSequence = {'A', '1', 'B', 'C', '2', 'D', '3', 'E', '4', 'F', '5', 'G', '6', 'H', '7', 'I', '8', 'J',
            'K', '9', 'L', '2', 'M', '3', 'N', 'P', '4', 'Q', '5', 'R', '6', 'S', '7', 'T', '8', 'U', '9', 'V', 'W', 'X', 'Y', 'Z'};

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        HttpSession session = request.getSession();
        int width = 100;
        int height = 35;
        Random random = new Random();
        //设置response头信息
        //禁止缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        //生成缓冲区image类
        BufferedImage image = new BufferedImage(width, height, 1);
        //产生image类的Graphics用于绘制操作
        Graphics g = image.getGraphics();
        //Graphics类的样式
        g.setColor(this.getColor(200, 250));
        g.setFont(new Font("Times New Roman", 0, 30));
        g.fillRect(0, 0, width, height);
        //绘制干扰线
        for(int i = 0; i < 40; i++)
        {
            g.setColor(this.getColor(130, 200));
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(12);
            int y1 = random.nextInt(12);
            g.drawLine(x, y, x + x1, y + y1);
        }
        //绘制字符
        String strCode = "";
        for(int i = 0; i < 4; i++)
        {
            String rand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
            strCode = strCode + rand;
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 10 + (width - 20) / 4 * i + random.nextInt(15), height / 4 * 3 + random.nextInt(10));
        }
        //将字符保存到session中用于前端的验证
        session.setAttribute("confirmCode", strCode.toLowerCase());
        g.dispose();
        ImageIO.write(image, "JPEG", response.getOutputStream());
        response.getOutputStream().flush();
    }

    public Color getColor(int fc, int bc)
    {
        Random random = new Random();
        if(fc > 255)
            fc = 255;
        if(bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
