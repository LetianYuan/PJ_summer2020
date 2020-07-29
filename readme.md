# PJ说明文档

> 袁乐天 19302010019

## 项目完成情况

> 基础部分全部已完成，Bonus部分完成了“项目说明文档”、“验证码”、“实时聊天”、“云部署”。

### 服务器云部署

网址：[http://letianyuan.xyz:63337/PJ/](http://letianyuan.xyz:63337/PJ/)

> 说明：考虑到国内部署网站会需要ICP备案和网安备案，ICP备案较容易通过，而“旅游图片分享平台”属于“交互式网站”，欲通过网安备案则需要设立安全员、审核员等，基本不可能通过。所以没有部署在80端口，而是选择了一个不知名端口以防查水表。
>
> 因此，网站会在助教批改完PJ后及时关闭。

### 首页

* 首页的轮播图采用原生`js`代码与`css`动画编写，详见`js/index_carousel.js`文件
* “最新”利用SQL语句的`ORDER BY updatetime DESC`来实现

### 登陆

* 用户首次登陆时是不需要输入**验证码**的，用户首次登陆失败之后，会要求用户输入**验证码**

* **验证码**的“看不清，换一张”功能采用`ajax`实现

* 登陆页的背景动画是借鉴了该大佬主页([https://csdoker.com/](https://csdoker.com/))的背景动画

* “密码不得明文传输”这一功能用到了`google`的`sha256.js`，用户传输账号密码之前会进行`sha256`加密再传输，详见`js/login_encryptPassword.js`

* 用户的密码进行哈希加盐，不明文存储在数据库

  `数据库里的密码 = sha256(sha256(明文密码 + 用户名) + 随机盐)`

### 注册

* 考虑到用户可以删除`js`代码来越过合法性检查上传不合法信息，所以我在前端与后端同时检测了数据的合法性
* 检查用户名是否以重复采用`ajax`实现

### 详情页面

* 图片的热度定义为收藏的数量
* 用户如果没有登陆时点击收藏，会跳转到登录页面

### 搜索

> “翻页时请不要刷新页面”是一个很奇怪的要求，这是不符合RESTful风格的

* “翻页时请不要刷新页面”利用了`ajax`来实现
* 按热度排序和时间排序主要利用了SQL语句的`ORDER BY`

### 上传界面

> “作者”输入框也是一个很奇怪的要求，作者我直接设置为了用户的用户名

* 考虑到用户可以删除`js`代码来越过合法性检查上传不合法信息，所以我在前端与后端同时检测了上传数据的合法性
* 下拉栏联动直接传了一个1MB大小的`js`文件，以节省服务器算力
* 上传图片之后会对图片进行压缩，原图放在`large`文件夹中，压缩后的图片放在`small`文件夹中

### 我的照片

* 删除照片时会对用户身份进行检验以防止CSRF攻击
* 删除照片时会同时更新“我的足迹”、“我的收藏”、“我的照片”相关的数据库

### 好友列表

* 用户可以点击用户名查看对方的收藏

### 个人中心

* 用户可以在个人中心设置他人查看自己收藏的权限，采用`ajax`实现
* 用户可以在个人中心搜索并申请添加其他人为好友，也是采用`ajax`实现

### 验证码

* 验证码图片的实现摘自[https://www.cnblogs.com/lovetq520/p/11676333.html](https://www.cnblogs.com/lovetq520/p/11676333.html)

  并对其做了些改动

### 聊天功能

* 聊天采用`websocket`实现，聊天消息会对“用户名”，“对方的用户名”，“发送内容”串行化
* 聊天窗口采用`<iframe>`，悬浮在页面的右下方

### 网络安全

> 本人学识疏浅，能力有限，在我的知识水平内做了最大程度的网络安全防护

* 所有涉及到需要用户登陆的操作都会利用`HttpFilter`对用户是否登陆进行检查，详见`CheckLogin.java`
* 用户聊天、用户上传图片等均会进行防XSS攻击，详见`HTMLFilter.java`和`XSSFilter.java`
* 用户删除图片、取消收藏、删除好友等都会进行用户身份的检验，以防CSRF攻击
* 绝大部分涉及到数据库增删改查的操作都进行了数据库语句的预处理，实在不能进行预处理的地方使用了SQL的`concat`函数并对SQL关键字进行检查，以防SQL注入