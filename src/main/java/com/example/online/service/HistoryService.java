package com.example.online.service;

import com.example.online.dto.ShopExecution;
import com.example.online.entity.History;
import com.example.online.entity.Shop;

import java.util.List;

public interface HistoryService {
   History getHistory(long shopId,long userId);
   History getCollected(long shopId,long userId);
   List<History> listHistory(long userId);
    List<History> listCollectedList(long userId);
   int insertToHistory(History history);
   int insertToCollectedList(History history);
    int deleteFromHistory(long userId,long shopId);
    int deleteAllHistory(long userId);
    int deleteFromCollectedList(long userId,long shopId);
    int deleteAllCollectedList(long userId);
}
