package com.example.nftartist.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.graphics.Color;

import android.os.Build;
import android.os.Bundle;

import android.view.View;

import android.widget.Toast;

import com.example.nftartist.Adapter.MainTabPagerAdapter;
import com.example.nftartist.Model.NFTMain;
import com.example.nftartist.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.util.ArrayList;


import devlight.io.library.ntb.NavigationTabBar;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MainTabPagerAdapter viewPagerAdapter;

    String permissionList[] = {Manifest.permission.CAMERA
            ,Manifest.permission.READ_EXTERNAL_STORAGE
            ,Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null){
            ActivityIntent(Login.class);
        }else{
            for (UserInfo profile : user.getProviderData()) {
                String name = profile.getDisplayName();
                String nicName = profile.getDisplayName();

                if(name != null && nicName !=null){
                    if(name.length() > 1 && nicName.length() > 1){
                        ActivityIntent(Login.class);
                    }
                }
            }
        }
        initUI();
    }

    private void initUI() {
        viewPager = (ViewPager) findViewById(R.id.frame_container);
        viewPagerAdapter = new MainTabPagerAdapter(getSupportFragmentManager());
        initPages();
        viewPager.setAdapter(viewPagerAdapter);
        initNavigationBar();
    }

    private void initPages() {
        viewPagerAdapter.addFragment(new Home());
        viewPagerAdapter.addFragment(new Search());
        viewPagerAdapter.addFragment(new Rank());
        viewPagerAdapter.addFragment(new Profile());
    }

    private void initNavigationBar(){
        final String[] colors = getResources().getStringArray(R.array.default_preview);
        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_first),
                        Color.parseColor(colors[0]))
                        .title("Home")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_second),
                        Color.parseColor(colors[1]))
                        .title("Search")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_third),
                        Color.parseColor(colors[2]))
                        .title("Rank")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_fourth),
                        Color.parseColor(colors[3]))
                        .title("Profile")
                        .build()
        );


        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 0);

        navigationTabBar.setTitleMode(NavigationTabBar.TitleMode.ACTIVE);
        navigationTabBar.setBadgeGravity(NavigationTabBar.BadgeGravity.BOTTOM);
        navigationTabBar.setBadgePosition(NavigationTabBar.BadgePosition.CENTER);
        navigationTabBar.setIsBadged(true);
        navigationTabBar.setIsTitled(true);
        navigationTabBar.setIsTinted(true);
        navigationTabBar.setIsBadgeUseTypeface(true);
        navigationTabBar.setBadgeBgColor(Color.RED);
        navigationTabBar.setBadgeTitleColor(Color.WHITE);
        navigationTabBar.setIsSwiped(true);
        navigationTabBar.setBadgeSize(10);
        navigationTabBar.setTitleSize(20);
        navigationTabBar.setIconSizeFraction(0.5f);


        //IMPORTANT: ENABLE SCROLL BEHAVIOUR IN COORDINATOR LAYOUT
        navigationTabBar.setBehaviorEnabled(true);

        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(final NavigationTabBar.Model model, final int index) {

           }


            @Override
            public void onEndTabSelected(NavigationTabBar.Model model, int index) {
                model.hideBadge();
            }
        });

            navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(final int position) {

                }

                @Override
                public void onPageScrollStateChanged(final int state) {

                }
            });



            // Click
            final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.parent);
            findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    ActivityIntent(NFTAdd.class);
                 }

            });
       }

    private void ActivityIntent(Class c){
        Intent intent = new Intent(this,c);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    }


