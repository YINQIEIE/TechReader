package com.yq.eie;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yq.eie.adapter.MyFragmentAdapter;
import com.yq.eie.base.BaseActivity;
import com.yq.eie.base.BaseFragment;
import com.yq.eie.fragments.BookFragment;
import com.yq.eie.fragments.GankFragment;
import com.yq.eie.fragments.LocalFragment;
import com.yq.eie.fragments.MovieFragment;
import com.yq.eie.statusbar.StatusBarUtil;
import com.yq.eie.utils.BitmapHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindArray(R.array.titles_main)
    String[] titles;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.bottom_bar)
    FrameLayout bottomBar;

    private MyFragmentAdapter adapter;
    private View headerView;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected void setTheme() {
        StatusBarUtil.setColorNoTranslucentForDrawerLayout(this, drawer,
                getResources().getColor(R.color.themeColor));
    }

    private void init() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.getHeaderView(0);
        ImageView imageView = headerView.findViewById(R.id.iv_avater);
        LinearLayout ll_nav_header = headerView.findViewById(R.id.ll_nav_header);
        ll_nav_header.setBackground(new BitmapDrawable(BitmapHelper.getBluredBitmap(getApplicationContext(), R.mipmap.nav_header_bac, 4)));
        imageView.setImageBitmap(BitmapHelper.getRoundCornerBitmapWithBorder(getResources(), R.mipmap.icon_avater, 14));
        initViewPager();
    }

    private void initViewPager() {
        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new LocalFragment());
        fragments.add(new GankFragment());
        fragments.add(new MovieFragment());
        fragments.add(new BookFragment());
        adapter = new MyFragmentAdapter(getSupportFragmentManager(), fragments, titles);
        viewpager.setOffscreenPageLimit(fragments.size() - 1);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(fragments.size() - 1);
        tabLayout.setupWithViewPager(viewpager);

        addBottomBar();
    }

    protected void addBottomBar() {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.add(R.id.bottom_bar, new BottomBarFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_homepage) {
            // Handle the camera action
        } else if (id == R.id.nav_scan_download) {

        } else if (id == R.id.nav_about_yueyun) {

        } else if (id == R.id.nav_github_login) {

        } else if (id == R.id.exit) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void changeFragment(int index) {
        viewpager.setCurrentItem(index);
    }

    @Override
    protected void setFinishTransaction() {

    }
}
