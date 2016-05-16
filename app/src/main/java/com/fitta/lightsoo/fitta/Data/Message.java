package com.fitta.lightsoo.fitta.Data;

/**
 * Created by LG on 2016-04-18.
 */
public class Message {

    public String imageUrl;
    public String msg;
    public int code;
    public String clothesFeedback;


    public Message(){}
    public Message(int code, String msg, String imageUrl, String clothesFeedback) {
        this.imageUrl = imageUrl;
        this.code = code;
        this.msg = msg;
        this.clothesFeedback= clothesFeedback;
    }

    public String getMsg() {
        return imageUrl;
    }

    public void setMsg(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getCode(){
        return code;
    }

    @Override
    public String toString() {
        String str = "code : " + getCode() + ", msg : " + getMsg() + ", imageUrl : " + imageUrl;
        return str;
    }
}
