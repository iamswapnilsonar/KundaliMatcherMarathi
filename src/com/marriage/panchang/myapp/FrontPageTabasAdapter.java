package com.marriage.panchang.myapp;

//import android.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;

/**
 * Created by ravindra_daramwar on 11/10/14.
 *
 * Request is sent by ViewPager whenever tab listener requests ViewPager about change of tab by user
 */
public class FrontPageTabasAdapter extends FragmentPagerAdapter {

    public FrontPageTabasAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }
    @Override
    public Fragment getItem(int index){
        switch (index){
            case 0:
                return new MatchJodiFragment();
            case 1:
                return new PersonDetailsFragment();
            case 2:
                return new PanchangFragment();
            case 3:
                return new AboutFragment();
        }
        return null;
    }

    @Override
    public int getCount(){
        return 4;
    }
}
