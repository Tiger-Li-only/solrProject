package com.springapp.mvc.service.impl;
import com.springapp.mvc.domain.User;
import com.springapp.mvc.service.IUserService;
import com.springapp.mvc.utils.SolrUtil;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created by Tiger-Li on 2017/10/22.
 */
@Service
public class UserServiceImpl implements IUserService {
    @Override
    public String addUser(User user) throws IOException,SolrServerException{
              user.setId(UUID.randomUUID().toString());
              try{
                  SolrUtil.addDocumentByBean(user,"new_core");
              }catch (Exception e){
                  e.printStackTrace();
              }
              return "success";

    }

    @Override
    public void deleteUser(User user) throws IOException, SolrServerException {
        try{
            SolrUtil.deleteDocumentById(user.getId(),"new_core");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getUsers(User user, boolean isHightLight) throws IOException, SolrServerException {
        try{
            List userList = SolrUtil.getDocument("new_core",user,isHightLight);
            return userList;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
