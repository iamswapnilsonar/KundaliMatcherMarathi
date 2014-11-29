package com.marriage.panchang.myapp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by ravindra_daramwar on 18/09/14.
 */

// entry is added in Run -> Edit Configuration to make this activity as launcher activity
public class FrontPage extends FragmentActivity
                       implements ActionBar.TabListener {

    private ViewPager viewPager;
    private ActionBar actionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.frontpagetabs);

        setView();
    }

    private void setView() {
            // initialize tabs
            viewPager = (ViewPager)findViewById(R.id.frontpagetabs);
            viewPager.setAdapter(new FrontPageTabasAdapter(getSupportFragmentManager()));
            actionBar = getActionBar();

            actionBar.setHomeButtonEnabled(false);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            actionBar.setStackedBackgroundDrawable(getResources().getDrawable(R.color.Gray));

            for (String each:getResources().getStringArray(R.array.actionBarTabs)) {
                ActionBar.Tab tab = actionBar.newTab()
                        .setText(each)
                        .setTabListener(this);
                actionBar.addTab(tab);
            }

            /**
             * on swiping the viewpager make respective tab selected
             * */
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageSelected(int position) {
                    // on changing the page
                    // make respected tab selected
                    actionBar.setSelectedNavigationItem(position);
                }

                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {
                }

                @Override
                public void onPageScrollStateChanged(int arg0) {
                }
            });
    }
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respective fragment view
        viewPager.setCurrentItem(tab.getPosition());

        // hide keyboard fresh tab select
        InputMethodManager imm = (InputMethodManager)getSystemService(Service.INPUT_METHOD_SERVICE);
        View focus = getCurrentFocus();
        if(focus != null ) {
            imm.hideSoftInputFromWindow(focus.getWindowToken(), 0);
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }
}
