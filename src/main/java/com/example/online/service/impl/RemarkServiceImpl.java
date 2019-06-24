package com.example.online.service.impl;

import com.example.online.dao.RemarkDao;
import com.example.online.entity.Remark;
import com.example.online.service.RemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RemarkServiceImpl implements RemarkService {
    @Autowired
    RemarkDao remarkDao;

    @Override
    public int insertRemark(Remark remark) {
        if(remark.getRemarkInfo()!=null)
        {
            return remarkDao.insertRemark(remark);
        }
        else {
            return  0;
        }
    }

    @Override
    public int deleteRemark(Remark remark,long remarkId) {
        return remarkDao.deleteRemark(remark,remarkId);
    }

    @Override
    public List<Remark> listRemark(long remarkedUserId) {
        return remarkDao.listRemark(remarkedUserId);
    }
}
