package com.springapp.mvc.service;

import com.springapp.mvc.domain.User;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrException;

import java.io.IOException;
import java.util.List;

/**
 * Created by Tiger-Li on 2017/10/22.
 */
public interface IUserService {
    public String addUser(User user) throws IOException,SolrServerException;
    public void deleteUser(User user) throws IOException,SolrServerException;
    public List<User> getUsers(User user,boolean isHightLight) throws IOException,SolrServerException;
}
