package com.springapp.mvc.controller;

import com.springapp.mvc.domain.User;
import com.springapp.mvc.service.IUserService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

/**
 * Created by Tiger-Li on 2017/10/22.
 */
@Controller
@RequestMapping("/user")
public class UserController {
@Autowired
public IUserService userService;

    /**
     * 新增
     * @param user
     * @param model
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    @RequestMapping("/add")
    public String addUser(User user,ModelMap model) throws IOException,SolrServerException {
        String result = userService.addUser(user);
        if ("success".equals(result)){
            model.addAttribute("message","添加成功!");
        }else if("error".equals(result)){
            model.addAttribute("message","添加失败!");
        }
        return "hello";
    }


    /**
     * 删除
     * @param user
     * @param model
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    @RequestMapping("/delete")
    public String deleteUser(User user,ModelMap model) throws IOException,SolrServerException {
        userService.deleteUser(user);
        model.addAttribute("message","删除成功");
        return "hello";
    }

    /**
     * 查询
     * @param user
     * @param model
     * @param isHighLight 是否高亮
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    @RequestMapping("/query")
    public String getDocument(User user,ModelMap model,@RequestParam boolean isHighLight)  throws IOException,SolrServerException {
        model.addAttribute("userList",userService.getUsers(user,isHighLight));
        model.addAttribute("from","query");
        return "hello";
    }

}
