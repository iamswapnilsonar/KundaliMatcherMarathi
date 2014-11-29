package com.marriage.panchang.myapp;

import android.provider.BaseColumns;

/**
 * Created by ravindra_daramwar on 02/09/14.
 */
public interface DatabaseConstants extends BaseColumns {

    public static final String GROOM = "groom";
    public static final String BRIDE = "bride";

    public static final String DATABASE_NAME = "komatipanchang.db";

    // NOTE: need to add entry in PanchangDatabase::onUpgrade() for new version number for upgrade changes
    public static final int DATABASE_VERSION = 2;

    // table Nakshatra
    public static final String TABLE_NAKSHATRA = "Nakshatra";

    public interface Nakshatra {
        // columns in Nakshatra
        public static final String NAKSHATRANAME = "nakshatraname";
        public static final String NADI = "nadi";
        public static final String YONI = "yoni";
        public static final String GAN = "gan";
        public static final String ARADHYAVRUKSH = "aradhyavruksh";
        public static final String DAN = "dan";
        public static final String DEVATA = "devata";
        public static final String MUKH = "mukh";
        public static final String DRUSHTI = "drushti";
        public static final String TATVA = "tatva";
        public static final String SADNYA = "sadnya";

        public static final String NAKSHATRA_COLUMNS[] = {
            NAKSHATRANAME,
            NADI,
            YONI,
            GAN,
            ARADHYAVRUKSH,
            DAN,
            DEVATA,
            MUKH,
            DRUSHTI,
            TATVA,
            SADNYA
        };
    };
    public static String[] FROM_NAKSHATRA = { Nakshatra.NAKSHATRANAME, Nakshatra.NADI, Nakshatra.YONI, Nakshatra.GAN, Nakshatra.GAN, Nakshatra.ARADHYAVRUKSH, Nakshatra.DAN, Nakshatra.DEVATA, Nakshatra.MUKH, Nakshatra.DRUSHTI, Nakshatra.TATVA, Nakshatra.SADNYA, };

    // table Rashi
    public static final String TABLE_RASHI = "Rashi";

    public interface Rashi {
        // columns in Rashi
        public static final String RASHINAME = "rashiname";
        public static final String SWAMI = "swami";
        public static final String VARN = "varn";
        public static final String VASHY = "vashy";
        public static final String TATV = "tatv";

        public static final String RASHI_COLUMNS[] = {
                RASHINAME,
                SWAMI,
                VARN,
                VASHY,
                TATV
        };
    };

    // table Charan
    public static final String TABLE_CHARAN = "Charan";

    public interface Charan {
        // columns in the table
        public static final String PIDADIVAS = "pidadivas";
        public static final String CHARNAKSHARE = "charnakshare";
        public static final String CHARNANK = "charnank";
        public static final String CHARANSWAMI = "charnswami";
        public static final String NAVANSHRASHI = "navanshrashi";
        public static final String NAVANSHSAPTAMI = "navanshsaptami";
        public static final String NAVANSHSAPTAMINO = "navanshsaptamino";

        public static final String CHARAN_COLUMN[] = {
                PIDADIVAS,
                CHARNAKSHARE,
                CHARNANK,
                CHARANSWAMI,
                NAVANSHRASHI,
                NAVANSHSAPTAMI,
                NAVANSHSAPTAMINO,
                Nakshatra.NAKSHATRANAME,
                Rashi.RASHINAME
        };
    }

    // table dosh
    public static final String TABLE_DOSH = "Dosh";

    public interface Dosh {
        // columns in dosh
        public static final String DOSH_KRAMANK = "Kramank";
        public static final String DOSH_DESCRIPTION = "Description";

        public static final String DOSH_COLUMN[] = {
               DOSH_KRAMANK,
               DOSH_DESCRIPTION
        };
    }
    // table goon
    public static final String TABLE_GOON = "Goon";

    public interface Goon {
        // columns in goon
        public static final String VADHURAS = "vadhuras";
        public static final String VADHUNAKSHATRA = "vadhunakshatra";
        public static final String VARRAS = "varras";
        public static final String VARNAKSHATRA = "varnakshatra";
        public static final String GOON = "goon";
        public static final String DOSH = "dosh";

        public static final String GOON_COLUMNS[] = {
                VADHURAS,
                VADHUNAKSHATRA,
                VARRAS,
                VARNAKSHATRA,
                GOON,
                DOSH
        } ;
    };

    // table Nadipadvedh Koshtak
    public static final String TABLE_NADIPADVEDH_KOSHTAK = "NadipadhvedhKoshtak";

    public interface NadipadhvedhKoshtak {
        // columns
        public static final String NAKSHATRA = "nakshatra";
        public static final String CHARNANK = "charnank";
        public static final String ROW = "row";
        public static final String NADIPADVEDH_KOSHTAK_COLUMNS[] = {
                NAKSHATRA,
                CHARNANK,
                ROW
        };
    };

    // table MatchHistory
    public static final String TABLE_MATCH_HISTORY = "MatchHistory";

    public interface MatchHistory {
        // columns
        public static final String GENDER = "gender";
        public static final String NAME = "name";
        public static final String COUNT = "count";

        public static final String MATCH_HISTORY_COLUMNS[] = {
                GENDER,
                NAME,
                COUNT
        };
    }
}
