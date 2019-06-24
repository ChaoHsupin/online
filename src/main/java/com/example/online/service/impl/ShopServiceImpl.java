package com.example.online.service.impl;

import com.example.online.dao.ShopDao;
import com.example.online.dto.ImageHolder;
import com.example.online.dto.ShopExecution;
import com.example.online.entity.Shop;
import com.example.online.enums.ShopStateEnum;
import com.example.online.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopDao shopDao;

    @Override
    public ShopExecution getShopList(String shopName) {
        List<Shop> shopList = shopDao.queryShopList(shopName);
        // 基于同样的查询条件返回该查询条件下的商品总数
        int count = shopDao.queryShopCount(shopName);
        ShopExecution shopExecution;
        if(shopList!=null)
        {
            shopExecution=new ShopExecution(ShopStateEnum.SUCCESS,shopList);
            shopExecution.setCount(count);
        }
        else{
            shopExecution=new ShopExecution(ShopStateEnum.EMPTY);
        }
        return shopExecution;
    }

    @Override
    public ShopExecution querySaledShopList(long sellerId) {
        List<Shop> shopList = shopDao.querySaledShopList(sellerId);
        ShopExecution shopExecution;
        if(shopList!=null)
        {
            shopExecution=new ShopExecution(ShopStateEnum.SUCCESS,shopList);
        }
        else{
            shopExecution=new ShopExecution(ShopStateEnum.EMPTY);
        }
        return shopExecution;
    }

    @Override
    public ShopExecution querySellingShopList(long sellerId) {
        List<Shop> shopList = shopDao.querySellingShopList(sellerId);
        ShopExecution shopExecution;
        if(shopList!=null)
        {
            shopExecution=new ShopExecution(ShopStateEnum.SUCCESS,shopList);
        }
        else{
            shopExecution=new ShopExecution(ShopStateEnum.EMPTY);
        }
        return shopExecution;
    }

    @Override
    public ShopExecution queryBoughtShopList(long buyerId) {
        List<Shop> shopList = shopDao.queryBoughtShopList(buyerId);
        ShopExecution shopExecution;
        if(shopList!=null)
        {
            shopExecution=new ShopExecution(ShopStateEnum.SUCCESS,shopList);
        }
        else{
            shopExecution=new ShopExecution(ShopStateEnum.EMPTY);
        }
        return shopExecution;
    }

    @Override
    public ShopExecution getShopById(long shopId) {
        Shop shop= shopDao.queryShopById(shopId);
        ShopExecution shopExecution;
        if(shop!=null){
            shopExecution=new ShopExecution(ShopStateEnum.SUCCESS,shop);
        }
        else{
            shopExecution=new ShopExecution(ShopStateEnum.EMPTY);
        }
        return shopExecution;
    }

    @Override
    public ShopExecution randomShopList() {
        List<Shop>shopList= shopDao.randomShopList();
        ShopExecution shopExecution;
        if(shopList==null)
            shopExecution=new ShopExecution(ShopStateEnum.EMPTY);
        else
            shopExecution=new ShopExecution(ShopStateEnum.SUCCESS,shopList);
        return shopExecution;
    }

    @Override
    @Transactional
    public ShopExecution addShop(Shop shop)
            throws RuntimeException {
        // 空值判断
        if (shop != null ) {
            // 默认为上架的状态
            shop.setIfSolved(0);
            try {
                // 创建商品信息
                int effectedNum = shopDao.insertShop(shop);

                if (effectedNum <= 0) {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                }
                shop.setShopId(shopDao.getNewShopId());
            } catch (Exception e) {
                return new ShopExecution(ShopStateEnum.INNER_ERROR);
            }

            return new ShopExecution(ShopStateEnum.SUCCESS, shop);
        } else {
            return new ShopExecution(ShopStateEnum.EMPTY);
        }
    }

    @Override
    public ShopExecution successSaleShop(long shopId,long buyerId) {
        ShopExecution shopExecution;
        int effectedNum=shopDao.successSaleShop(shopId,buyerId);
        if(effectedNum!=1)
        {
            shopExecution=new ShopExecution(ShopStateEnum.ERROR);
        }
        else{
            shopExecution=new ShopExecution(ShopStateEnum.SUCCESS);
        }
        return  shopExecution;
    }

    @Override
    @Transactional
    public ShopExecution updateShop(Shop shop) {
        ShopExecution shopExecution;
        if (shop != null ) {
            int effectedNum=shopDao.updateShop(shop);
            if(effectedNum<=0){
                shopExecution=new ShopExecution(ShopStateEnum.OFFLINE);
            } else{
                shopExecution=new ShopExecution(ShopStateEnum.SUCCESS,shop);
            }
            return shopExecution;
        }
        return new ShopExecution(ShopStateEnum.EMPTY);
    }

    @Override
    @Transactional
    public ShopExecution deleteShop(long shopId,long sellerId) {
        ShopExecution shopExecution;
        try{
            int effectedNum=shopDao.deleteShop(shopId,sellerId);
            if(effectedNum<=0)
            {
                shopExecution=new ShopExecution(ShopStateEnum.DOWN);
            }else {
                shopExecution=new ShopExecution(ShopStateEnum.SUCCESS);
            }
            return shopExecution;
        }catch (Exception e){
            return new ShopExecution(ShopStateEnum.ERROR);
        }
    }
}
