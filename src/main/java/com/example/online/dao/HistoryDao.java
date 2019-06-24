package com.example.online.dao;

import com.example.online.entity.History;
import com.example.online.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HistoryDao {
    History getHistory(@Param("shopId") long shopId,@Param("userId") long userId);//查看单条历史记录
    History getCollected(@Param("shopId") long shopId,@Param("userId") long userId);//查看单条收藏记录
    List<History> listHistory(long userId);//用户查看历史记录（浏览过的商品信息）
    List<History> listCollectedList(long userId);//用户查看收藏列表
    int insertHistory(History history);//插入至历史记录
    int insertCollectedList(History history);//插入至收藏列表（将该history的if_collected设为1）
    int deleteFromHistory(@Param("userId") long userId,@Param("shopId") long shopId);//删除某条历史记录
    int deleteAllHistory(long userId);//删除所有历史记录
    int deleteFromCollectedList(@Param("userId")long userId,@Param("shopId")long shopId);//删除某条收藏记录 即将该history的if_collected设为0
    int deleteAllCollectedList(long userId);//删除所有收藏记录
}
