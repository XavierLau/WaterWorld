package project.comp3717.bcit.ca.waterworld;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import project.comp3717.bcit.ca.waterworld.ContinentContract.ContinentEntry;
import project.comp3717.bcit.ca.waterworld.CountryContract.CountryEntry;
/**
 * Created by Xavier Lau on 3/11/2016.
 */
public class DataDBHelper
        extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Data.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String[] SQL_CREATE_ENTRIES = {"CREATE TABLE " + CountryEntry.TABLE_NAME + " (" +
            CountryEntry._ID + " INTEGER PRIMARY KEY," +
            CountryEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
            CountryEntry.COLUMN_NAME_RATING + INTEGER + COMMA_SEP +
            CountryEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE +
            ")",
            "CREATE TABLE " + ContinentEntry.TABLE_NAME + " (" +
                    ContinentEntry._ID + " INTEGER PRIMARY KEY," +
                    ContinentEntry.COLUMN_NAME_NAME + TEXT_TYPE +
                    ")"
    };

    private static final String[] SQL_DELETE_ENTRIES = {"DROP TABLE IF EXISTS " + CountryEntry.TABLE_NAME,
            "DROP TABLE IF EXISTS " + ContinentEntry.TABLE_NAME
    };


    public DataDBHelper(final Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(final SQLiteDatabase db)
    {
        for (int i = 0; i < SQL_CREATE_ENTRIES.length; i++)
        {
            db.execSQL(SQL_CREATE_ENTRIES[i]);
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        for (int i = 0; i < SQL_DELETE_ENTRIES.length; i++)
        {
            db.execSQL(SQL_DELETE_ENTRIES[i]);
            Log.d("drop", "dropping tables" + i);
        }
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onUpgrade(db, oldVersion, newVersion);
    }
}
