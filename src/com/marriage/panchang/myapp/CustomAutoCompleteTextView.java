package com.marriage.panchang.myapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ravindra_daramwar on 15/10/14.
 */
public class CustomAutoCompleteTextView extends AutoCompleteTextView {

    public CustomAutoCompleteTextView(Context context, AttributeSet attrs) {

        super(context, attrs);

        GoonMatcher goonMatcher = new GoonMatcher(context);

        ArrayList<String> charnakshar = goonMatcher.getCharnaksharList();

        // get list of charnakshar from database and create the list of charnaksar and respective image to displya on dropdown
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        // Keys used in Hashmap
        String[] from = { "flag","txt"};

        // Ids of views in listview_layout
        int[] to = { R.id.flag,R.id.txt};

        for(int i=0;i<charnakshar.size();i++){

            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put(from[1], charnakshar.get(i));

            int imageResoure = getResources().getIdentifier(charnakshar.get(i), "drawable", context.getPackageName());
            hm.put(from[0], Integer.toString(imageResoure) );

            aList.add(hm);
        }

        SimpleAdapter adapter = new SimpleAdapter(context, aList, R.layout.autocomplete_layout, from, to);

        setAdapter(adapter);

        setOnItemClickListener(new ItemClickListenerAutoCompleteText(this));
    }
    @Override
    protected CharSequence convertSelectionToString(Object selectedItem) {
        /** Each item in the autocompetetextview suggestion list is a hashmap object */
        HashMap<String, String> hm = (HashMap<String, String>) selectedItem;
        return hm.get("txt");
    }

}
