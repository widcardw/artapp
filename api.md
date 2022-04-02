# Art App 接口文档

服务器 IP 1.116.110.23，端口号 8081

## Admin

```ts
interface Admin {
    id: int;
    adminName: string;
    nickName: string;
    password: string;
}
```

| 方式   |接口| 参数                        | 功能  |
|------|---|----------------------------------------------|--------|
| post |`/admin/login`| `{adminName: string, password: md5(string)}` |  管理员登录 |
| get |`/admin/info` | `adminName: string` | 获取管理员信息 |
| put |`/admin` | `{adminName: string, nickName: string, password: md5(string)}`| 更新管理员信息 |

## Course（其实这个被改成试卷名称了）

```ts
interface Course {
    id: int;
    courseNo: string;
    courseName: string;
}
```

|方式 | 接口                  |参数|功能|
|------|---------------------|----------------------------------------------|--------|
|post| `/course`           |`{courseNo: string, courseName: string}`|新建课程|
|put| `/course`           |`{id?: int, courseNo: string, courseName: string}`|更改课程|
|get| `/course`           |`{pageNum?: int, pageSize?: int, search?: string}`|查找课程|
|get| `/course/no`        |None|获取所有课程的课程号|
|get| `/course/name`      |None|获取所有课程的课程名|
|get| `/course/all`       |None|获取所有的课程|
|delete| `/course/{id: int}` |None|删除课程|

## Exercise

```ts
interface Exercise {
    _id: string;
    id: int;
    type: string;
    content: string;
    choices: Array<Map<String, Object>>;
}
// 选项数据结构
interface Choices {
    cid: string;  // 选项名称
    ccontent: string;  // 选项内容
    canswer: boolean;  // 是否为正确答案
}
```

|方式 | 接口                              |参数|功能|
|------|---------------------------------|--------------------------------------------|--------|
|get| `/exercise/page`                |`{pageNum?: int, pageSize?: int}`|无视题目类别直接获取题目|
|get| `/exercise/id/{id: int}`        |None|根据 `id`(不是`_id`) 获取|
|get| `/exercise/type/{type: string}` |`{pageNum?: int, pageSize?: int}`|根据类别分页获取|
|post| `/exercise/one`                 |exercise 数据结构|插入一个题目|
|delete| `/exercise/id/{id: int}`        |None|删除一个题目|
|put| `/exercise/one`                 |exercise 数据结构|更改一个题目|


## User


```ts
interface User {
    id: int;
    adminName: string;
    nickName: string;
    password: string;
}
```

|方式 | 接口                | 参数                                                             |功能|
|------|-------------------|----------------------------------------------------------------|--------|
|post| `/user`           | `{username: string, nickName?: string, password: md5(string)}` |新增用户|
|post| `/user/login`     | `{username: string, password: string}`                         |登录| 
|put| `/user`           | `{username: string, nickName?: string, password: md5(string)}` |更改用户信息|
|get| `/user/page`      |`{pageNum?: int, pageSize?: int, search? string}`|分页查找|
|delete| `/user/{id: int}` |None|删除用户|

## Teacher


```ts
interface Teacher {
    id: int;
    teacherName: string;
    nickName: string;
    password: string;
}
```

|方式 | 接口                   | 参数                                                                |功能|
|------|----------------------|-------------------------------------------------------------------|--------|
|post| `/teacher`           | `{teacherName: string, nickName?: string, password: md5(string)}` |新增老师|
|post| `/teacher/login`     | `{teacherName: string, password: md5(string)}`                    |登录|
|put| `/teacher`           | `{teacehrName: string, nickName?: string, password: md5(string)}` |更改用户信息|
|get| `/teacher/info`      |`teacherName: string`|获取教师信息|
|get| `/teacher`           |`{pageNum?: int, pageSize?: int, search?: string}`|分页查找|
|delete| `/teacher/{id: int}` |None|删除|

## Score

```ts
interface Score {
    stuId: int;
    courseId: int;
    score: int;
}
```

|方式 | 接口                                       | 参数                                         |功能|
|------|------------------------------------------|--------------------------------------------|--------|
|get| `/score/stuId/{stuId: int}`              | `{pageNum?: int, pageSize?: int}`          |分页查找学生成绩|
|get| `/score/courseId/{courseId: int}`        | `{pageNum?: int, pageSize?: int}`          |分页查找科目成绩|
|get| `/score/stuName/{stuName: string}`       | `{pageNum?: int, pageSize?: int}`          |分页查找学生成绩|
|get| `/score/courseName/{courseName: string}` | `{pageNum?: int, pageSize?: int}`          |分页查找科目成绩|
|get| `/score/courseNo/{courseNo: string}`     | `{pageNum?: int, pageSize?: int}`          |分页查找科目成绩|
|put|`/score`| `{stuId: int, courseId: int, score: int}`  | 修改成绩|
|delete|`/score`| `{stuId: int, courseId: int, score?: int}` |删除成绩|



