# artapp

å¤§åðæçå¤§åðððæå°±æ¯è¦é¼çæå·¥äººð

## Installation

### åéé¡¹ç®

```shell
$ git clone
$ cd artapp
```

### å®è£åç«¯æ¡æ¶

```shell
$ cd vue-demo
$ npm install
```
### å®è£åç«¯æ¡æ¶ï¼éè¦ä½¿ç¨ mavenï¼

```shell
$ cd ../spring-demo
$ mvn clean install
```

### æ°æ®åºæ¯æ

- [Mysql](https://www.mysql.com/)
- [MongoDB](https://www.mongodb.com/)

æ°æ®åºå¸å·å¯ç ç»èè¯·ä¿®æ¹ `spring-demo/src/main/resources/application.yaml`

å¦å¤ï¼æç» MongoDB è®¾å®äºå¿é¡»è¦æå¸å·å¯ç ï¼å¦ææ²¡æè®¾ç½®ï¼è¯·å¨ `changelog.md` ä¸­æ¥çè¯´æ

## é¡¹ç®è¿è¡

### åç«¯

```shell
$ cd ./spring-demo
$ mvn spring-boot:run
```

### åç«¯

```shell
$ cd ./vue-demo
$ npm run serve
```

æ¨èä½¿ç¨ Intellij IDEA ä½ä¸º IDE

## é¡¹ç®é¨ç½²

### æ°æ®åº

#### å®è£ MySQL

ç¥

#### å®è£ MongoDB

è®¾ç½®æ°æ®åºç®¡çåé¨åè¯¦è§ `changelog.md`

### åç«¯

1. ä¿®æ¹ `spring-demo/src/main/resources/application-prod.yaml`ï¼ä½¿å¾å¶ä¸­çéç½®ä¸æå¡å¨ç«¯çæ°æ®åºéç½®ä¸è´
2. ä½¿ç¨ maven æå

    ```shell
    $ mvn clean package
    ```
3. ä¸ä¼ å°æå¡å¨
4. å¯å¨æå¡

    ```shell
    $ nohup java -jar target/spring-demo-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod &
    ```
   
### åç«¯

1. ä½¿ç¨ npm æå»º
    ```shell
    $ npm run build
   ```
2. ä¸ä¼ å°æå¡å¨
3. éç½® nginx ååä»£ç
