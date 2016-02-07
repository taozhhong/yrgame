package ServletAction.LoginPackages;


import javax.imageio.ImageIO;
import javax.servlet.ServletException;
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
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2016-1-11
 * Time: 14:11:41
 * 生成图形验证码
 */
@WebServlet(name = "ServletCode",urlPatterns = {"/Action/GetCode.do"})
public class ServletCode extends HttpServlet
{

    private Font mFont = new Font("Arial Black", 0, 16);

    public void init() throws ServletException
    {
        super.init();
    }

    private Color getRandColor(int fc, int bc)
    {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    private String getRandomChar()
    {
        int rand = (int) Math.round(Math.random() * 2.0D);
        long itmp = 0L;
        char ctmp = '\000';
        switch (rand)
        {
            case 1:
                itmp = Math.round(Math.random() * 25.0D + 65.0D);
                ctmp = (char) (int) itmp;
                return String.valueOf(ctmp);
            case 2:
                itmp = Math.round(Math.random() * 25.0D + 97.0D);
                ctmp = (char) (int) itmp;
                return String.valueOf(ctmp);
        }
        itmp = Math.round(Math.random() * 9.0D);
        return String.valueOf(itmp);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        int width = 100;
        int height = 35;
        BufferedImage image = new BufferedImage(width, height, 1);

        Graphics g = image.getGraphics();
        Random random = new Random();
        g.setColor(getRandColor(200, 250));
        g.fillRect(1, 1, width - 1, height - 1);
        g.setColor(new Color(102, 102, 102));
        g.drawRect(0, 0, width - 1, height - 1);
        g.setFont(this.mFont);
        g.setColor(getRandColor(160, 200));

        for (int i = 0; i < 155; i++)
        {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int xl = random.nextInt(6) + 1;
            int yl = random.nextInt(12) + 1;
            g.drawLine(x, y, x + xl, y + yl);
        }
        for (int i = 0; i < 70; i++)
        {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int xl = random.nextInt(12) + 1;
            int yl = random.nextInt(6) + 1;
            g.drawLine(x, y, x - xl, y - yl);
        }
        String sRand = "";

        int LEN = 5;
        for (int i = 0; i < LEN; i++)
        {
            String tmp = getRandomChar();
            sRand = sRand + tmp;
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(tmp, 13 * i + 4, 20);
        }
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(300);
        session.setAttribute("randomImageStr", sRand.toLowerCase());
        g.dispose();
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }
}
