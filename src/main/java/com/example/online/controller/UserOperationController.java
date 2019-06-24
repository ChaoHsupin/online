package com.example.online.controller;

import com.example.online.entity.User;
import com.example.online.service.UserService;
import com.example.online.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserOperationController {
    @Autowired
    UserService userService;

    /**
     * 注册用户
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/registeruserpageinfo")
    private Map<String, Object> registerUserPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            String userName = HttpServletRequestUtil.getString(request, "userName");
            String schoolNumber = HttpServletRequestUtil.getString(request, "schoolNumber");
            String schoolPassword = HttpServletRequestUtil.getString(request, "schoolPassword");
            User user = new User();
            user.setUserName(userName);
            user.setSchoolNumber(schoolNumber);
            user.setSchoolPassword(schoolPassword);
            if (userName.trim() == null || schoolNumber.trim() == null || schoolPassword.trim() == null) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "请补充完整信息");
            } else if (userService.loginUser(schoolNumber, schoolPassword) != null) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "该用户已经被注册");
            } else {
                userService.insertUser(userName, schoolNumber, schoolPassword);
                modelMap.put("success", true);
                modelMap.put("user", user);
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        return modelMap;
    }

    /**
     * 用户登录
     * 展示用户界面信息 （只能查看自己的信息）
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/showuserpageinfo", method = RequestMethod.GET)
    private Map<String, Object> showUserPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            String schoolNumber = HttpServletRequestUtil.getString(request, "schoolNumber");
            String schoolPassword = HttpServletRequestUtil.getString(request, "schoolPassword");
            User user = userService.loginUser(schoolNumber, schoolPassword);
            if (user != null) {
                modelMap.put("success", true);
                modelMap.put("user", user);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "登录失败，请重新登陆");
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }

        return modelMap;
    }


    /**
     * 上传用户头像
     * @param userImgFile
     * @return
     */
    @Value("${web.upload.path}")
    private String uploadPath;

    @PostMapping("/uploadprofile")
    @ResponseBody
    public Map<String,Object> upload(@RequestParam("file") MultipartFile multipartFile,@RequestParam("userId") long userId) {
        //获取文件名
        String fileName = multipartFile.getOriginalFilename();
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //重新生成文件名
        fileName = userId + suffixName;
        String newPath=uploadPath+"profile/" + fileName;
        //指定本地文件夹存储图片
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            //将图片保存到static文件夹里
            multipartFile.transferTo(new File(newPath));
            modelMap.put("success",true);
            return modelMap;
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success",false);
            modelMap.put("errMsg","图片上传错误!");
            return modelMap;
        }
    }



    /**
     * 用户修改个人信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateuserpageinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> uodateUserPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            String userName = HttpServletRequestUtil.getString(request, "userName");
            long userId = HttpServletRequestUtil.getLong(request, "userId");
//            String userImg=HttpServletRequestUtil.getString(request,"userImg");
            String telephone = HttpServletRequestUtil.getString(request, "telephone");
            String introduceMyself = HttpServletRequestUtil.getString(request, "introduceMyself");
            String qq = HttpServletRequestUtil.getString(request, "qq");
            User user = new User();
            user.setQq(qq);
            user.setUserId(userId);
            user.setIntroduceMyself(introduceMyself);
            user.setTelephone(telephone);
            user.setUserImg("");
            user.setUserName(userName);
            int effected = userService.updateUserInfo(user);
            if (effected == 1) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "修改信息失败！");
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        return modelMap;
    }

    /**
     * 注销用户账号
     */
    @RequestMapping(value = "deleteuserpageinfo", method = RequestMethod.GET)
    private Map<String, Object> deleteUserPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            long userId = (long) request.getSession().getAttribute("userId");
            int effectedNum = userService.deleteUser(userId);
            if (effectedNum == 1) {
                modelMap.put("success", true);
                modelMap.put("state", "注销用户成功");
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "用户注销失败");
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }

        return modelMap;
    }
}
