package project.comp3717.bcit.ca.waterworld;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import project.comp3717.bcit.ca.waterworld.ContinentContract.ContinentEntry;
import project.comp3717.bcit.ca.waterworld.CountryContract.CountryEntry;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Xavier Lau, Chiseong Oh and Connor Phalen on 2016-03-26.
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{
    private DataDBHelper       dataDBHelper;
    private GoogleMap          mMap;
    private final OkHttpClient client = new OkHttpClient();
    private String continent;
    private String country;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        final Resources                 res;
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

        // Execute database retrieval. Parameter is continent you want to look in.
        new GetCountryASync(dbWrite).execute("CountryListNA");


        // ---- Start Old Code ----

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

        // ---- End Old Code ----
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

    public class GetCountryASync extends AsyncTask<String, Void, String>
    {
        private Exception exception;
        private SQLiteDatabase database;

        GetCountryASync(final SQLiteDatabase db)
        {
            database = db;
        }

        protected String doInBackground(String... arg0)
        {
            try
            {
                // Build the query string.
                QueryBuilder query = new QueryBuilder();

                Request request = new Request.Builder()
                        .url(query.getBaseURL() + arg0[0] + query.getAPIKeyURLExtension())
                        .build();

                // Executes REST call, get JSON data.
                Response response = client.newCall(request).execute();

                return response.body().string();
            }
            catch(Exception ex)
            {
                Log.d("GetCountry Error", ex.getMessage() + "\nCountry Could not be retrieved.");
                ex.printStackTrace();
                return (null);
            }
        }

        protected void onPostExecute(final String data)
        {
            if(data == null)
            {
                return;
            }

            final JSONArray array;
            Log.d("All JSON Data", data);

            try
            {
                // Assign retrieved data into a JSON Array
                array = new JSONArray(data);

                String countryID;
                String countryName;
                String    countryRating;
                String countryDesc;
                JSONObject object;

                // For all countries returned...
                for(int i = 0; i < array.length() ; i++)
                {
                    object = array.getJSONObject(i);

                    // If country name is equal to desired country name
                    if(object.getString("_id").equals("CANADA"))  /* ----- Need to not hardcode. Change when we can click. ---- */
                    {
                        countryName   = object.getString("countryname");
                        countryDesc   = object.getString("countrydesc");
                        countryRating = object.getString("countryrating");

                        Bundle countryBundle = new Bundle();

                        countryBundle.putString("COUNTRY_NAME", countryName);
                        countryBundle.putString("COUNTRY_DESC", countryDesc);
                        countryBundle.putString("COUNTRY_RATING", countryRating);

                        Intent intent = new Intent(getApplicationContext(), Pop.class);

                        intent.putExtras(countryBundle);
                        startActivity(intent);

                        break;
                    }
                }
            }
            catch(Exception ex)
            {
                Log.d("onPostExecute Error", ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}
