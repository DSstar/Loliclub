package com.loliclub.eater.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.loliclub.eater.R;
import com.loliclub.eater.view.MenuFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends ActionBarActivity {

    private Toolbar toolbar;
    //	private PagerTabStrip pagerTabStrip;
    private ViewPager viewPager;

    private List<String> titleList;
    private List<Fragment> viewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initParams();
        initUI();
        initEvent();
    }

    /**
     * 初始化参数
     */
    private void initParams() {
        titleList = new ArrayList<String>();
        viewList = new ArrayList<Fragment>();

        titleList.add("tab1");
        titleList.add("tab2");
        titleList.add("tab3");
        titleList.add("tab4");

        List<String> menuList = new ArrayList<String>();
        menuList.add("menu1");
        menuList.add("menu2");
        menuList.add("menu3");
        menuList.add("menu4");
        menuList.add("menu5");

        viewList.add(MenuFragment.newInstance(menuList));
        viewList.add(MenuFragment.newInstance(menuList));
        viewList.add(MenuFragment.newInstance(menuList));
        viewList.add(MenuFragment.newInstance(menuList));

    }

    /**
     * 初始化UI
     */
    private void initUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.menuActivity_viewpager);
//		pagerTabStrip = (PagerTabStrip) findViewById(R.id.mainActivity_tabs);

        // 初始化Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 初始化ViewPager
        viewPager.setAdapter(new MenuFragmentPagerAdapter(getSupportFragmentManager(), viewList, titleList));
    }

    /**
     * 初始化事件处理
     */
    private void initEvent() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
