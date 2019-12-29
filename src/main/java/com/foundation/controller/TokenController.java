package com.foundation.controller;

import com.foundation.verify.token.TokenImageCreator;
import com.foundation.verify.token.TokenManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description
 * @Author tsy20
 * @Date 2019/12/29
 */
@RestController
public class TokenController {

    private final Logger logger = LoggerFactory.getLogger(TokenController.class);

    private final TokenManager tokenManager;
    private final TokenImageCreator imageCreator;

    public TokenController(TokenManager tokenManager, TokenImageCreator imageCreator) {
        this.tokenManager = tokenManager;
        this.imageCreator = imageCreator;
    }


    @RequestMapping("/genImg.do")
    public void generateImg(HttpServletResponse response){

        String tokenStr = tokenManager.generateMultiSeq();
        byte[] source = imageCreator.generateImageSource(tokenStr);

        response.setContentType("images/*");
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(source);
            outputStream.flush();
            outputStream.close();
        }catch (IOException e){
            logger.error("generate image source failed", e);
        }


    }


    @RequestMapping("/genImage.do")
    public String generateImg(){

        String tokenStr = tokenManager.generateMultiSeq();
        return imageCreator.createBase64Image(tokenStr);
    }
}
