package com.example.online.service;

import com.example.online.dto.ImageHolder;
import com.example.online.dto.ShopExecution;
import com.example.online.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopService {
    /**
     * 查询商品列表，可输入的条件有： 商品名（模糊）
     *
     * @param shopName
     * @return
     */
    ShopExecution getShopList(String shopName);
    /**
     * 列出自己已卖的商品列表
     *传过来的userId作为sellerId
     */
    ShopExecution querySaledShopList(long sellerId);
    /**
     * 列出自己正在卖的商品列表
     *传过来的userId作为sellerId
     */
    ShopExecution querySellingShopList(long sellerId);
    /**
     * 列出自己已买的商品列表
     *传过来的userId作为buyerId
     */
    ShopExecution queryBoughtShopList(long buyerId);

    /**
     * 通过商品Id查询唯一的商品信息
     *
     * @param shopId
     * @return
     */
    ShopExecution getShopById(long shopId);

    /**
     * 随机获取20条商品信息用于前台展示
     * @return
     */
    ShopExecution randomShopList();
    /**
     * 添加商品信息以及图片处理
     *
     * @param shop
     * @return
     */
    ShopExecution addShop(Shop shop);

    /**
     * 卖出商品后，修改该商品的信息
     */
    ShopExecution successSaleShop( long shopId,long buyerId);
    /**
     * 修改商品信息以及图片处理
     *
     * @param shop
     * @return
     */
    ShopExecution updateShop(Shop shop);

    /**
     * 删除商品
     * @param shopId
     * @return
     */
    ShopExecution deleteShop(long shopId,long sellerId);

}
