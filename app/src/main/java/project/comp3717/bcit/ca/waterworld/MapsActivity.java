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

/**
 * Created by Xavier Lau, Chiseong Oh and Connor Phalen on 2016-03-26.
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{
    private String                          continent;
    private String                          country;
    private DataDBHelper                    dataDBHelper;
    private ArrayList<String>               continentNames;
    private GoogleMap                       googleMap;
    private final String                    ftCountryAPIKey         = "AIzaSyAm3E9BguE5FWR8IGPaj3YT0XowwRYa4Rk";
    private final String                    ftCountryDataURL        = "https://www.googleapis.com/fusiontables/v2/query?sql="
                                                                        + "SELECT%20*%20FROM%201Olm63OBLeyofjNSThCCLnxRgXJV0g3"
                                                                        + "0iQK-J5tKu&key=";
    private final ArrayList<GeoJsonLayer>   geoJsonLayerArrayList   = new ArrayList<GeoJsonLayer>();
    private final OkHttpClient              client                  = new OkHttpClient();

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
        //countries                       = res.getStringArray(R.array.countries);         //change this when we get countries
        //continents                      = res.getStringArray(R.array.continents);        //change this when we get continents
        continentNames                  = new ArrayList<String>();

        // Execute database retrieval. Parameter is continent you want to look in.
        new GetCountryASync(dbWrite).execute("CountryListNA");
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
        this.googleMap = googleMap;
        //grabs the polygon data and draws the polygons as layers on the map
        new RetrieveCountryData().execute(ftCountryDataURL + ftCountryAPIKey);
    }

    /**
     * Makes a REST call to the fusion table were the country boundry points are stored. Response
     * will return a JSON object. Parse the JSON object to create GeoJSON layers on the map.
     */
    private class RetrieveCountryData extends AsyncTask<String, Void, JSONObject>
    {
        @Override
        protected JSONObject doInBackground(String... params)
        {
            final URL               url;
            final HttpURLConnection httpURLConnection;
            final InputStream       inputStream;
            String                  line;
            BufferedReader          bufferedReader;
            StringBuilder           result              = new StringBuilder();

            try
            {
                url                 = new URL(params[0]);
                httpURLConnection   = (HttpURLConnection) url.openConnection();
                inputStream         = new BufferedInputStream(httpURLConnection.getInputStream());
                bufferedReader      = new BufferedReader(new InputStreamReader(inputStream));

                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line);
                }

                bufferedReader.close();
                inputStream.close();
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

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            GeoJsonLayer    geoJsonLayer;
            JSONArray       rowJSONArray;
            JSONArray       countryJSONArray;
            JSONObject      countryJSONObject;
            String          countryName;

            try
            {
                if (jsonObject != null)
                {
                    rowJSONArray = jsonObject.getJSONArray("rows");

                    for (int i = 0; i < rowJSONArray.length(); i++) {
                        countryJSONArray    = rowJSONArray.getJSONArray(i);
                        countryJSONObject   = new JSONObject(countryJSONArray.getString(1));
                        geoJsonLayer        = new GeoJsonLayer(googleMap, countryJSONObject);
                        geoJsonLayerArrayList.add(geoJsonLayer);
                        geoJsonLayer.addLayerToMap();
                    }
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    private class GetCountryASync extends AsyncTask<String, Void, String>
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
