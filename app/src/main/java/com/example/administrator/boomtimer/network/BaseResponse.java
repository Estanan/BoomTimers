package com.example.administrator.boomtimer.network;

/**
 * Created by Administrator on 2017/1/16.
 * 网络请求返回基类
 */
public class BaseResponse {
    //服务器返回消息
    protected String msg;
    //服务器返回执行结果
    protected int result;
    protected String dataid;
 
    public int getResult() {
        return result;
    }
 
    public void setResult(int result) {
        this.result = result;
    }
 
    public String getDataid() {
        return dataid;
    }
 
    public void setDataid(String dataid) {
        this.dataid = dataid;
    }
 
    public String getMsg() {
        return msg;
    }
 
    public void setMsg(String msg) {
        this.msg = msg;
    }
}