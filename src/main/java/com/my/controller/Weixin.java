package com.my.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.my.controller.base.BaseWebController;
import com.my.controller.base.User;

@Controller
public class Weixin extends BaseWebController {

    @RequestMapping(value = "/init.do")
    public ModelAndView init(HttpServletRequest request) {
        String url = getServerUrl(request, "baidu");
        ModelAndView view = new ModelAndView("success");
        User user = new User();
        user.setUserName("qihao");
        user.setPassword(url);
        view.addObject("user", user);
        return view;
    }

    @RequestMapping(value = "/a.do")
    public ModelAndView table(HttpServletRequest request) {
        String url = getServerUrl(request, "baidu");
        ModelAndView view = new ModelAndView("table");
        User user = new User();
        user.setUserName("qihao");
        user.setPassword(url);
        view.addObject("user", user);
        return view;
    }
}
