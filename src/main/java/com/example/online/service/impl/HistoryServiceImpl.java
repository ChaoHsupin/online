package com.example.online.service.impl;

import com.example.online.dao.HistoryDao;
import com.example.online.dao.ShopDao;
import com.example.online.dto.ShopExecution;
import com.example.online.entity.History;
import com.example.online.entity.Shop;
import com.example.online.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    HistoryDao historyDao;
    @Autowired
    ShopDao shopDao;
    @Override
    public int insertToHistory(History history) {
        History history1=historyDao.getHistory(history.getShopId(),history.getUserId());
        //如果历史记录中没有该条记录，说明这是第一次浏览，插入
        if(history1==null)
        {
            return historyDao.insertHistory(history);
        }
        //否则不插入
        return 0;
    }

    @Override
    public int insertToCollectedList(History history) {
        History history1=historyDao.getHistory(history.getShopId(),history.getUserId());
        //如果历史记录中没有该条记录，说明这是第一次浏览，先插入历史记录，再添加至收藏列表
        if(history1==null)
        {
            historyDao.insertHistory(history);
            return historyDao.insertCollectedList(history);
        }
        else{
            return historyDao.insertCollectedList(history);
        }
    }

    @Override
    public int deleteFromHistory(long userId,long shopId) {
        return historyDao.deleteFromHistory(userId,shopId);
    }

    @Override
    public int deleteFromCollectedList(long userId,long shopId) {
        return historyDao.deleteFromCollectedList(userId,shopId);
    }

    @Override
    public History getHistory(long shopId, long userId) {
        return historyDao.getHistory(shopId,userId);
    }

    @Override
    public History getCollected(long shopId, long userId) {
        return historyDao.getCollected(shopId,userId);
    }

    @Override
    public int deleteAllHistory(long userId) {
        return historyDao.deleteAllHistory(userId);
    }

    @Override
    @Transactional
    public List<History> listCollectedList(long userId) {
        return historyDao.listCollectedList(userId);


    }

    @Override
    public int deleteAllCollectedList(long userId) {
        return historyDao.deleteAllCollectedList(userId);
    }

    @Override
    public List<History> listHistory(long userId) {
        return historyDao.listHistory(userId);
    }
}
