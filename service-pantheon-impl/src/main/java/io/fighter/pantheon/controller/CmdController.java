package io.fighter.pantheon.controller;

import io.fighter.pantheon.core.service.CmdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiasx
 * @create 2019-03-20 17:28
 **/

@RestController
@RequestMapping("/cmdcontroller")
public class CmdController {

    @Autowired
    private CmdService cmdService;

    @GetMapping(value = "/cmd")
    public void getAllMappingValue() {
        cmdService.initCmd();
    }
}
