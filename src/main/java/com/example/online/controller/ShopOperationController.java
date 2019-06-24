package com.example.online.controller;

import com.example.online.dto.ShopExecution;
import com.example.online.entity.Shop;
import com.example.online.service.ShopService;
import com.example.online.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/shop")
public class ShopOperationController {
    @Autowired
    private ShopService shopService;

    /**
     * 随机从数据库中抽取若干条数据用于首页展示
     * @return
     */
    @RequestMapping(value = "/listrandomshops",method = RequestMethod.GET)
    @ResponseBody //该注解将对象转换成json数据，传至前台
    private Map<String,Object>listRandomShops(){
        Map<String,Object>modelMap=new HashMap<String, Object>();

        ShopExecution shopExecution=shopService.randomShopList();
        if(shopExecution.getStateInfo()=="商品不存在")
        {
            modelMap.put("success",false);
            modelMap.put("errMsg","目前没有商品信息");
        }
        else{
            modelMap.put("success",true);
            modelMap.put("shopList",shopExecution.getShopList());
        }

        return modelMap;
    }
    /**
     * 列出用户所卖出的所有商品
     * @param request
     * @return
     */
    @RequestMapping(value = "/listsaledshopspageinfo",method = RequestMethod.GET)
    @ResponseBody //该注解将对象转换成json数据，传至前台
    private Map<String,Object>listSaledShopsPageInfo(HttpServletRequest request){
        Map<String,Object>modelMap=new HashMap<String, Object>();
        //获取从前台传过来的sellerId
        long sellerId=HttpServletRequestUtil.getLong(request,"userId");
        ShopExecution shopExecution=shopService.querySaledShopList(sellerId);
        if(shopExecution.getState()!=1){
            modelMap.put("success",false);
            modelMap.put("errMsg","暂时还没有卖出的商品");
        }
        else {
            modelMap.put("success",true);
            modelMap.put("shopList",shopExecution.getShopList());
        }
        return modelMap;
    }
    /**
     * 列出用户正在卖的所有商品
     * @param request
     * @return
     */
    @GetMapping(value = "/listsellingshopspageinfo")
    @ResponseBody
    private Map<String,Object> listSellingShopsPageInfo(HttpServletRequest request){
        Map<String,Object>modelMap=new HashMap<String, Object>();
        //获取从前台传过来的sellerId
        long sellerId=HttpServletRequestUtil.getLong(request,"userId");
        ShopExecution shopExecution=shopService.querySellingShopList(sellerId);
        if(shopExecution.getState()!=1){
            modelMap.put("success",false);
            modelMap.put("errMsg","暂时还没有正在出售的商品");
        }
        else {
            modelMap.put("success",true);
            modelMap.put("shopList",shopExecution.getShopList());
        }
        return modelMap;
    }
    /**
     * 列出用户已买的所有商品
     * @param request
     * @return
     */
    @GetMapping(value = "/listboughtshopspageinfo")
    @ResponseBody
    private Map<String,Object> listBoughtShopsPageInfo(HttpServletRequest request){
        Map<String,Object>modelMap=new HashMap<String, Object>();
        //获取从前台传过来的buyerId
        long buyerId=HttpServletRequestUtil.getLong(request,"userId");
        ShopExecution shopExecution=shopService.queryBoughtShopList(buyerId);
        if(shopExecution.getState()!=1){
            modelMap.put("success",false);
            modelMap.put("errMsg","暂时还没有正在出售的商品");
        }
        else {
            modelMap.put("success",true);
            modelMap.put("shopList",shopExecution.getShopList());
        }
        return modelMap;
    }

    /**
     * 查询商品时
     * @param request
     * @return
     */
    @RequestMapping(value = "/listshopspageinfo",method = RequestMethod.GET)
    @ResponseBody //该注解将对象转换成json数据，传至前台
    private Map<String,Object>listShopsPageInfo(HttpServletRequest request){
        Map<String,Object>modelMap=new HashMap<String, Object>();
        //获取从前台传过来的shopName
        String shopName=HttpServletRequestUtil.getString(request,"shopName");
        if(shopName==null)
        {
            modelMap.put("success",false);
            modelMap.put("errMsg","商品名不能为空！");
        }
        else{
            ShopExecution shopExecution=shopService.getShopList(shopName);
            if(shopExecution.getCount()==0)
            {
                modelMap.put("success",false);
                modelMap.put("errMsg","未查找到此类商品");
            }
            else{
                modelMap.put("success",true);
                modelMap.put("shopList",shopExecution.getShopList());
                modelMap.put("shopCount",shopExecution.getCount());
            }
        }
        return modelMap;
    }

    /**
     * 查看单个商品详情信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/listshopdetailpageinfo",method = RequestMethod.GET)
    @ResponseBody //该注解将对象转换成json数据，传至前台
    private Map<String,Object>listShopDetailPageInfo(HttpServletRequest request){
        Map<String,Object> modelMap=new HashMap<String,Object>();
        //获取从前台传过来的shopId
        long shopId= HttpServletRequestUtil.getLong(request,"shopId");
        if(shopId!=-1){
            ShopExecution shopExecution=shopService.getShopById(shopId);
            modelMap.put("shop",shopExecution.getShop());
            modelMap.put("success",true);
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","商品不存在");
        }
        return modelMap;
    }
    /**
     * 新增商品
     */
    @PostMapping(value = "/addshoppageinfo")
    @ResponseBody
    private Map<String,Object> addShopPageInfo(@RequestParam("file") MultipartFile multipartFile,
                                               @RequestParam("shopName") String shopName,
                                               @RequestParam("shopInfo") String shopInfo,
                                               @RequestParam("sellerId") long sellerId){
        Map<String,Object> modelMap=new HashMap<String,Object>();
        try{
            if(shopName!=null&&shopInfo!=null)
            {
                Shop shop=new Shop();
                shop.setShopName(shopName);
                shop.setShopInfo(shopInfo);
                shop.setSellerId(sellerId);
                shop.setIfSolved(0);

                ShopExecution shopExecution=shopService.addShop(shop);
                long shopId=shopExecution.getShop().getShopId();
                if(upload(multipartFile,shopId)) {
                    modelMap.put("success", true);
                    modelMap.put("state", "商品新增成功");
                }
                else {
                    modelMap.put("success",false);
                    modelMap.put("errMsg","图片保存错误");
                }
            }
            else {
                modelMap.put("success",false);
                modelMap.put("errMsg","商品信息不完整");
            }
        }catch (Exception e ){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
        }
        return modelMap;
    }

    /**
     * 上传商品
     * @param userImgFile
     * @return
     */
    @Value("${web.upload.path}")
    private String uploadPath;
    public boolean upload(MultipartFile multipartFile,long shopId) {
        //获取文件名
        String fileName = multipartFile.getOriginalFilename();
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //重新生成文件名
        fileName = shopId + suffixName;
        String newPath=uploadPath+"goods/" + fileName;
        //指定本地文件夹存储图片
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            //将图片保存到static文件夹里
            multipartFile.transferTo(new File(newPath));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 用户点击确认交易
     * @param request
     * @return
     */
    @RequestMapping(value = "/successdeal")
    @ResponseBody
    private Map<String,Object>successDeal(HttpServletRequest request)
    {
        Map<String,Object> modelMap=new HashMap<String,Object>();
        long shopId=HttpServletRequestUtil.getLong(request,"shopId");
        long buyerId=HttpServletRequestUtil.getLong(request,"buyerId");
        ShopExecution shopExecution=shopService.successSaleShop(shopId,buyerId);
        if(shopExecution.getState()==1){
            modelMap.put("success",true);
        }
        else {
            modelMap.put("success",false);
        }
        return modelMap;
    }
    /**
     * 撤销商品
     */
    @RequestMapping(value = "/delshop",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> delShop(HttpServletRequest request){
        Map<String,Object>modelMap=new HashMap<String, Object>();
        long shopId=HttpServletRequestUtil.getLong(request,"shopId");
        long sellerId=HttpServletRequestUtil.getLong(request,"sellerId");
        ShopExecution shopExecution=shopService.deleteShop(shopId,sellerId);
        if(shopExecution.getState()==1)
        {
            modelMap.put("success",true);
            modelMap.put("state","删除成功");
        }
        else{
            modelMap.put("success",false);
            modelMap.put("errMsg","未能成功撤销商品");
        }
        return modelMap;
    }
    /**
     * 重新编辑商品信息
     */
    @RequestMapping(value = "updateshoppageinfo" ,method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object>updateShopPageInfo(HttpServletRequest request){
        Map<String,Object> modelMap=new HashMap<String,Object>();
        try{
            String shopName=HttpServletRequestUtil.getString(request,"shopName");
            String shopInfo=HttpServletRequestUtil.getString(request,"shopInfo");
            int ifSolved=HttpServletRequestUtil.getInt(request,"ifSolved");
            long sellerId=(long)request.getSession().getAttribute("sellerId");
            if(shopName!=null&&shopInfo!=null)
            {
                Shop shop=new Shop();
                shop.setShopName(shopName);
                shop.setShopInfo(shopInfo);
                shop.setIfSolved(ifSolved);
                shop.setSellerId(sellerId);
                ShopExecution shopExecution=shopService.updateShop(shop);
                modelMap.put("success",true);
                modelMap.put("state",shopExecution.getState());
            }
            else {
                modelMap.put("success",false);
                modelMap.put("errMsg","商品信息不完整");
            }
        }catch (Exception e ){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
        }
        return modelMap;
    }
}
