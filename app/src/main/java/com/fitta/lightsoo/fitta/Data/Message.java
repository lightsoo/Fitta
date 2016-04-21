package com.fitta.lightsoo.fitta.Data;

/**
 * Created by LG on 2016-04-18.
 */
public class Message {

    public String url;
    public String msg;
    public int code;

    public Message(){}
    public Message(int code, String msg, String url) {
        this.url = url;
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode(){
        return code;
    }

    @Override
    public String toString() {
        String str = "code : " + getCode() + ", msg : " + getMsg() + ", url : " + url;
        return str;
    }
}
