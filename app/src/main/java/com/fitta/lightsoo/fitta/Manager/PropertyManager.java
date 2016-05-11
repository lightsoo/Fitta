package com.fitta.lightsoo.fitta.Manager;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.fitta.lightsoo.fitta.MyApplication;

public class PropertyManager {
    SharedPreferences mPrefs;
    SharedPreferences.Editor mEditor;

    private static final String KEY_LOGIN_TYPE = "key_login_type";
    private static final String FILED_TOKEN ="filed_token";

    //스플래쉬, 로그인에서 사용
    public static final String LOGIN_TYPE_KAKAO = "login_type_kakao";
    public static final String LOGIN_TYPE_FACEBOOK = "login_type_facebook";

    //사용자의 개인치수
    public static final String USER_SEX = "sex", USER_AGE="age", USER_HEIGHT="height", USER_WEIGHT="weight", USER_TOP="top", USER_BOTTOM="bottom"; ;
    public static String USER_AVATAR;

    //사용자 이름


    private PropertyManager() {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        mEditor = mPrefs.edit();
    }
    // singleton holder pattern : thread safe, lazy class initialization, memory saving.
    public static class InstanceHolder{ private static final PropertyManager INSTANCE = new PropertyManager();}
    public static PropertyManager getInstance(){ return InstanceHolder.INSTANCE; }
    public void setUserLoginToken(String token){
        mEditor.putString(FILED_TOKEN, token);
        mEditor.commit();
    }

    public void setLoginType(String loginType){
        mEditor.putString(KEY_LOGIN_TYPE, loginType);
        mEditor.commit();
    }

    public String getUserLoginToken(){
        return mPrefs.getString(FILED_TOKEN, "");
    }

    public String getLoginType(){
        return mPrefs.getString(KEY_LOGIN_TYPE, "");
    }

    public void deleteUserLoginId(){
        mEditor.remove(FILED_TOKEN);
        mEditor.commit();
    }
    public void deleteLoginType()
    {
        mEditor.remove(KEY_LOGIN_TYPE);
        mEditor.commit();

    }


//  유저아바타, 이미지리소스를 저장하지 않으려고 그냥 사용
    public void setUserAvatar(int userAvatar){
        mEditor.putInt(USER_AVATAR, userAvatar);
        mEditor.commit();
    }

    public int getUserAvatar(){
        return mPrefs.getInt(USER_AVATAR, 0);
    }


//    유저의 정보를 저장해서 사용





    //sex, age, weight, heiht, top, bottom
    public void setUserSex(String userSex){
        mEditor.putString(USER_SEX, userSex);
        mEditor.commit();
    }

    public void setUserAge(String userAge){
        mEditor.putString(USER_AGE, userAge);
        mEditor.commit();
    }

    public void setUserWeight(String userWeight){
        mEditor.putString(USER_WEIGHT, userWeight);
        mEditor.commit();
    }

    public void setUserHeight(String userHeight){
        mEditor.putString(USER_HEIGHT, userHeight);
        mEditor.commit();
    }
    public void setUserTop(String userTop){
        mEditor.putString(USER_TOP, userTop);
        mEditor.commit();
    }

    public void setUserBottom(String userBottom){
        mEditor.putString(USER_BOTTOM, userBottom);
        mEditor.commit();
    }
    //GETTER
    public String getUserSex(){
        return mPrefs.getString(USER_SEX, "");
    }

    public String getUserAge(){
        return mPrefs.getString(USER_AGE, "");
    }

    public String getUserWeight(){
        return mPrefs.getString(USER_WEIGHT, "");
    }

    public String getUserHeight(){
        return mPrefs.getString(USER_HEIGHT, "");
    }

    public String getUserTop(){
        return mPrefs.getString(USER_TOP, "");
    }

    public String getUserBottom(){
        return mPrefs.getString(USER_BOTTOM, "");
    }

}
