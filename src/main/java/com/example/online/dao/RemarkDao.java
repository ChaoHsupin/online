package com.example.online.dao;

import com.example.online.entity.Remark;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RemarkDao {
    int insertRemark(Remark remark);
    //当userId和该条评论的评论者id一样时，才能删除（只能删除自己的评价）
    int deleteRemark(@Param("remark") Remark remark, @Param("userId") long userId);
    //根据被评价者的id列出所有评价
    List<Remark>listRemark(long remarkedUserId);
}
