<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<h1>使用solr进行增删改查操作</h1>
<div id="app">
    <button v-on:click="operation('add')">增加</button>
    <button v-on:click="operation('delete')">删除</button>
    <button v-on:click="operation('update')">修改</button>
    <button v-on:click="operation('query')">查询</button>
    <button v-on:click="operation('queryLight')">高亮查询</button>
</div>
${message}
<%
    String from = (String)request.getAttribute("from");
if ("query".equals(from)){
%>
<table border="1" cellpadding="10">
    <tr>
        <td>id</td>
        <td> 用户名</td>
        <td>年龄</td>
        <td>密码</td>
        <td>描述</td>
    </tr>
    <c:forEach items="${userList}" var="user">
        <tr>
            <td> ${user.id}</td>
            <td> ${ user.username }</td>
            <td> ${ user.age }</td>
            <td> ${ user.password }</td>
            <td> ${ user.desc }</td>
        </tr>
    </c:forEach>
</table>
<%
    }
%>
<div style="display: none" id="addDiv">
        <form method="post" action="/user/add">
        <p>用户名：</p>
        <input v-model="username" name="username" placeholder="请输入用户名">
        <p>密码:</p>
        <input v-model="password" type="password" name="password" placeholder="请输入密码">
        <p>年龄:</p>
        <input v-model="age" placeholder="请输入年龄" name="age">
        <p>描述：</p>
        <textarea v-model="desc" placeholder="请输入对本人的描述……" name="desc"></textarea>
         <input type = "submit" value="提交">
        </form>
</div>

<div style="display: none" id="queryDiv">
    <h3>请输入查询条件</h3>
    <form method="post" action="/user/query?isHighLight=false">
        <p>Id：</p>
        <input v-model="id" name="id">
        <p>用户名：</p>
        <input v-model="username" name="username">
        <p>描述：</p>
        <textarea v-model="desc" name="desc"></textarea>
        <input type = "submit" value="查询">
    </form>

</div>
<div style="display: none" id="queryLightDiv">
    <h3>请输入查询条件</h3>
    <form method="post" action="/user/query?isHighLight=true">
        <p>Id：</p>
        <input v-model="id" name="id">
        <p>用户名：</p>
        <input v-model="username" name="username">
        <p>描述：</p>
        <textarea v-model="desc" name="desc"></textarea>
        <input type = "submit" value="查询">
    </form>

</div>

<div style="display: none" id="deleteDiv">
    <h3>请输入需要删除的对象id</h3>
    <form method="post" action="/user/delete">
        <p>Id：</p>
        <input v-model="id" name="id">
        <input type = "submit" value="确定">
    </form>
</div>
<script src="/resources/js/vue.min.js"></script>
<script src="/resources/js/jquery-3.0.0.min.js"></script>
<script>
    new Vue({
        el: '#app',
        methods: {
            operation: function (message) {
                if(message == "add"){
                    $("#queryLightDiv").hide();
                    $("#deleteDiv").hide();
                    $("#addDiv").show();
                }else if(message == "query"){
                    $("#addDiv").hide();
                    $("#deleteDiv").hide();
                    $("#queryDiv").show();
                }else if(message == "queryLight"){
                    $("#addDiv").hide();
                    $("#deleteDiv").hide();
                    $("#queryLightDiv").show();
                }else if(message == "delete"){
                    $("#addDiv").hide();
                    $("#queryLightDiv").hide();
                    $("#deleteDiv").show();
                }
            }
        }
    })
</script>
</body>
</html>