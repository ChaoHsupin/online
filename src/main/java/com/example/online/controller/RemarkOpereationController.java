package com.example.online.controller;

import com.example.online.entity.Remark;
import com.example.online.service.RemarkService;
import com.example.online.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/remark")
public class RemarkOpereationController {
    @Autowired
    RemarkService remarkService;

    /**
     *添加评价
     * @param request
     * @return
     */
    @RequestMapping(value = "/addremark" ,method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object>addRemarkPageInfo(HttpServletRequest request){
        Map<String,Object>modelMap=new HashMap<String, Object>();
        try{
            String remarkInfo= HttpServletRequestUtil.getString(request,"remarkInfo");
            long remarkUserId=HttpServletRequestUtil.getLong(request,"userId");
            long remarkedUserId=HttpServletRequestUtil.getLong(request,"sellerId");
            Date createTime=new Date();
            Remark remark=new Remark();
            remark.setRemarkInfo(remarkInfo);
            remark.setRemarkedUserId(remarkedUserId);
            remark.setRemarkUserId(remarkUserId);
            remark.setCreateTime(createTime);
            int effectedNum=remarkService.insertRemark(remark);
            if(effectedNum==1)
            {
                modelMap.put("success",true);
                modelMap.put("state","评价成功！");
            }
            else {
                modelMap.put("success",false);
                modelMap.put("errMsg","评价失败");
            }
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
        }
        return modelMap;
    }

    /**
     * 删除评价
     */
    @RequestMapping(value = "/deleteremarkpageinfo" ,method = RequestMethod.GET)
    private Map<String,Object>deleteRemarkPageInfo(HttpServletRequest request){
        Map<String,Object>modelMap=new HashMap<String,Object>();
        try{
            long userId=(long)request.getSession().getAttribute("userId");
            long remarkUserId=HttpServletRequestUtil.getLong(request,"remarkUserId");
            String remarkInfo=HttpServletRequestUtil.getString(request,"remarkInfo");
            Remark remark=new Remark();
            remark.setRemarkInfo(remarkInfo);
            remark.setRemarkUserId(remarkUserId);
            int effectedNum=remarkService.deleteRemark(remark,userId);
            if(effectedNum==1)
            {
                modelMap.put("success",true);
                modelMap.put("state","删除评论成功");
            }
            else {
                modelMap.put("success",false);
                modelMap.put("errMsg","删除评论失败");
            }
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
        }
        return modelMap;

    }
    /**
     * 查看评价列表
     */
    @RequestMapping(value = "/remarkpagelistinfo" ,method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listRemarkPageInfo(HttpServletRequest request){
        Map<String,Object>modelMap=new HashMap<String, Object>();
        long remarkedUserId=HttpServletRequestUtil.getLong(request,"remarkedUserId");
        List<Remark>listRemark=remarkService.listRemark(remarkedUserId);
        if(listRemark!=null)
        {
            modelMap.put("success",true);
            modelMap.put("remarkList",listRemark);

        }
        else {
            modelMap.put("success",false);
            modelMap.put("errMsg","当前用户无评价");
        }
        return modelMap;
    }
}
