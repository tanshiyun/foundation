package com.foundation.controller.areas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName AreasController
 * @Description TODO
 * @Author tsy20
 * @Date 2020/1/19
 **/

@Controller
public class AreasController {


    @GetMapping("/index.do")
    public String index(){
        return "index";
    }

}
