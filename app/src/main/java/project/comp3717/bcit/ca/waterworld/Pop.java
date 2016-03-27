package project.comp3717.bcit.ca.waterworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by norne and Connor Phalen on 2016-02-11.
 */
public class Pop extends Activity{

   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.popup);

       DisplayMetrics dm = new DisplayMetrics();
       getWindowManager().getDefaultDisplay().getMetrics(dm);

       final String countryName;
       final String countryDesc;
       final String countryRating;

       int width = dm.widthPixels;
       int height = dm.heightPixels;
       getWindow().setLayout((int)(width*0.8), (int)(height*0.8));

       Intent intent = getIntent();

       countryName   = intent.getStringExtra("COUNTRY_NAME");
       countryDesc   = intent.getStringExtra("COUNTRY_DESC");
       countryRating = intent.getStringExtra("COUNTRY_RATING");

       TextView countryTitleTextView   = (TextView) findViewById(R.id.textViewCountryTitle);
       TextView countryDescTextView    = (TextView) findViewById(R.id.textViewCountryDesc);
       TextView countryRatingTextView  = (TextView) findViewById(R.id.textViewCountryRating);
       //ImageView countryFlagImageView  = (ImageView) findViewById(R.id.imageViewCountryFlag);
       //ImageView countryMeterImageView = (ImageView) findViewById(R.id.imageViewWaterMeter);

       countryTitleTextView.setText(countryName);
       countryDescTextView.setText(countryDesc);
       countryRatingTextView.setText(countryRating); // Not being found for some reason

       //countryDescTextView.setWidth();
   }
}
