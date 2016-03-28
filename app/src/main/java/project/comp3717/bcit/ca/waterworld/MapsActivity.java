package project.comp3717.bcit.ca.waterworld;

import android.content.ContentValues;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.maps.android.geojson.GeoJsonLayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{
    private DataDBHelper                dataDBHelper;
    private ArrayList<String> continentNames;
    private GoogleMap googleMap;
    private final String ftCountryAPIKey = "AIzaSyAm3E9BguE5FWR8IGPaj3YT0XowwRYa4Rk";
    private final String ftCountryDataURL = "https://www.googleapis.com/fusiontables/v2/query?sql="
                                            + "SELECT%20*%20FROM%201Olm63OBLeyofjNSThCCLnxRgXJV0g3"
                                            + "0iQK-J5tKu&key=";
    private final ArrayList<GeoJsonLayer> geoJsonLayerArrayList = new ArrayList<GeoJsonLayer>();

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
        continentNames                  = new ArrayList<String>();

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
        final RetrieveCountryData retrieveCountryData;

        this.googleMap = googleMap;
        retrieveCountryData = new RetrieveCountryData();
        retrieveCountryData.execute(ftCountryDataURL + ftCountryAPIKey);

        /*InputStream stream = getResources().openRawResource(R.raw.test);

        String line;
        StringBuilder result = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        try
        {
            while ((line = reader.readLine()) != null) {
                // Read and save each line of the stream
                result.append(line);
            }
            // Close the stream
            reader.close();
            stream.close();
            JSONObject jsonObject = new JSONObject(result.toString());
            JSONArray rows;
            rows = jsonObject.getJSONArray("rows");
            JSONArray countries;
            countries = rows.getJSONArray(0);

            Log.d("json", countries.getString(1));
            geoJsonLayer = new GeoJsonLayer(this.googleMap, new JSONObject(countries.getString(1)));
            geoJsonLayer.addLayerToMap();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }*/

        /*try {
            geoJsonLayer = new GeoJsonLayer(googleMap, R.raw.test, getApplicationContext());
            geoJsonLayer.addLayerToMap();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e)
        {
            e.printStackTrace();
        }*/
    }

    private class RetrieveCountryData extends AsyncTask<String, Void, JSONObject>
    {
        @Override
        protected JSONObject doInBackground(String... params)
        {
            final URL url;
            final HttpURLConnection httpURLConnection;
            final JSONObject jsonObject;
            final InputStream inputStream;
            String line;
            StringBuilder result = new StringBuilder();
            BufferedReader bufferedReader;

            try
            {
                url = new URL(params[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line);
                }

                bufferedReader.close();
                inputStream.close();
                Log.d("json", result.toString());
                return new JSONObject(result.toString());
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(JSONObject jsonObject) {
            GeoJsonLayer geoJsonLayer;
            JSONArray rowJSONArray;
            JSONArray countryJSONArray;
            JSONObject countryJSONObject;
            String countryName;

            try
            {
                if (jsonObject != null)
                {
                    rowJSONArray = jsonObject.getJSONArray("rows");

                    for (int i = 0; i < rowJSONArray.length(); i++) {
                        countryJSONArray = rowJSONArray.getJSONArray(i);
                        countryJSONObject = new JSONObject(countryJSONArray.getString(1));
                        geoJsonLayer = new GeoJsonLayer(googleMap, countryJSONObject);
                        geoJsonLayerArrayList.add(geoJsonLayer);
                        geoJsonLayer.addLayerToMap();
                    }
                }
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }
}
