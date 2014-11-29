package com.marriage.panchang.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ravindra_daramwar on 13/11/14.
 */
public class MatchHistory {

    private PanchangDatabase database;

    MatchHistory(Context ctx){
        database = new PanchangDatabase(ctx);
    }

    void setHistory(String groomName, String brideName){

        int groomCount = 0, brideCount = 0;

        groomCount = getRowCountValue(DatabaseConstants.GROOM, groomName);

        brideCount = getRowCountValue(DatabaseConstants.BRIDE, brideName);

        groomCount++;
        brideCount++;

        ContentValues values = new ContentValues();
        values.put(DatabaseConstants.MatchHistory.NAME, groomName);
        values.put(DatabaseConstants.MatchHistory.COUNT, groomCount);

        SQLiteDatabase db = database.getReadableDatabase();

        String filter = DatabaseConstants.MatchHistory.GENDER + "=" + "'" + DatabaseConstants.GROOM + "'";

        db.update(DatabaseConstants.TABLE_MATCH_HISTORY, values, filter, null);

        values.clear();
        values.put(DatabaseConstants.MatchHistory.NAME, brideName);
        values.put(DatabaseConstants.MatchHistory.COUNT, brideCount);

        filter = DatabaseConstants.MatchHistory.GENDER + "=" + "'" + DatabaseConstants.BRIDE + "'";
        db.update(DatabaseConstants.TABLE_MATCH_HISTORY, values, filter, null);
    }

    String[] getHistory(){

        int groomCount = 0, brideCount = 0;

        groomCount = getRowCountValue(DatabaseConstants.GROOM);

        brideCount = getRowCountValue(DatabaseConstants.BRIDE);

        String[] name = new String[2];
        if(groomCount >= brideCount) {
            name[0] = DatabaseConstants.GROOM;
            name[1] = getRowNameValue(DatabaseConstants.GROOM);
        }
        else {
            name[0] = DatabaseConstants.BRIDE;
            name[1] = getRowNameValue(DatabaseConstants.BRIDE);

        }
        return name;
    }

    int getRowCountValue(String gender, String name){

        int count = 0;
        SQLiteDatabase db = database.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + DatabaseConstants.MatchHistory.COUNT +
                        " FROM " + DatabaseConstants.TABLE_MATCH_HISTORY +
                        " WHERE " + DatabaseConstants.MatchHistory.GENDER + "=? AND " +
                        DatabaseConstants.MatchHistory.NAME + "=? ",
                new String[]{gender, name}
        );
        cursor.moveToFirst();
        if(cursor.getCount() != 0){
            count = cursor.getInt(0);
        }

        return count;
    }

    int getRowCountValue(String gender){

        int count = 0;
        SQLiteDatabase db = database.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + DatabaseConstants.MatchHistory.COUNT +
                        " FROM " + DatabaseConstants.TABLE_MATCH_HISTORY +
                        " WHERE " + DatabaseConstants.MatchHistory.GENDER + "=? ",
                new String[]{gender}
        );
        cursor.moveToFirst();
        if(cursor.getCount() != 0){
            count = cursor.getInt(0);
        }

        return count;
    }


    String getRowNameValue(String gender){

        String name = "";
        SQLiteDatabase db = database.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + DatabaseConstants.MatchHistory.NAME +
                        " FROM " + DatabaseConstants.TABLE_MATCH_HISTORY +
                        " WHERE " + DatabaseConstants.MatchHistory.GENDER + "=? ",
                new String[]{gender}
        );
        cursor.moveToFirst();
        if(cursor.getCount() != 0){
            name = cursor.getString(0);
        }

        return name;
    }

}
