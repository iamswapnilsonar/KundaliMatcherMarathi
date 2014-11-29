package com.marriage.panchang.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import static android.provider.BaseColumns._ID;

import android.provider.ContactsContract;
import com.marriage.panchang.myapp.DatabaseConstants;
/**
 * Created by ravindra_daramwar on 02/09/14.
 */
public class PanchangDatabase extends SQLiteOpenHelper {

    public PanchangDatabase(Context ctx){
        super(ctx, DatabaseConstants.DATABASE_NAME, null, DatabaseConstants.DATABASE_VERSION);
    }

    // only called if database already does not exists
    @Override
    public void onCreate(SQLiteDatabase db) {
        InitPanchang(db, false);
        InitHistoryTable(db);
    }

    // called only if version is changed and when getReadableDatabase() or getWritableDatabase is called
    // this helps in releasing new version of software
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        if(2 > oldVersion){
            CleanPanchang(db);
            InitPanchang(db, false);
            InitHistoryTable(db);
        }
        /*
        Add one if you change the database version
        if(3 > oldVersion){
        }
        */
    }

    private void CleanPanchang(SQLiteDatabase db)
    {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.TABLE_NAKSHATRA);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.TABLE_RASHI);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.TABLE_CHARAN);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.TABLE_GOON);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.TABLE_DOSH);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.TABLE_NADIPADVEDH_KOSHTAK);
    }

    private void InitPanchang(SQLiteDatabase db,  boolean isUpgrade)
    {
        db.execSQL("CREATE TABLE " + DatabaseConstants.TABLE_CHARAN + " ( "
                        + DatabaseConstants.Charan.PIDADIVAS + " TEXT NOT NULL, "
                        + DatabaseConstants.Charan.CHARNAKSHARE + " TEXT NOT NULL, "
                        + DatabaseConstants.Charan.CHARNANK + " TEXT NOT NULL, "
                        + DatabaseConstants.Charan.CHARANSWAMI + " TEXT NOT NULL, "
                        + DatabaseConstants.Charan.NAVANSHRASHI + " TEXT NOT NULL, "
                        + DatabaseConstants.Charan.NAVANSHSAPTAMI + " TEXT NOT NULL, "
                        + DatabaseConstants.Charan.NAVANSHSAPTAMINO + " TEXT NOT NULL, "
                        + DatabaseConstants.Nakshatra.NAKSHATRANAME + " TEXT NOT NULL, "
                        + DatabaseConstants.Rashi.RASHINAME + " TEXT NOT NULL );"
        );

        db.execSQL("CREATE TABLE " + DatabaseConstants.TABLE_NAKSHATRA + " ( "
                        + DatabaseConstants.Nakshatra.NAKSHATRA_COLUMNS[0] + " TEXT NOT NULL, "
                        + DatabaseConstants.Nakshatra.NAKSHATRA_COLUMNS[1] + " TEXT NOT NULL, "
                        + DatabaseConstants.Nakshatra.NAKSHATRA_COLUMNS[2] + " TEXT NOT NULL, "
                        + DatabaseConstants.Nakshatra.NAKSHATRA_COLUMNS[3] + " TEXT NOT NULL, "
                        + DatabaseConstants.Nakshatra.NAKSHATRA_COLUMNS[4] + " TEXT NOT NULL, "
                        + DatabaseConstants.Nakshatra.NAKSHATRA_COLUMNS[5] + " TEXT NOT NULL, "
                        + DatabaseConstants.Nakshatra.NAKSHATRA_COLUMNS[6] + " TEXT NOT NULL, "
                        + DatabaseConstants.Nakshatra.NAKSHATRA_COLUMNS[7] + " TEXT NOT NULL, "
                        + DatabaseConstants.Nakshatra.NAKSHATRA_COLUMNS[8] + " TEXT NOT NULL, "
                        + DatabaseConstants.Nakshatra.NAKSHATRA_COLUMNS[9]+ " TEXT NOT NULL, "
                        + DatabaseConstants.Nakshatra.NAKSHATRA_COLUMNS[10] + " TEXT NOT NULL );"
        );

        db.execSQL("CREATE TABLE " + DatabaseConstants.TABLE_RASHI + " ( "
                        + DatabaseConstants.Rashi.RASHINAME + " TEXT NOT NULL, "
                        + DatabaseConstants.Rashi.SWAMI + " TEXT NOT NULL, "
                        + DatabaseConstants.Rashi.VARN + " TEXT NOT NULL, "
                        + DatabaseConstants.Rashi.VASHY + " TEXT NOT NULL, "
                        + DatabaseConstants.Rashi.TATV + " TEXT NOT NULL );"
        );

        db.execSQL("CREATE TABLE " + DatabaseConstants.TABLE_DOSH + " ( "
                        + DatabaseConstants.Dosh.DOSH_KRAMANK + " TEXT NOT NULL, "
                        + DatabaseConstants.Dosh.DOSH_DESCRIPTION + " TEXT NOT NULL ); "
        );

        db.execSQL("CREATE TABLE " + DatabaseConstants.TABLE_GOON + " ( "
                        + DatabaseConstants.Goon.VADHURAS + " TEXT NOT NULL, "
                        + DatabaseConstants.Goon.VADHUNAKSHATRA + " TEXT NOT NULL, "
                        + DatabaseConstants.Goon.VARRAS + " TEXT NOT NULL, "
                        + DatabaseConstants.Goon.VARNAKSHATRA + " TEXT NOT NULL, "
                        + DatabaseConstants.Goon.GOON + " TEXT NOT NULL, "
                        + DatabaseConstants.Goon.DOSH + " TEXT NOT NULL ); "
        );

        db.execSQL("CREATE TABLE " + DatabaseConstants.TABLE_NADIPADVEDH_KOSHTAK + " ( "
                        + DatabaseConstants.NadipadhvedhKoshtak.NAKSHATRA + " TEXT NOT NULL, "
                        + DatabaseConstants.NadipadhvedhKoshtak.CHARNANK + " TEXT NOT NULL, "
                        + DatabaseConstants.NadipadhvedhKoshtak.ROW + " TEXT NOT NULL ); "
        );

        InitPanchangValues(db);
    }

    private void InitHistoryTable(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + DatabaseConstants.TABLE_MATCH_HISTORY + " ("
                        + DatabaseConstants.MatchHistory.GENDER + " TEXT NOT NULL, "
                        + DatabaseConstants.MatchHistory.NAME + " TEXT NOT NULL, "
                        + DatabaseConstants.MatchHistory.COUNT + " INTEGER ); "
        );

        ContentValues values = new ContentValues();
        values.put(DatabaseConstants.MatchHistory.GENDER, DatabaseConstants.GROOM);
        values.put(DatabaseConstants.MatchHistory.NAME, "");
        values.put(DatabaseConstants.MatchHistory.COUNT, 0);
        db.insertOrThrow(DatabaseConstants.TABLE_MATCH_HISTORY, null, values);

        values.clear();
        values.put(DatabaseConstants.MatchHistory.GENDER, DatabaseConstants.BRIDE);
        values.put(DatabaseConstants.MatchHistory.NAME, "");
        values.put(DatabaseConstants.MatchHistory.COUNT, 0);
        db.insertOrThrow(DatabaseConstants.TABLE_MATCH_HISTORY, null, values);

    }

    private void InitPanchangValues(SQLiteDatabase db){

        ContentValues values = new ContentValues();


        for (int i = 0; i < panchang.NAKSHATRA_VALUES.length; i++) {
            values.put(DatabaseConstants.Nakshatra.NAKSHATRA_COLUMNS[0], panchang.NAKSHATRA_VALUES[i][0]);
            values.put(DatabaseConstants.Nakshatra.NAKSHATRA_COLUMNS[1], panchang.NAKSHATRA_VALUES[i][1]);
            values.put(DatabaseConstants.Nakshatra.NAKSHATRA_COLUMNS[2], panchang.NAKSHATRA_VALUES[i][2]);
            values.put(DatabaseConstants.Nakshatra.NAKSHATRA_COLUMNS[3], panchang.NAKSHATRA_VALUES[i][3]);
            values.put(DatabaseConstants.Nakshatra.NAKSHATRA_COLUMNS[4], panchang.NAKSHATRA_VALUES[i][4]);
            values.put(DatabaseConstants.Nakshatra.NAKSHATRA_COLUMNS[5], panchang.NAKSHATRA_VALUES[i][5]);
            values.put(DatabaseConstants.Nakshatra.NAKSHATRA_COLUMNS[6], panchang.NAKSHATRA_VALUES[i][6]);
            values.put(DatabaseConstants.Nakshatra.NAKSHATRA_COLUMNS[7], panchang.NAKSHATRA_VALUES[i][7]);
            values.put(DatabaseConstants.Nakshatra.NAKSHATRA_COLUMNS[8], panchang.NAKSHATRA_VALUES[i][8]);
            values.put(DatabaseConstants.Nakshatra.NAKSHATRA_COLUMNS[9], panchang.NAKSHATRA_VALUES[i][9]);
            values.put(DatabaseConstants.Nakshatra.NAKSHATRA_COLUMNS[10], panchang.NAKSHATRA_VALUES[i][10]);
            db.insertOrThrow(DatabaseConstants.TABLE_NAKSHATRA, null, values);
        }

        values.clear();

        for (int i = 0; i < panchang.RASHI_VALUES.length; i++) {
            values.put(DatabaseConstants.Rashi.RASHI_COLUMNS[0], panchang.RASHI_VALUES[i][0]);
            values.put(DatabaseConstants.Rashi.RASHI_COLUMNS[1], panchang.RASHI_VALUES[i][1]);
            values.put(DatabaseConstants.Rashi.RASHI_COLUMNS[2], panchang.RASHI_VALUES[i][2]);
            values.put(DatabaseConstants.Rashi.RASHI_COLUMNS[3], panchang.RASHI_VALUES[i][3]);
            values.put(DatabaseConstants.Rashi.RASHI_COLUMNS[4], panchang.RASHI_VALUES[i][4]);
            db.insertOrThrow(DatabaseConstants.TABLE_RASHI, null, values);
        }

        values.clear();

        for (int i = 0; i < panchang.CHARAN_VALUE.length; i++) {
            values.put(DatabaseConstants.Charan.CHARAN_COLUMN [0], panchang.CHARAN_VALUE[i][0]);
            values.put(DatabaseConstants.Charan.CHARAN_COLUMN [1], panchang.CHARAN_VALUE[i][1]);
            values.put(DatabaseConstants.Charan.CHARAN_COLUMN [2], panchang.CHARAN_VALUE[i][2]);
            values.put(DatabaseConstants.Charan.CHARAN_COLUMN [3], panchang.CHARAN_VALUE[i][3]);
            values.put(DatabaseConstants.Charan.CHARAN_COLUMN [4], panchang.CHARAN_VALUE[i][4]);
            values.put(DatabaseConstants.Charan.CHARAN_COLUMN [5], panchang.CHARAN_VALUE[i][5]);
            values.put(DatabaseConstants.Charan.CHARAN_COLUMN [6], panchang.CHARAN_VALUE[i][6]);
            values.put(DatabaseConstants.Charan.CHARAN_COLUMN [7], panchang.CHARAN_VALUE[i][7]);
            values.put(DatabaseConstants.Charan.CHARAN_COLUMN [8], panchang.CHARAN_VALUE[i][8]);
            db.insertOrThrow(DatabaseConstants.TABLE_CHARAN, null, values);
        }

        values.clear();
        for (int i = 0; i < panchang.DOSH_LIST.length; i++) {
            values.put(DatabaseConstants.Dosh.DOSH_COLUMN[0], panchang.DOSH_LIST[i][0]);
            values.put(DatabaseConstants.Dosh.DOSH_COLUMN[1], panchang.DOSH_LIST[i][1]);
            db.insertOrThrow(DatabaseConstants.TABLE_DOSH, null, values);
        }

        values.clear();

        for (int i = 0; i < panchang.GOON_VALUES.length; i++) {
            values.put(DatabaseConstants.Goon.GOON_COLUMNS[0], panchang.GOON_VALUES[i][0]);
            values.put(DatabaseConstants.Goon.GOON_COLUMNS[1], panchang.GOON_VALUES[i][1]);
            values.put(DatabaseConstants.Goon.GOON_COLUMNS[2], panchang.GOON_VALUES[i][2]);
            values.put(DatabaseConstants.Goon.GOON_COLUMNS[3], panchang.GOON_VALUES[i][3]);
            values.put(DatabaseConstants.Goon.GOON_COLUMNS[4], panchang.GOON_VALUES[i][4]);
            values.put(DatabaseConstants.Goon.GOON_COLUMNS[5], panchang.GOON_VALUES[i][5]);
            db.insertOrThrow(DatabaseConstants.TABLE_GOON, null, values);
        }

        values.clear();

        for (int i = 0; i < panchang.NADIPADVEDH_KOSHTAK_VALUES.length; i++) {
            values.put(DatabaseConstants.NadipadhvedhKoshtak.NADIPADVEDH_KOSHTAK_COLUMNS[0], panchang.NADIPADVEDH_KOSHTAK_VALUES[i][0]);
            values.put(DatabaseConstants.NadipadhvedhKoshtak.NADIPADVEDH_KOSHTAK_COLUMNS[1], panchang.NADIPADVEDH_KOSHTAK_VALUES[i][1]);
            values.put(DatabaseConstants.NadipadhvedhKoshtak.NADIPADVEDH_KOSHTAK_COLUMNS[2], panchang.NADIPADVEDH_KOSHTAK_VALUES[i][2]);
            db.insertOrThrow(DatabaseConstants.TABLE_NADIPADVEDH_KOSHTAK, null, values);
        }
        values.clear();
    }

}