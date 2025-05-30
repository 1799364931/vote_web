# 投票系统 —— 数据库课程设计

[接口文档](http://localhost:8888/swagger-ui/index.html)
## 1 需要实现的功能
+ 用户(管理员)的注册登录
+ 创建投票(创建投票选项)

## 2 功能实现
### 2.1 注册登录
+ 实现用户注册和登录功能，使用用户名和密码进行身份验证。
+ session管理，确保用户登录状态。
+ 网页路由，登陆后跳转到Home页面。

### 2.2 请求携带token

登录存储token:
```js
localStorage.setItem("token", data.token); // 持久存储（关闭页面仍然有效）
sessionStorage.setItem("token", data.token); // 仅当前会话有效
```

请求携带token:
```js
fetch("http://localhost:8888/protected-endpoint", {
        method: "GET",
        headers: {
        "Authorization": `Bearer ${localStorage.getItem("token")}`
        }
        })
        .then(response => response.json())
        .then(data => console.log("获取的数据:", data))
        .catch(error => console.error("请求失败:", error));

```

后端解析token:

```java

// @RequestHeader("Authorization") String authorizationHeader

```

### 2.2 投票逻辑

+ 显示所有投票信息
+ 单个投票的信息显示
+ 创建投票
+ 考虑多轮投票？

## 3 网页路由

``` 
/login
/register
/home {
    /user{
        /edit [GET | POST | PUT | DELETE]
        /logout [GET | POST | PUT | DELETE]
        /vote [GET | POST | PUT | DELETE]
    }
    /vote_id{
        /{option_id}  [GET | POST | PUT | DELETE]  
    }
    /create{
        /option [POST | GET | PUT | DELETE]  
    }   
    
} 
```

## 4 网页设计

先设计HTML+CSS前端页面 再将对应的button和url对应到js中作为接口

### 4.1 登录页面

### 4.2 Home页面

+ 显示所有投票信息
+ 每个投票信息包含投票标题、创建者、创建时间、投票状态等。
+ 菜单栏
  + 用户信息
  + 创建投票
  + 搜索投票

### 4.3 创建投票

### 4.4 投票详情页