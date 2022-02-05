# 2022

## 01/20

- [x] 初步建立工程
- [x] 编写后台的用户管理部分
- [x] 学习如何编写接口等
- [x] 测试数据库
  - Java 接口 SSH 连接非本机数据库失败，因此打算使用本机数据库

## 01/21

- [x] 安装本机数据库并初步安排数据库表
- [x] 测试用户管理接口
- [x] 初步编写教师管理接口

## 01/22

- [x] 编写教师管理接口
- [x] 处理密码加密
- [x] 增加超级管理员接口
- [x] 在登录的时候要返回管理员或教师的信息，以便在右上角显示
- [x] vuex 的数据保存（好像有问题）
- [x] 把 adminNickName 这个属性也都改成 nickName（方便显示）

## 01/23

- [x] 以上 TODO 完成
- [x] 创建 github 仓库，能够在云端维护
- [x] 学习 MyBatis 等

## 01/24-01/25

- [x] 发现 MyBatis Plus 对数据库的操作较为单一，甚至可以说是将 SQL 当作是 NoSQL 使用了，因此一些复杂操作还是需要去学习基础的 Mybatis
- [x] 使用 Mybatis + XML 进行连接查询

## 01/26

- [x] 编写前端成绩查询和修改页面
- [x] 学习基本的开发模式（什么？我之前都没搞会开发的框架？）了解后端的基本逻辑结构
  - Controller
  - Service
    - ServiceImpl
  - Mapper
  - Entity
- [ ] 导入成绩页面

## 01/27

- [x] 搭建 MongoDB 环境（用于存放题目等）
- [x] 基于以前的老本学习 MongoDB 数据库以及基本的 NoSQL 模式
- [x] 测试 MongoDB 的 Java API，并尝试连接数据库，增加数据库管理员验证等

MongoDB 可以设置管理员，通过命令来进行添加

```nosql
// 给 admin 表增加管理员
use admin
db.createUser({
  user: "admin", pwd: "123456",
  roles: [{ role: "userAdminAnyDatabase", db: "admin" }]
})
```

MongoDB 的每个数据库都能设置自己的管理员，因此需要手动在某个数据库进行设置操作

```nosql
// 给 test 表增加管理员
use test
db.createUser({
  user: "admin", pwd: "123456",
  roles: [{ role: "dbOwner", db: "test" }]
})
```

之后，在 `mongod.cfg` 中添加配置，设置为需要验证

```yml
security:
   authorization: enabled
```

于是每次登录都要验证了

```nosql
use test
db.auth("admin", "123456")
```

与此同时发现一个问题，Java 的 API 连接需要 admin 表进行验证，连接 test 表也需要 admin 表验证，于是在 yaml 文件里面需要有管理员名和密码。但是这个管理员居然必须是同一个人，也就是说 admin 表和 test 表都需要有 admin 这个用户，这是不是有什么问题？

- [ ] 将 MongoDB 集成到题库管理系统中
- [ ] 编写导入题库功能
  - 重新构建题目的 Schema (结构)
  - 数据处理部分 (超烦)
    - 给出题老师一个题目模板表？

| 属性 | 数据类型 | 用途 |
|:----:|:----:|:---|
| _id  | String | 自带属性 |
| id   | int | 人为规定题目 id |
| type | String | 题目类别 |
| content | String | 题目描述 |
| choices | List | 选项 |
| answer | String | 答案选项 |

# 01/28

- [ ] 查询需要重写
  - 带有关键字的查询在翻页的时候自动将关键字清空了
- [x] 将题库查询集成到界面
- [ ] 编写导入题库功能

# 02/04 及以前

玩去了，完全没有心情写😅，也就写一点注释罢了，顺便把格式稍微修正一下，需要 Controller 引入 Service，Service 引入 Mapper，Mapper 内部实现查询语句

