package com.marriage.panchang.myapp;

import android.app.Activity;
import android.graphics.Color;
import android.widget.TextView;

/**
 * Created by ravindra_daramwar on 18/11/14.
 */
public class GoonMatchViewer {

    public void Display(Activity activity, GoonMatcher.MatchResult matchResult){
        // set groom details
        TextView mText;
        mText = (TextView)activity.findViewById(R.id.col_charnakshar_groom);
        mText.setText(matchResult.getGroom_details().getCharnakshar());

        mText = (TextView)activity.findViewById(R.id.col_charnank_groom);
        mText.setText(matchResult.getGroom_details().getCharnank());

        mText = (TextView)activity.findViewById(R.id.col_nadi_groom);
        mText.setText(matchResult.getGroom_details().getNadi());

        mText = (TextView)activity.findViewById(R.id.col_nakshatra_groom);
        mText.setText(matchResult.getGroom_details().getNakshatra());

        mText = (TextView)activity.findViewById(R.id.col_ras_groom);
        mText.setText(matchResult.getGroom_details().getRas());

        // set bride details
        mText = (TextView)activity.findViewById(R.id.col_charnakshar_bride);
        mText.setText(matchResult.getBride_details().getCharnakshar());

        mText = (TextView)activity.findViewById(R.id.col_charnank_bride);
        mText.setText(matchResult.getBride_details().getCharnank());

        mText = (TextView)activity.findViewById(R.id.col_nadi_bride);
        mText.setText(matchResult.getBride_details().getNadi());

        mText = (TextView)activity.findViewById(R.id.col_nakshatra_bride);
        mText.setText(matchResult.getBride_details().getNakshatra());

        mText = (TextView)activity.findViewById(R.id.col_ras_bride);
        mText.setText(matchResult.getBride_details().getRas());



        // set match result
        mText = (TextView)activity.findViewById(R.id.Match_Result);
        if(matchResult.getAllowMarriage())
        {
            mText.setTextColor(Color.GREEN);
            mText.setText("\"" + matchResult.getGroom_details().getName() + "\"" + " can marry with " + "\"" + matchResult.getBride_details().getName() + "\"");
        }
        else
        {
            mText.setTextColor(Color.RED);
            mText.setText("\"" + matchResult.getGroom_details().getName() + "\"" +" can not marry with " + "\""+matchResult.getBride_details().getName()+"\"");
        }

        mText = (TextView)activity.findViewById(R.id.col_goon_value);

        if(matchResult.getGoon().equals("*"))
        {
            mText.setText("36");

        } else {
            mText.setText(matchResult.getGoon());
        }

        mText = (TextView)activity.findViewById(R.id.col_dosh_value);
        mText.setText(matchResult.getDosh());

        mText = (TextView)activity.findViewById(R.id.col_dosh_meaning_value);
        mText.setText(matchResult.getDosh_description());

    }
}
