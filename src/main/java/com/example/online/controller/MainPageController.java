package com.example.online.controller;

import com.example.online.dto.ShopExecution;
import com.example.online.entity.Shop;
import com.example.online.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/front")
public class MainPageController {
    @Autowired
    private ShopService shopService;

    /**
     * 初始化前端展示系统，首页随机展示20条以内的商品信息
     * @return
     */
    @RequestMapping(value = "/listmainpageinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object>listMainPageInfo(){
        Map<String,Object>modelMap=new HashMap<String, Object>();
        try {
            ShopExecution shopExecutiont=shopService.randomShopList();
            modelMap.put("随机抽取商品列表",shopExecutiont);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        modelMap.put("success",true);
        return modelMap;
    }
}
