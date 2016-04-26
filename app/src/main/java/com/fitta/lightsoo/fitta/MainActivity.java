package com.fitta.lightsoo.fitta;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.fitta.lightsoo.fitta.Fragment.FittingFragment;
import com.fitta.lightsoo.fitta.Fragment.FittingRoomFragment;
import com.fitta.lightsoo.fitta.Fragment.SettingFragment;
import com.fitta.lightsoo.fitta.Fragment.StoreFragment;
import com.fitta.lightsoo.fitta.Handler.BackPressCloseHandler;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static FragmentTabHost tabHost;
    //드로우 메뉴를 하기위한 clicked리스너였는데 일단은 제외한다.
//    private TabWidget tabWidget;

    private static final int TEST= 10;

    //싱글톤 패턴, 프로그램 종료시점까지 하나의 인스턴스만을 생성해서 관리한다.
    public static class InstanceHolder{
        public static final MainActivity INSTANCE = new MainActivity();
    }
    public static MainActivity getInstance(){return InstanceHolder.INSTANCE;}

    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //뒤로가기 핸들러
        backPressCloseHandler = new BackPressCloseHandler(this);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        //false해서 기존 title을 없애
        getSupportActionBar().setDisplayShowTitleEnabled(false);*/

        Log.d(TAG, "메인액티비티 호출!!");
        init();
    }

    public void init(){
        tabHost = (FragmentTabHost)findViewById(R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        //위젯의 크기를 바꾸려면 xml가서 tabWidget크기를 바꿔라
        View fitting = LayoutInflater.from(MainActivity.this).inflate(R.layout.tab_fitting_btn, null);
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(fitting), FittingFragment.class, null);

        View fittingroom = LayoutInflater.from(MainActivity.this).inflate(R.layout.tab_fittingroom_btn, null);
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(fittingroom), FittingRoomFragment.class, null);

        View store = LayoutInflater.from(MainActivity.this).inflate(R.layout.tab_store_btn, null);
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator(store), StoreFragment.class, null);

        View setting = LayoutInflater.from(MainActivity.this).inflate(R.layout.tab_setting_btn, null);
        tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator(setting), SettingFragment.class, null);
        tabHost.setCurrentTab(0);
    }

    public static FragmentTabHost getCurrentTabHost(){
        return tabHost;
    }
    public void switchTab(int tab){
        tabHost.setCurrentTab(tab);
    }



    @Override
    public void onBackPressed() {backPressCloseHandler.onBackPressed();  }
}
