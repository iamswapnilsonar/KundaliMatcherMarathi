package com.marriage.panchang.myapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.provider.ContactsContract;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ravindra_daramwar on 08/09/14.
 */
public class GoonMatcher {

    private PanchangDatabase database;

    GoonMatcher(Context ctx){
        database = new PanchangDatabase(ctx);
    }

    public  MatchResult MatchNames(String groomName, String brideName) throws Throwable{

        MatchResult matchResult = new MatchResult();

        // get groom details
        PersonDetails groomDetails = GetDetailsFromBirtName(groomName);

        // get groom details
        PersonDetails brideDetails = GetDetailsFromBirtName(brideName);

        matchResult.setGroom_details(groomDetails);
        matchResult.setBride_details(brideDetails);

        // get goon
        SQLiteDatabase db = database.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + DatabaseConstants.Goon.GOON + " , " + DatabaseConstants.Goon.DOSH +
                        " FROM " + DatabaseConstants.TABLE_GOON +
                        " WHERE " + DatabaseConstants.Goon.VARRAS + "=? AND " +
                        DatabaseConstants.Goon.VARNAKSHATRA + "=? AND " +
                        DatabaseConstants.Goon.VADHURAS + "=? AND " +
                        DatabaseConstants.Goon.VADHUNAKSHATRA + "=? ",
                new String[]{brideDetails.getRas(), brideDetails.getNakshatra(), groomDetails.getRas(), groomDetails.getNakshatra()}
        );

        cursor.moveToFirst();
        Integer numberofrows = cursor.getCount();

        if (numberofrows == 1) {

            matchResult.setGoon(cursor.getString(0));
            matchResult.setDosh(cursor.getString(1));

            String doshDescription = "";
            for(int i = 0; i < matchResult.getDosh().length(); i++) {

                String dosh = matchResult.getDosh().substring(i, i + 1);
                cursor = db.rawQuery("SELECT " + DatabaseConstants.Dosh.DOSH_DESCRIPTION +
                                " From " + DatabaseConstants.TABLE_DOSH +
                                " WHERE " + DatabaseConstants.Dosh.DOSH_KRAMANK + "=? ",
                        new String[]{dosh}
                );
                cursor.moveToFirst();

                if (cursor.getCount() == 1) {
                    if(i > 0) {
                        doshDescription = doshDescription + ". " + dosh + "-" + cursor.getString(0);
                    } else {
                        doshDescription = dosh + "-" + cursor.getString(0);
                    }
                }
            }
            matchResult.setDosh_description(doshDescription);
        } else {
            throw new Throwable("Unable to match details. Please recheck the birth name.");
        }

        SetFinalResult(matchResult);
        return matchResult;
    }
    private void SetFinalResult(GoonMatcher.MatchResult matchResult){

        boolean marry = true;
        if(matchResult.getDosh().contains("x")){
            marry = false;
        }
        else if (matchResult.getGoon().equals("*")) {

            // Ekcharan dosh
            if (matchResult.getDosh().contains("3")) {
                marry = false;
            }
        }
        else if (matchResult.getDosh().equals("6")){
            marry = false;
        }
        else if (matchResult.getDosh().equals("7"))
        {
            if(Integer.parseInt(matchResult.getGoon()) > 18) {
                marry = true;
            }
            else {
                marry = false;
            }
        }

        // eknadi dosh
        else if (matchResult.getDosh().contains("8") &&
                !SpeciaMatch(matchResult.getGroom_details().getNakshatra(),
                             matchResult.getGroom_details().getCharnank(),
                             matchResult.getBride_details().getNakshatra(),
                             matchResult.getBride_details().getCharnank()))
        {

            marry = false;
        }

        matchResult.setAllowMarriage(marry);
    }

    private boolean SpeciaMatch(String groomNakshatra, String groomCharnank, String brideNakshatra, String brideCharnank)
    {
        String groomRow = "";
        String brideRow = "";

        SQLiteDatabase db = database.getReadableDatabase();

        Cursor cursor = db.rawQuery("select " + DatabaseConstants.NadipadhvedhKoshtak.ROW +
                " from " + DatabaseConstants.TABLE_NADIPADVEDH_KOSHTAK +
                " where " + DatabaseConstants.NadipadhvedhKoshtak.NAKSHATRA + "=? AND " +
                            DatabaseConstants.NadipadhvedhKoshtak.CHARNANK + "=? ",
                new String[] {groomNakshatra, groomCharnank});

        cursor.moveToFirst();
        int numberofrows = cursor.getCount();
        if(numberofrows == 1){
            groomRow = cursor.getString(0);
        }

        cursor = db.rawQuery("select " + DatabaseConstants.NadipadhvedhKoshtak.ROW +
                        " from " + DatabaseConstants.TABLE_NADIPADVEDH_KOSHTAK +
                        " where " + DatabaseConstants.NadipadhvedhKoshtak.NAKSHATRA + "=? AND " +
                        DatabaseConstants.NadipadhvedhKoshtak.CHARNANK + "=? ",
                new String[] {brideNakshatra, brideCharnank});

        cursor.moveToFirst();
        numberofrows = cursor.getCount();
        if(numberofrows == 1){
            brideRow = cursor.getString(0);
        }

        if(groomRow.equals(brideRow)){
            return false;
        }
        else {
            return true;
        }
    }
    private PersonDetails GetDetailsFromBirtName(String birthName)throws Throwable{

        PersonDetails personDetails = new PersonDetails();
        personDetails.setName(birthName);

        String first_char = GetFirstCharFromString(birthName);
        personDetails.setCharnakshar(first_char);

        SQLiteDatabase db = database.getReadableDatabase();

        Cursor cursor = db.rawQuery("select " + DatabaseConstants.Nakshatra.NAKSHATRANAME + " , " + DatabaseConstants.Rashi.RASHINAME +
                " , " + DatabaseConstants.Charan.CHARNANK +
                " from " + DatabaseConstants.TABLE_CHARAN + " where " + DatabaseConstants.Charan.CHARNAKSHARE + " = ?", new String[] {first_char});

        cursor.moveToFirst();
        int numberofrows = cursor.getCount();

        String groom_nakshatra = "", groom_ras = "";
        if (numberofrows == 1) {
            personDetails.setNakshatra(cursor.getString(0));
            personDetails.setRas(cursor.getString(1));
            personDetails.setCharnank(cursor.getString(2));
        }else if (numberofrows > 0){
            throw new Throwable("Multiple Charnakshar entries are found for \"" + first_char + "\" Need to correct the birth name");

        } else{
            throw new Throwable("Charnakshar \"" + first_char + "\"" + " of " + "\""+ birthName + "\"" + " not found");
        }

        cursor = db.rawQuery("SELECT " + DatabaseConstants.Nakshatra.NADI + " FROM " + DatabaseConstants.TABLE_NAKSHATRA
                + " WHERE " + DatabaseConstants.Nakshatra.NAKSHATRANAME + " = ?", new String[]{personDetails.getNakshatra()}
        );
        cursor.moveToFirst();
        numberofrows = cursor.getCount();
        if(numberofrows == 1){
            personDetails.setNadi(cursor.getString(0));
        } else if (numberofrows > 0){
            throw new Throwable("Conflict found in Multiple Nakshatra details for " + personDetails.getNakshatra());
        } else {
            throw new Throwable("Nakshatra details of " + personDetails.getNakshatra() + " are not found");
        }
        return personDetails;
    }

    private String GetFirstCharFromString(String name){

        List<Character> vowels = Arrays.asList('a', 'e', 'i', 'o', 'u');

        if(name.length() == 0){
            return new String();
        }

        int i;
        boolean found_first_vowel = false;
        boolean found_first_special_char = false;

        for (i = 0; i < name.length(); i++){
            if (!vowels.contains(name.charAt(i)) && !found_first_vowel)
            {
                continue;
            }
            else if (vowels.contains(name.charAt(i)))
            {
                found_first_vowel = true;
                continue;
            }
            else if (!vowels.contains(name.charAt(i)) && found_first_vowel && i > 1)
            {
                // special handling
                if (name.charAt(0) == 'g' && name.charAt(1) == 'a' && (name.charAt(2) == 'n' || name.charAt(2) == 'm'))
                {
                    found_first_special_char = true;
                    i++;
                }
                else if(name.charAt(0) == 'd' && name.charAt(1) == 'o' && name.charAt(2) == '_')
                {
                    found_first_special_char = true;
                    i++;
                }
                break;
            }
        }
        return name.substring(0, i);
    }

    ArrayList<String> getCharnaksharList(){

        ArrayList<String> charnakshar = new ArrayList<String>();

        SQLiteDatabase db = database.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + DatabaseConstants.Charan.CHARNAKSHARE + " From " + DatabaseConstants.TABLE_CHARAN, new String[]{});

        cursor.moveToFirst();

        if (cursor.getCount() > 0){

            do {
                charnakshar.add(cursor.getString(0));

            } while(cursor.moveToNext());
        }
        return charnakshar;
    }

    class PersonDetails{
        private String name;
        private String ras;
        private String nakshatra;
        private String nadi;
        private String charnakshar;
        private String charnank;

        PersonDetails(){
            name = "";
            ras = "";
            nadi = "";
            nakshatra = "";
            charnakshar = "";
            charnank = "";
        }
        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return name;
        }
        public void setRas(String ras){
            this.ras = ras;
        }
        public void setNakshatra(String nakshatra){
            this.nakshatra = nakshatra;
        }
        public void setNadi(String nadi){
            this.nadi = nadi;
        }
        public String getRas(){
            return ras;
        }
        public String getNakshatra(){
            return nakshatra;
        }
        public String getNadi(){
            return nadi;
        }
        public void setCharnakshar(String charnakshar){this.charnakshar=charnakshar;}
        public String getCharnakshar(){return charnakshar;}
        public void setCharnank(String charnank){this.charnank = charnank;}
        public String getCharnank(){return charnank;}
    };

    class MatchResult{
        private String goon;
        private String dosh;
        private String dosh_description;
        private boolean marry;

        PersonDetails groom_details;
        PersonDetails bride_details;

        MatchResult(){
            goon = "";
            dosh = "";
            dosh_description = "";
            marry = true;
        }
        public void setGoon(String goon){
            this.goon = goon;
        }
        public String getGoon(){
            return goon;
        }
        public void setDosh(String dosh){

            for (int i = 0; i < dosh.length(); i++) {
                if(i > 0){
                    this.dosh = this.dosh.concat(", ");
                }
                this.dosh = this.dosh.concat(Character.toString(dosh.charAt(i)));
            }
        }
        public String getDosh(){
            return dosh;
        }
        public void setDosh_description(String dosh_description){this.dosh_description = dosh_description;}
        public String getDosh_description(){return dosh_description;}
        public void setGroom_details(PersonDetails details){this.groom_details = details;}
        public PersonDetails getGroom_details(){return groom_details;}
        public void setBride_details(PersonDetails details){this.bride_details = details;}
        public PersonDetails getBride_details(){return bride_details;}
        public void setAllowMarriage(boolean marry){this.marry = marry;}
        public boolean getAllowMarriage(){return marry;}
    };
}
