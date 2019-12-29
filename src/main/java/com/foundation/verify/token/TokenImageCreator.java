package com.foundation.verify.token;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

/**
 * @ClassName TokenImageCreator
 * @Description 图形验证码生成类
 * @Author tsy20
 * @Date 2019/12/28
 **/

@Component
public class TokenImageCreator {

    private final Logger logger = LoggerFactory.getLogger(TokenImageCreator.class);

    private int width; //图片宽度
    private int height; //图片高度
    private int fontSize; //验证码字体大小
    private int lineNum; //干扰线条数

    //背景色参数
    private int red;
    private int green;
    private int blue;

    private Random random = new Random();

    //字体数组
    private String[] fontFamily = {"Lucida Calligraphy", "Consolas", "Bell MT", "Ink Free", "Segoe Print"};
    //字体样式数组
    private int[] fontStyle = {Font.ITALIC+Font.BOLD, Font.PLAIN, Font.ITALIC, Font.BOLD};


    public TokenImageCreator() {
        this.width = 160;
        this.height = 70;
        this.fontSize = this.height>>1;
        this.lineNum = 3;
        this.red = 230;
        this.green = 230;
        this.blue = 250;
    }


    /**
     * 返回图片的base64编码
     * @return String
     */
    public String createBase64Image(String content) {
        StringBuilder source = new StringBuilder("data:image/jpeg;base64,");
        Base64.Encoder encoder = Base64.getEncoder();
        String base64 = encoder.encodeToString(generateImageSource(content));
        source.append(base64);
        return source.toString();
    }



    /**
     * 绘制验证码图片
     * @param content 图片内容
     * @return 返回图片字节数组
     */
    public byte[] generateImageSource(String content) {
        BufferedImage image = new BufferedImage(this.width, this.height, 1);

        Graphics2D graphics = image.createGraphics();

        //绘制背景
        graphics.setColor(new Color(this.red, this.green, this.blue));
        graphics.fillRect(0, 0, this.width, this.height);

        //计算字符绘制坐标
        int baseX = (this.width % content.length())>>1;

        int offsetY = (this.height - this.fontSize)>>2;
        int baseY = offsetY + this.fontSize;

        int step = this.width / content.length();

        //绘制背景干扰线
        drawInterferenceLine(graphics);

        //绘制验证码字符
        for(int i=0; i<content.length(); i++){
            //设置随机颜色和字体
            graphics.setColor(getRandomColor());
            graphics.setFont(getRandomFont());

            graphics.drawString(String.valueOf(content.charAt(i)), baseX+i*step, baseY);
        }

        //绘制前景干扰线
        drawInterferenceLine(graphics);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, "jpeg", out);
        } catch (IOException e) {
            logger.error("Create token image failed", e);
        }finally {
            try {
                out.close();
            } catch (IOException e) {
                logger.error("close output stream(ByteArrayOutputStream) failed", e);
            }
        }

        return out.toByteArray();


    }



    /**
     * 绘制干扰线
     * @param graphics
     */
    private void drawInterferenceLine(Graphics2D graphics) {

        //绘制30条随机干扰线
        for (int i=0; i<this.lineNum; i++){
            graphics.setColor(getRandomColor());
            graphics.setFont(getRandomFont());

            int offsetX = this.width>>1;
            graphics.drawLine(random.nextInt(offsetX), random.nextInt(this.height), random.nextInt(offsetX)+offsetX, random.nextInt(this.height));
        }

    }



    /**
     * 返回随机颜色
     * @return color
     */
    private Color getRandomColor(){
        int red = Math.abs( (100 - this.red) + random.nextInt(155) );
        int green = Math.abs( (100 - this.green) + random.nextInt(155) );
        int blue = Math.abs( (100 - this.blue) + random.nextInt(155) );
        return new Color(red,green,blue);
    }



    /**
     * 返回随机字体
     * @return font
     */
    private Font getRandomFont(){
        return new Font(fontFamily[random.nextInt(fontFamily.length)], fontStyle[random.nextInt(fontStyle.length)], this.fontSize);
    }



    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setLineNum(int lineNum) { this.lineNum = lineNum; }

    public void setBackgroundColor(String backgroundColor) {
        //图片背景色
        String[] colorParam = backgroundColor.split(",");

        this.red = Integer.parseInt(colorParam[0]);
        this.green = Integer.parseInt(colorParam[1]);
        this.blue = Integer.parseInt(colorParam[2]);
    }
}
