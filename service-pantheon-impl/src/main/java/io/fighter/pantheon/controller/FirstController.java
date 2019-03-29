package io.fighter.pantheon.controller;

import io.fighter.galaxy.cache.RedisDao;
import io.fighter.pantheon.CmdDescription;
import io.fighter.pantheon.param.PostParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xiasx
 * @create 2019-03-12 14:22
 **/

@RestController
@RequestMapping("/firstcontroller")
public class FirstController {

    @Autowired
    private RedisDao redisDao;


    @CmdDescription(value = "Get响应", level = "base-cmd")
    @GetMapping(value = "/echo")
    public String getNacosEcho(@RequestParam String name) {
        redisDao.valueSet("test", "999");
        return "Hello Nacos Discovery " + name;
    }

    @CmdDescription(value = "Post响应")
    @PostMapping(value = "/echo2")
    public String getPostEcho(@RequestBody PostParam postParam) {
        return "Hello " + postParam.getName() + ", " + postParam.getValue();
    }
}
