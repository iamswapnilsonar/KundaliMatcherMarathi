package com.marriage.panchang.myapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ravindra_daramwar on 12/10/14.
 */
public class PanchangFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle){
        return layoutInflater.inflate(R.layout.panchang, viewGroup, false);
    }
}
