# artapp

大创😅我的大创😅😅😅我就是苦逼的打工人😅

## Installation

### 克隆项目

```shell
$ git clone
$ cd artapp
```

### 安装前端框架

```shell
$ cd vue-demo
$ npm install
```
### 安装后端框架（需要使用 maven）

```shell
$ cd ../spring-demo
$ mvn clean install
```

### 数据库支持

- [Mysql](https://www.mysql.com/)
- [MongoDB](https://www.mongodb.com/)

数据库帐号密码细节请修改 `spring-demo/src/main/resources/application.yaml`

另外，我给 MongoDB 设定了必须要有帐号密码，如果没有设置，请在 `changelog.md` 中查看说明

## 项目运行

### 后端

```shell
$ cd ./spring-demo
$ mvn spring-boot:run
```

### 前端

```shell
$ cd ./vue-demo
$ npm run serve
```

推荐使用 Intellij IDEA 作为 IDE

## 项目部署

### 数据库

#### 安装 MySQL

略

#### 安装 MongoDB

设置数据库管理员部分详见 `changelog.md`

### 后端

1. 修改 `spring-demo/src/main/resources/application-prod.yaml`，使得其中的配置与服务器端的数据库配置一致
2. 使用 maven 打包

    ```shell
    $ mvn clean package
    ```
3. 上传到服务器
4. 启动服务

    ```shell
    $ nohup java -jar target/spring-demo-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod &
    ```
   
### 前端

1. 使用 npm 构建
    ```shell
    $ npm run build
   ```
2. 上传到服务器
3. 配置 nginx 反向代理
