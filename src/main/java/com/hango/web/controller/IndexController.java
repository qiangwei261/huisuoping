package com.hango.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hango.web.service.IndexService;


/**
 * 处理首页请求
 */
@Controller
public class IndexController {
    
    @Autowired
    private IndexService indexService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("index");
        //请求后台系统中的接口获取数据,大广告位
        mv.addObject("bigAd", this.indexService.getIndexBigAd());
        //封装楼数据
        mv.addObject("indexData", this.indexService.getIndexData());
        return mv;
    }

}
