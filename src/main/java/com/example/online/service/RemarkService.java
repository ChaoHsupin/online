package com.example.online.service;

import com.example.online.entity.Remark;

import java.util.List;

public interface RemarkService {
    int insertRemark(Remark remark);
    int deleteRemark(Remark remark,long remarkId);
    List<Remark> listRemark(long remarkedUserId);
}
