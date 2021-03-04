### 在线课程平台
- 项目简介：在线课程平台，分成了前台页面和后台管理页面两部分
- 做这个项目的目的主要是为了熟悉主流后端开发的开发流程和熟悉springboot，选用了微服务的架构，模仿网上的课程，并结合自己的想法进行修改和独立开发。

- ***在线Demo演示***：【[点击进入后台管理页面](http://krits.fun:9528) 用户名：admin 密码：admin】【[点击进入前台页面](http://krits.fun:3000)（施工中...）】

- 项目使用到的技术：
	- 前端：后台管理页面使用了基于Vue的[管理模板](https://github.com/PanJiaChen/vue-admin-template)；前台页面也套用了一个基于nuxt的模板进行开发；使用element-ui绘制和编排表单样式；
	- 后端：使用springboot、mybatis-plus持久化、nginx反向代理分发请求、Swagger生成接口文档、阿里云oss对象存储、阿里云视频点播服务、maven管理项目、redis缓存等；

项目目录和功能模块介绍：

- 后端目录（Back-end目录）：使用maven管理，包含两个子模块
	- common模块: 一些公用的配置、工具类等，包含两个子模块
		- common_utils: 各模块公用的工具类等
		- service_base: 全局配置类、全局异常捕获、swagger配置等
	- service模块：服务模块，其中包含若干个微服务
 		- service-edu【[接口文档链接](http://krits.fun:8001/swagger-ui.html#/)】: 主要的接口都在这里，包含了管理后台的登录、教师、课程信息、课程章节、课程小节的数据库操作和业务逻辑。 
		- service-oss【[接口文档链接](http://krits.fun:8002/swagger-ui.html#/)】: 文件上传服务，阿里云oss存储对接
		- service-video【[接口文档链接](http://krits.fun:8003/swagger-ui.html#/)】： 视频上传和获取视频播放凭证，阿里云视频点播服务对接
		- service-cms【[接口文档链接](http://krits.fun:8004/swagger-ui.html#/)】: Banner模块(主页广告牌)，针对Banner的CRUD以及返回首页显示数据，因为这个模块可能经常访问，所以加入了redis缓存
		- servce-ucenter【[接口文档链接](http://krits.fun:8006/swagger-ui.html#/)】: 用户中心模块，这个模块提供登录注册功能，并为用户办理基于JWT技术生成的token凭证
- 前端目录（Front-end目录）: 包含两个前端项目
	- vue-back-ManagerSystem: 后台管理页面项目
	- vue-front: 前台页面项目
