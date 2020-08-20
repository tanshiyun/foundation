package com.foundation.controller.areas;

import com.foundation.component.BaseController;
import com.foundation.core.entity.User;
import com.foundation.dao.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName AreasController
 * @Description TODO
 * @Author tsy20
 * @Date 2020/1/19
 **/
@Controller
public class AreasController extends BaseController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/index.do")
    public String index(){
        return "index";
    }


    @PostMapping("/userAdd.do")
    @ResponseBody
    public int test(User user){
        super.beginTransaction();
        int i = userMapper.addUser(user);
        super.commit();
        return i;
    }
}
