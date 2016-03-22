package com.fitta.lightsoo.fitta;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.fitta.lightsoo.fitta.Fragment.FittingFragment;
import com.fitta.lightsoo.fitta.Fragment.FittingRoomFragment;
import com.fitta.lightsoo.fitta.Fragment.SettingFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentTabHost tabHost;
    //드로우 메뉴를 하기위한 clicked리스너였는데 일단은 제외한다.
//    private TabWidget tabWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        //false해서 기존 title을 없애
        getSupportActionBar().setDisplayShowTitleEnabled(false);*/
        init();
    }

    public void init(){
        tabHost = (FragmentTabHost)findViewById(R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
//        tabWidget = (TabWidget)tabHost.getTabWidget();


        View home = LayoutInflater.from(MainActivity.this).inflate(R.layout.tab_home_btn, null);
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(home), FittingFragment.class, null);

        View chat = LayoutInflater.from(MainActivity.this).inflate(R.layout.tab_chat_btn, null);
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(chat), FittingFragment.class, null);

        View myinfo = LayoutInflater.from(MainActivity.this).inflate(R.layout.tab_myinfo_btn, null);
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator(myinfo), FittingRoomFragment.class, null);

        View write = LayoutInflater.from(MainActivity.this).inflate(R.layout.tab_write_btn, null);
        tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator(write), SettingFragment.class, null);
        tabHost.setCurrentTab(0);

        //클릭리스너야
       /* View tabwidgetgetview = (View)tabWidget.getChildTabViewAt(0);
        tabwidgetgetview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "cliked", Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}
