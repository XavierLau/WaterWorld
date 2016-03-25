package project.comp3717.bcit.ca.waterworld;

import android.content.ContentValues;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import project.comp3717.bcit.ca.waterworld.ContinentContract.ContinentEntry;
import project.comp3717.bcit.ca.waterworld.CountryContract.CountryEntry;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{
    private DataDBHelper                dataDBHelper;
    private ArrayList<String> continentNames;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        final Resources                 res;
        //final String[]                  continents;
        //final String[]                  countries;
        final SQLiteDatabase            dbWrite;
        final SQLiteDatabase            dbRead;
        final ContentValues             values;
        final Cursor                    cursor;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(
                R.id.map);
        mapFragment.getMapAsync(this);

        dataDBHelper                    = new DataDBHelper(this);
        values                          = new ContentValues();
        dbWrite                         = dataDBHelper.getWritableDatabase();
        dbRead                          = dataDBHelper.getReadableDatabase();
        res                             = getResources();
        //countries                       = res.getStringArray(R.array.countries);         //change this when we get countries
        //continents                      = res.getStringArray(R.array.continents);        //change this when we get continents
        continentNames                  = new ArrayList<String>(7);

        /*
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

        // moving through the cursor to get all the continents then adding them to the continent names arraylist
        while (cursor.moveToNext())
        {
            continentNames.add(cursor.getString(
                    cursor.getColumnIndex(
                            ContinentEntry.COLUMN_NAME_NAME.toString()
                    )));
        }
        cursor.close();
        */
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
    }
}
