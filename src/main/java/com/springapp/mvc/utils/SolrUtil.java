package com.springapp.mvc.utils;
import com.springapp.mvc.domain.User;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.util.List;
import java.util.Map;

/**
 * Created by Tiger-Li on 2017/10/22.
 */
public class SolrUtil {
    public static String SOLR_URL = "http://localhost:8090/solr";
    /**
     * 获取solr服务
     *
     * @return
     */
    public static HttpSolrClient getSolrClient(String core) {
        HttpSolrClient hsc = new HttpSolrClient(SOLR_URL + core);
        return hsc;
    }



    /**
     * 添加文档，通过bean方式
     *
     * @param user
     * @param core
     * @throws Exception
     */
    public static void addDocumentByBean(User user, String core)
            throws Exception {
        HttpSolrClient solrClient = getSolrClient("/" + core);
        solrClient.addBean(user);
        commitAndCloseSolr(solrClient);
    }

    /**
     * 添加文档，通过bean方式
     *
     * @param persons
     * @param core
     * @throws Exception
     */
    public static void addDocumentByBeans(List<User> persons, String core)
            throws Exception {
        HttpSolrClient solrClient = getSolrClient("/" + core);
        solrClient.addBeans(persons);
        commitAndCloseSolr(solrClient);
    }

    /**
     * 根据id删除索引
     *
     * @param userId
     * @param core
     * @throws Exception
     */
    public static void deleteDocumentById(String userId, String core)
            throws Exception {
        HttpSolrClient solrClient = getSolrClient("/" + core);
        solrClient.deleteById(userId);
        commitAndCloseSolr(solrClient);
    }

    /**
     * 根据id集合删除索引
     *
     * @param ids
     * @param core
     * @throws Exception
     */
    public static void deleteDocumentByIds(List<String> ids, String core)
            throws Exception {
        HttpSolrClient solrClient = getSolrClient("/" + core);
        solrClient.deleteById(ids);
        commitAndCloseSolr(solrClient);
    }

    /**
     * 查询方法
     * @param core
     * @param user
     * @param isHighlighting
     * @return
     * @throws Exception
     */
    public static List<User> getDocument(String core,User user,boolean isHighlighting) throws Exception {
        HttpSolrClient solrClient = getSolrClient("/" + core);
        int page = 1;
        int rows = 10;

        SolrQuery solrQuery = new SolrQuery("*:*"); // 构造搜索条件
        if (user.getId() != ""){
            solrQuery.setQuery("id:" + user.getId()); // 搜索关键词
        }
        if (user.getUsername() != ""){
            solrQuery.setQuery("username:*"+user.getUsername()+"*");
        }
        if (user.getDesc() != ""){
            solrQuery.setQuery("desc:*"+user.getDesc()+"*");
        }

        // 设置分页
        solrQuery.setStart((Math.max(page, 1) - 1) * rows);
        solrQuery.setRows(rows);

        // 是否需要高亮
        if (isHighlighting) {
            // 设置高亮
            solrQuery.setHighlight(true); // 开启高亮组件
            solrQuery.addHighlightField("username");// 高亮字段
            solrQuery.addHighlightField("id");
            solrQuery.setHighlightSimplePre("<span style='color:red;'>");// 标记，高亮关键字前缀
            solrQuery.setHighlightSimplePost("</span>");// 后缀
        }

        QueryResponse queryResponse = solrClient.query(solrQuery);
        List<User> persons = queryResponse.getBeans(User.class);

        if (isHighlighting) {
            // 将高亮的标题数据写回到数据对象中
            Map<String, Map<String, List<String>>> map = queryResponse.getHighlighting();
            for (Map.Entry<String, Map<String, List<String>>> highlighting : map.entrySet()) {
                for (User person : persons) {
                    if (!highlighting.getKey().equals(person.getId().toString())) {
                        continue;
                    }
                    if(highlighting.getValue().get("username") != null){
                        person.setUsername(highlighting.getValue().get("username")+"");
                    }
                    if(highlighting.getValue().get("id") != null){
                        person.setId(highlighting.getValue().get("id")+"");
                    }
                    break;
                }
            }
        }
       return persons;
    }



    /**
     * 提交以及关闭服务
     *
     * @param solrClient
     * @throws Exception
     */
    public static void commitAndCloseSolr(HttpSolrClient solrClient)
            throws Exception {
        solrClient.commit();
        solrClient.close();
    }

}
