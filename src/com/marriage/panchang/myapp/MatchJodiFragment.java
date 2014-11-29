package com.marriage.panchang.myapp;

import android.app.Activity;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static android.provider.BaseColumns._ID;

// TODO
// History - update tab
// Ability to update the additional details of partner
// Display Panchang
public class MatchJodiFragment extends Fragment {

    GoonMatcher goonMatcher;
    MatchHistory history;

    /**
     * Called when the activity is first created.
     */
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle)
    {

        View view = layoutInflater.inflate(R.layout.main, viewGroup, false);

        goonMatcher = new GoonMatcher(getActivity());
        history = new MatchHistory(getActivity());

        final Button mButton = (Button)view.findViewById(R.id.submit_button);

        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // hide keyboard on button click
                EditText myEditText = (EditText) getActivity().findViewById(R.id.groom_name);
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Service.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);

                MatchPatrika();
            }
        });

        // set groom or bride value depending on the history
        setBirthNamePrediction(view);

        //TextView temp = (TextView)view.findViewById(R.id.TestField);
        //temp.setMovementMethod(new ScrollingMovementMethod());
        return view;
    }

    private void setBirthNamePrediction(View view){

        String name[] = new String[2];
        name = history.getHistory();

        if(!name[1].isEmpty()) {

            CustomAutoCompleteTextView textField;
            int imageResoure = getResources().getIdentifier(name[1], "drawable", view.getContext().getPackageName());

            if(name[0].equals(DatabaseConstants.GROOM)){
                textField = (CustomAutoCompleteTextView) view.findViewById(R.id.groom_name);
            }
            else
            {
                textField = (CustomAutoCompleteTextView) view.findViewById(R.id.bride_name);
            }
            textField.setText(name[1]);
            textField.setCompoundDrawablesWithIntrinsicBounds(0, 0, imageResoure, 0);
        }

    }
    public void MatchPatrika (){

        String groom_birth_name, bride_birth_name;
        Context context = getActivity().getApplicationContext();

        EditText mEdit   = (EditText)getActivity().findViewById(R.id.groom_name);
        groom_birth_name = mEdit.getText().toString();

        if (groom_birth_name.isEmpty()) {
            NotifyError(context, "Groom Birth Name can not be empty");
            return;
        }

        mEdit   = (EditText)getActivity().findViewById(R.id.bride_name);
        bride_birth_name = mEdit.getText().toString();
        if (bride_birth_name.isEmpty()) {
            NotifyError(context, "Bride Birth Name can not be empty");
            return;
        }

        GoonMatcher.MatchResult matchResult = null;
        try {
            matchResult = goonMatcher.MatchNames(groom_birth_name, bride_birth_name);
        }catch (Throwable error){
            NotifyError(context, error.getMessage());
            return;
        }

        // update history in database
        history.setHistory(matchResult.getGroom_details().getCharnakshar(), matchResult.getBride_details().getCharnakshar());

        // display details
        GoonMatchViewer viewer = new GoonMatchViewer();
        viewer.Display(getActivity(), matchResult);
    }

    private void NotifyError(Context context, String message){
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        v.setTextColor(Color.RED);
        toast.show();
    }
}
