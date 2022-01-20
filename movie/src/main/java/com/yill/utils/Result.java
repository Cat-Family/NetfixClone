package com.yill.utils;

import com.yill.constant.ResultCodeConstant;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Map;

@Data
public class Result implements Serializable {

    /**
     * 状态码
     */
    private int code;
    /**
     * 接口信息
     */
    private String msg;
    /**
     * 接口返回数据
     */
    private Object data;

    /**
     * 构造私有，不能new
     */
    private Result(){};

    public static Result succ(Object data){
       return succ(ResultCodeConstant.SUCCESS,"操作成功",data);
    }

    public static Result succ(int code,String msg,Object data){
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static Result fail(String msg){
        return fail(ResultCodeConstant.ERROR,msg,null);
    }

    public static Result fail(String msg,Object data){
        return fail(ResultCodeConstant.ERROR,msg,data);
    }
    public static Result fail(int code,String msg,Object data){
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    /**
     * 链式
     * @return
     */
    public Result code(int code) {
        this.setCode(code);
        return this;
    }
    public Result msg(String msg) {
        this.setMsg(msg);
        return this;
    }
    public Result data(Object data) {
        this.setData(data);
        return this;
    }
}
