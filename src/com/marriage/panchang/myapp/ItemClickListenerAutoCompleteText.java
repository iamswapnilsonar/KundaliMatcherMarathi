package com.marriage.panchang.myapp;

import android.view.View;
import android.widget.AdapterView;

import java.util.HashMap;

/**
 * Created by ravindra_daramwar on 18/11/14.
 */
public class ItemClickListenerAutoCompleteText implements AdapterView.OnItemClickListener {

    CustomAutoCompleteTextView textView;
    ItemClickListenerAutoCompleteText(CustomAutoCompleteTextView textView){
        this.textView = textView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        setImageToAutoCompleteText(textView, parent, position);
    }

    private void setImageToAutoCompleteText(CustomAutoCompleteTextView autoTextView, AdapterView<?> parent, int position) {

        HashMap<String,String> item = (HashMap<String,String>) parent.getAdapter().getItem(position);

        String valueString = item.get("flag");
        Integer valueInt = Integer.parseInt(valueString);
        autoTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, valueInt, 0);
    }
}
