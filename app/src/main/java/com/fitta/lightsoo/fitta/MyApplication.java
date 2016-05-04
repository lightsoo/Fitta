package com.fitta.lightsoo.fitta;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import com.facebook.FacebookSdk;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;

import java.lang.reflect.Field;

public class MyApplication extends Application{
    public static Context mContext;
    private Activity currentTopActivity;
    @Override
    public void onCreate() {
        super.onCreate();

        /*//갤러리 권한 체크 (사용권한이 없는 경우 -1)
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            //권한이 없을경우





        }*/


        //폰트 수정하기기
        //알아보니 안드로이드에서 지원해주는 것만 전체 application에 적용해서폰트처리할수있다!
//        setDefaultFont(this, "MONOSPACE", "Roboto-Regular.ttf");
        setDefaultFont(this, "MONOSPACE", "NotoSansKR-Regular.otf");

//        setDefaultFont(this, "MONOSPACE", "NotoSansMonoCJKkr-Regular.otf");
//        setDefaultFont(this, "MONOSPACE", "NotoSansCJKkr-Regular.otf");

        mContext = this;
        KakaoSDK.init(new KakaoAdapter() {
            @Override
            public IApplicationConfig getApplicationConfig() {
                return new IApplicationConfig() {
                    @Override
                    public Activity getTopActivity() {
                        return currentTopActivity;
                    }

                    @Override
                    public Context getApplicationContext() {
                        return MyApplication.this;
                    }
                };
            }
        });

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                currentTopActivity = activity;
            }

            @Override
            public void onActivityPaused(Activity activity) {
                if (currentTopActivity != null && currentTopActivity == activity) {
                    currentTopActivity = null;
                }
            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });

        //facebook sdk를 사용하기위해서 한번 로그인하면 이걸 쉐프에 저장한다. 그래서 다음번에 로그인할때 이걸쓰기 위한 처리작업
        //페이스북 기능을 쓰기전에 호출되어야한다.
        //facebook SDK에 application context값을 설저해주고, 쉐프에 저장된 로그인정보(accessToken정보)가 있으면 불러와서
        //AccessToken에 해당 값을 설정해 주는 역할을 한다.
        FacebookSdk.sdkInitialize(getApplicationContext());
    }
    public static Context getContext(){
        return mContext;
    }


    public static void setDefaultFont(Context ctx, String staticTypefaceFieldName, String fontAssetName) {
        final Typeface customFontTypeface = Typeface.createFromAsset(ctx.getAssets(), fontAssetName);
        replaceFont(staticTypefaceFieldName, customFontTypeface);
    }
    protected static void replaceFont(String staticTypefaceFieldName, final Typeface customFontTypeface) {
        try {
            final Field StaticField = Typeface.class
                    .getDeclaredField(staticTypefaceFieldName);
            StaticField.setAccessible(true);
            StaticField.set(null, customFontTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
