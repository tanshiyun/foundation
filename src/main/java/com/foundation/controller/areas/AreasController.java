package com.foundation.controller.areas;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
