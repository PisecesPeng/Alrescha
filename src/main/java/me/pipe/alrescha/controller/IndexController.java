package me.pipe.alrescha.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
@Api(value="index-controller", description="this is index controller")
public class IndexController {

    @GetMapping("/getInfo")
    @ApiOperation("返回获得的str信息")
    public String index(@RequestParam(value="str1", required=true) @ApiParam("用户输入的str1(必填)") String str1, @RequestParam(value="str2", required=false) @ApiParam("用户输入的str2") String str2) {
        return "index get str1 : " + str1 + ";\nindex get str2 : " + str2 + ";";
    }

}
