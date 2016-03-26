package project.comp3717.bcit.ca.waterworld;

import android.app.DownloadManager;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Connor Phalen on 25/03/2016.
 */
public class GetCountryASync extends AsyncTask<String, Void, String> {

    private final OkHttpClient client = new OkHttpClient();

    protected String doInBackground(String... arg0)
    {
        try
        {

        QueryBuilder query = new QueryBuilder();

        Request request = new Request.Builder()
                .url(query.getBaseURL() + arg0[0] + query.getAPIKeyURLExtension())
                .build();

        Response response = client.newCall(request).execute();

        return response.body().string();
        }
        catch(Exception ex)
        {
            Log.d("GetCountryASync Error", ex.getMessage());
            ex.printStackTrace();
            return (null);
        }
    }

    protected void onPostExecute(final String data)
    {
        final JSONArray array;

        Log.d("data", data);

        try
        {
            array = new JSONArray(data);

             String countryID;
             String countryName;
             int    countryRating;
             String countryDesc;
            JSONObject object;

            for(int i = 0; i < array.length() ; i++)
            {
                object = array.getJSONObject(i);
                if(object.getString("_id") == "CANADA")  /* ----- Need to not hardcode. ---- */
                {
                    countryName   = object.getString("countryName");
                    countryRating = Integer.parseInt(object.getString("countryRating"));
                    countryDesc   = object.getString("countryDesc");
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
