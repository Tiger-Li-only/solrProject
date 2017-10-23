package com.springapp.mvc.domain;

import org.apache.solr.client.solrj.beans.Field;

/**
 * Created by Tiger-Li on 2017/10/22.
 */
public class User {
    @Field("id")
    public String id;
    @Field("username")
    public String username;
    @Field("password")
    public String password;
    @Field("age")
    public int age;
    @Field("desc")
    public String desc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
