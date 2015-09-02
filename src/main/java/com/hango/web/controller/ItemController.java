package com.hango.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hango.manage.pojo.Item;
import com.hango.manage.pojo.ItemDesc;
import com.hango.web.service.ItemService;


@RequestMapping("item")
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 商品详情页
     * 
     * @param itemId
     * @return
     */
    @RequestMapping(value = "{itemId}", method = RequestMethod.GET)
    public ModelAndView showItem(@PathVariable("itemId") Long itemId) {
        ModelAndView mv = new ModelAndView("item");
        Item item = this.itemService.queryItemById(itemId);
        mv.addObject("item", item);
        //查询商品描述信息
        ItemDesc itemDesc = this.itemService.queryItemDescByItemId(itemId);
        mv.addObject("itemDesc", itemDesc);
        return mv;
    }

}
