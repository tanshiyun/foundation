package com.foundation.verify.token;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * @ClassName TokenImageCreator
 * @Description 图形验证码生成类
 * @Author tsy20
 * @Date 2019/12/28
 **/
public class TokenImageCreator {

    private int width; //图片宽度
    private int height; //图片高度
    private int fontSize; //验证码字体大小
    private String backgroundColor; //图片背景色

    private int red;
    private int green;
    private int blue;

    private Random random = new Random();

    //字体数组
    private String[] fontFamily = {"Lucida Calligraphy", "Consolas", "Bell MT", "Ink Free", "Segoe Print"};
    //字体样式数组
    private int[] fontStyle = {Font.ITALIC+Font.BOLD, Font.PLAIN, Font.ITALIC, Font.BOLD};


    public TokenImageCreator() {
        setWidth(160);
        setHeight(70);
        setFontSize(this.height/2);
        setBackgroundColor("230,230,250");
    }


    /**
     * 绘制验证码图片
     * @param content 图片内容
     * @return 返回图片字节数组
     */
    public byte[] createImage(String content) {
        BufferedImage image = new BufferedImage(this.width, this.height, 1);

        Graphics2D graphics = image.createGraphics();

        //绘制背景
        graphics.setColor(new Color(this.red, this.green, this.blue));
        graphics.fillRect(0, 0, this.width, this.height);

        //计算字符绘制坐标
        int baseX = (this.width % content.length())/2;
        int baseY = (this.height - this.fontSize)/2 + this.fontSize;

        int step = this.width / content.length();

        //绘制验证码字符
        for(int i=0; i<content.length(); i++){
            //设置随机颜色和字体
            graphics.setColor(getRandomColor());
            graphics.setFont(getRandomFont());

            graphics.drawString(String.valueOf(content.charAt(i)), baseX+i*step, baseY);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, "png", out);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return out.toByteArray();


    }



    /**
     * 返回随机颜色
     * @return color
     */
    public Color getRandomColor(){
        int red = (100-this.red)+random.nextInt(155);
        int green = (100-this.green)+random.nextInt(155);
        int blue = (100-this.blue)+random.nextInt(155);
        return new Color(red,green,blue);
    }



    /**
     * 返回随机字体
     * @return font
     */
    public Font getRandomFont(){
        return new Font(fontFamily[random.nextInt(fontFamily.length)], fontStyle[random.nextInt(fontStyle.length)], fontSize);
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

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
        String[] colorParam = backgroundColor.split(",");

        this.red = Integer.parseInt(colorParam[0]);
        this.green = Integer.parseInt(colorParam[1]);
        this.blue = Integer.parseInt(colorParam[2]);
    }
}
