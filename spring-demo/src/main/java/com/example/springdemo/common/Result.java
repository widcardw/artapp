package com.example.springdemo.common;

import lombok.Data;

/**
 * 用于向客户端返回的类，将其封装为一个 Result
 * @param <T>
 */
@Data
public class Result<T> {
    private String code;  // 返回码
    private String msg;   // 返回消息
    private T data;       // 返回数据

    /**
     * 不带数据的成功返回
     * @return Result
     */
    public static Result success() {
        Result result = new Result();
        result.setCode("0");
        result.setMsg("success");
        return result;
    }

    /**
     * 带有数据的成功返回
     * @param data 数据内容
     * @param <T>  泛型
     * @return Result<T>
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<T>();
        result.setCode("0");
        result.setMsg("success");
        result.setData(data);
        return result;
    }

    /**
     * 返回错误
     * @param code 错误码
     * @param msg  错误消息
     * @return Result
     */
    public static Result error(String code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static Result usernameError() {
        return error("-2", "用户名不合法! 请使用数字、字符及下划线，长度不超过18个字符!");
    }

    public static Result nickNameError() {
        return error("-3", "昵称不合法! ");
    }
}
