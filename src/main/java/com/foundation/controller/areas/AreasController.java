package com.foundation.controller.areas;

import com.foundation.dao.mapper.AreasMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName AreasController
 * @Description TODO
 * @Author tsy20
 * @Date 2020/1/19
 **/

@RestController
public class AreasController {

    @Autowired
    private AreasMapper areasMapper;

    @GetMapping("/topAreas")
    public List<Map<String,String>> getTopAreas(){
        return this.areasMapper.getTopAreas();
    }
}
