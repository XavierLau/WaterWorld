package project.comp3717.bcit.ca.waterworld;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

import project.comp3717.bcit.ca.waterworld.ContinentContract.ContinentEntry;
import project.comp3717.bcit.ca.waterworld.CountryContract.CountryEntry;

/**
 * Creates the database, populates it with data, while the splash screen is active.
 */
public class MainActivity extends AppCompatActivity
{
    private DataDBHelper                dataDBHelper;
    private ArrayList<String>           continentNames;

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        final Resources                 res;
        final String[]                  continents;
        final String[]                  countries;
        final SQLiteDatabase            dbWrite;
        final SQLiteDatabase            dbRead;
        final ContentValues             values;
        final Cursor 					cursor;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataDBHelper                    = new DataDBHelper(this);
        values                          = new ContentValues();
        dbWrite                         = dataDBHelper.getWritableDatabase();
        dbRead                          = dataDBHelper.getReadableDatabase();
        res                             = getResources();
        countries                       = res.getStringArray(R.array.countries);
        continents                      = res.getStringArray(R.array.continents);
        continentNames                  = new ArrayList<String>(7);

        // inserting data into country table
        for (final String country : countries)
        {
            long newRowId;

            values.put(CountryEntry.COLUMN_NAME_NAME, country);
            values.putNull(CountryEntry.COLUMN_NAME_RATING);
            values.putNull(CountryEntry.COLUMN_NAME_DESCRIPTION);
            newRowId = dbWrite.insert(CountryEntry.TABLE_NAME,
                    null,
                    values);
        }

        values.clear();

        // inserting data into continent table
        for (final String continent : continents)
        {
            long newRowId;

            values.put(ContinentEntry.COLUMN_NAME_NAME, continent);
            newRowId = dbWrite.insert(ContinentEntry.TABLE_NAME,
                    null,
                    values);
        }

        // getting names of all the continents and putting them into the cursor
        cursor = dbRead.query(ContinentEntry.TABLE_NAME,
                new String[]{ContinentEntry.COLUMN_NAME_NAME},
                null,
                null,
                null,
                null,
                null);

        // moving through the cursor to get all the continents then adding them to the continentnames arraylist
        while (cursor.moveToNext()) {
            continentNames.add(cursor.getString(
                    cursor.getColumnIndex(
                            ContinentEntry.COLUMN_NAME_NAME.toString()
                    )));
        }
        cursor.close();
    }

    public void displayWorldActivity(final View view)
    {
        Intent intent = new Intent(this, WorldActivity.class);
        intent.putStringArrayListExtra("continents", continentNames);
        startActivity(intent);
    }
}

