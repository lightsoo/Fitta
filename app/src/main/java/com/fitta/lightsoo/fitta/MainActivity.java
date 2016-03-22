package com.fitta.lightsoo.fitta;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TabWidget;
import android.widget.Toast;

import com.fitta.lightsoo.fitta.Fragment.FittingFragment;
import com.fitta.lightsoo.fitta.Fragment.FittingRoomFragment;
import com.fitta.lightsoo.fitta.Fragment.SettingFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentTabHost tabHost;
    private TabWidget tabWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        //false해서 기존 title을 없애
        getSupportActionBar().setDisplayShowTitleEnabled(false);*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        init();
    }

    public void init(){
        tabHost = (FragmentTabHost)findViewById(R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        tabWidget = (TabWidget)tabHost.getTabWidget();
        /**
         * 프레그먼트 두개를 넣어서 1,2번을 하고
         * 1번 눌럿을때 드로우메뉴도 나오게한다.
         */

        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("메뉴", null),
                FittingFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("피팅", null),
                FittingFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("피팅룸", null),
                FittingRoomFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator("세팅", null),
                SettingFragment.class, null);
        tabHost.setCurrentTab(1);

        View tabwidgetgetview = (View)tabWidget.getChildTabViewAt(0);
        tabwidgetgetview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "cliked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
