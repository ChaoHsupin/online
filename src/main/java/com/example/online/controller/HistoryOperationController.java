package com.example.online.controller;

import com.example.online.dto.ShopExecution;
import com.example.online.entity.History;
import com.example.online.entity.Shop;
import com.example.online.service.HistoryService;
import com.example.online.service.ShopService;
import com.example.online.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping(value = "/history")
public class HistoryOperationController {
    @Autowired
    HistoryService historyService;
    @Autowired
    ShopService shopService;
    /**
     * 将商品添加至历史记录
     */
    @RequestMapping(value = "/addshopinfotohistory", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> addShopInfoToHistory(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        long userId = HttpServletRequestUtil.getLong(request, "userId");
        History history = new History();
        history.setShopId(shopId);
        history.setUserId(userId);
        int effectedNum = historyService.insertToCollectedList(history);
        if (effectedNum == 1) {
            modelMap.put("success", true);
            modelMap.put("state", "成功加入历史记录");
        } else {
            modelMap.put("success", false);
            modelMap.put("state", "历史记录中已存在该条记录");
        }
        return modelMap;
    }

    /**
     * 从历史记录中删除某件商品
     */
    @RequestMapping(value = "/deleteshopinfofromhistory", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> deleteShopInfoFromHistory(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long userId = HttpServletRequestUtil.getLong(request, "userId");
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        int effectedNum = historyService.deleteFromHistory(userId,shopId);
        if (effectedNum == 1) {
            modelMap.put("success", true);
            modelMap.put("state", "成功删除该条历史记录");
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "删除失败");
        }
        return modelMap;
    }

    /**
     * 删除所有历史记录
     */
    @RequestMapping(value = "/deleteallhistory", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> deleteAllHistory(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            long userId = HttpServletRequestUtil.getLong(request,"userId");
            int effectedNum = historyService.deleteAllHistory(userId);
            if (effectedNum <= 0) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "历史记录删除失败");
            } else {
                modelMap.put("success", true);
                modelMap.put("historyNumber", effectedNum);
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        return modelMap;
    }

    /**
     * 查看所有历史记录商品
     */
    @RequestMapping(value = "/historylistpageinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> historyListPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            long userId = HttpServletRequestUtil.getLong(request, "userId");
            List<History> historyList = historyService.listHistory(userId);
            if (historyList == null) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "历史记录列表为空");
            } else {
                List<Shop>shopList=new ArrayList<Shop>();
                for(int i=0;i<historyList.size();i++)
                {
                    Shop shop=shopService.getShopById(historyList.get(i).getShopId()).getShop();
                    shopList.add(shop);
                }
                modelMap.put("success", true);
                modelMap.put("shopList", shopList);
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        return modelMap;
    }

    /**
     * 添加至收藏列表
     */
    @RequestMapping(value = "/addtocollectedlist", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> addToCollectedList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long userId = HttpServletRequestUtil.getLong(request, "userId");
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        History history = new History();
        history.setShopId(shopId);
        history.setUserId(userId);
        int effectedNum = historyService.insertToCollectedList(history);
        modelMap.put("success", true);
        modelMap.put("state", "成功添加至收藏列表");
        return modelMap;
    }

    /**
     * 从收藏列表中删除某条记录
     */
    @RequestMapping(value = "/deletefromcollectedlist", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> deleteFromCollectedList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long userId = HttpServletRequestUtil.getLong(request, "userId");
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        int effectedNum = historyService.deleteFromCollectedList(userId,shopId);
        if (effectedNum == 1) {
            modelMap.put("success", true);
            modelMap.put("state", "成功移除该商品");
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "移除商品失败");
        }
        return modelMap;
    }

    /**
     * 移除收藏列表中所有商品
     */
    @RequestMapping(value = "/deletecollectedlistallshop", method = RequestMethod.GET)
    private Map<String, Object> deleteCollectedListAllShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            long userId = HttpServletRequestUtil.getLong(request, "userId");
            int effectedNum = historyService.deleteAllCollectedList(userId);
            if (effectedNum <= 0) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "收藏列表所有信息删除失败");
            } else {
                modelMap.put("success", true);
                modelMap.put("collectedNumber", effectedNum);
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        return modelMap;

    }

    /**
     * 查看所有收藏商品
     */
    @RequestMapping(value = "/collectedlistpageinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> collectedListPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            long userId = HttpServletRequestUtil.getLong(request, "userId");
            List<History> collectedList = historyService.listCollectedList(userId);
            if (collectedList == null) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "收藏列表为空");
            } else {
                List<Shop>shopList=new ArrayList<Shop>();
                for(int i=0;i<collectedList.size();i++)
                {
                    Shop shop=shopService.getShopById(collectedList.get(i).getShopId()).getShop();
                    shopList.add(shop);
                }
                modelMap.put("success", true);
                modelMap.put("shopList", shopList);
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        return modelMap;
    }
}
