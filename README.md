## 微企业协同软件(VOA)

- 软件综合实习作业
- 展示地址：https://voa.yinjiahui.cn
- 作者：尹嘉辉、王琰博、欧阳昊宇
- 指导老师：颜雪松

## 项目简介

本项目使用了前后端分离的开发模式。后端主要使用SpringBoot框架，前端有Android端及Web端。

[![wV5pjL.jpg](https://t1.picb.cc/uploads/2021/05/27/wV5pjL.jpg)](https://www.picb.cc/image/wV5pjL)

#### 后端介绍

##### 模块划分

|     模块     | 释义           |                 备注                 |
| :----------: | :------------- | :----------------------------------: |
|  voa-common  | 项目公共模块   |     该模块只是作为核心依赖包存在     |
|    voa-db    | 数据库交互模块 | 该模块主要与数据库交互以及定义实体类 |
| voa-security | 权限管理模块   |  该模块主要进行权限验证、请求过滤等  |
|  voa-portal  | 后端接口模块   |         该模块实现大部分接口         |

##### 使用技术

+ 项目构建：Maven
+ Web框架：SpringBoot
+ 数据库ORM：MyBatis
+ 数据库连接池：HikariCP
+ 数据库：MySQL
+ 缓存：Redis
+ Web服务器：Nginx

#### Android介绍

#### Web前端介绍



