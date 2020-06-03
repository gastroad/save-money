package com.savemoney.web.controller;

import com.savemoney.core.service.CoreService;
import com.savemoney.steam.service.SteamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebViewController {

    @Autowired
    private CoreService coreService;

    @Autowired
    private SteamService steamService;

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "Hello Save Money!";
    }

    @ResponseBody
    @RequestMapping(value = "/core", method = RequestMethod.GET)
    public String core() {
        return coreService.load();
    }

    @ResponseBody
    @RequestMapping(value = "/steam", method = RequestMethod.GET)
    public String steam() {
        return steamService.load();
    }

}
