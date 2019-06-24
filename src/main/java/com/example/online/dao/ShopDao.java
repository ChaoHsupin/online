package com.example.online.dao;

import com.example.online.entity.Shop;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ShopDao {
    /**
     * 查询商品列表，可输入的条件有： 商品名（模糊）
     *
     * @param shopName
     * @return
     */
    List<Shop> queryShopList(@Param("shopName")String shopName);

    /**
     * 列出自己已卖的商品列表
     *传过来的userId作为sellerId
     * @param sellerId
     * @return
     */
    List<Shop> querySaledShopList(@Param("sellerId")long sellerId);
    /**
     * 列出自己正在卖的商品列表
     *传过来的userId作为sellerId
     * @param sellerId
     * @return
     */
    List<Shop> querySellingShopList(@Param("sellerId")long sellerId);
    /**
     * 列出自己已买的商品列表
     *传过来的userId作为buyerId
     * @param buyerId
     * @return
     */
    List<Shop> queryBoughtShopList(@Param("buyerId")long buyerId);
    /**
     * 查询对应的商品总数
     *
     * @param shopName
     * @return
     */
    int queryShopCount(@Param("shopName")String shopName);

    /**
     * 通过shopId查看单个商品详情
     *
     * @param shopId
     * @return
     */
    Shop queryShopById(@Param("shopId")long shopId);

    /**
     * 随机抽取20条商品信息用于首页展示
     * @return
     */
    List<Shop> randomShopList();

    /**
     * 新增商品
     *
     * @param shop
     * @return
     */
    int insertShop(Shop shop);

    /**
     * 卖出商品后，修改该商品的信息
     * @param shopId
     * @param sellerId
     * @param buyerId
     * @return
     */
    int successSaleShop(@Param("shopId") long shopId,@Param("buyerId") long buyerId);

    /**
     * 更新商品信息
     *
     * @param shop
     * @return
     */
    int updateShop(Shop shop);

    /**
     * 删除商品
     *userId作为sellerId删除自己的商品
     * @param shopId
     * @return
     */
    int deleteShop(@Param("shopId") long shopId,@Param("sellerId") long sellerId);

    @Select("select shop_id from goods order by shop_id desc limit 1")
    long getNewShopId();
}
