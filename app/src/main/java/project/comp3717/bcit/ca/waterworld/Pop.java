package project.comp3717.bcit.ca.waterworld;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

/**
 * Created by norne on 2016-02-11.
 */
public class Pop extends Activity{
    PopupWindow popUp;
    Button test;
    LayoutInflater layoutInflater;
    RelativeLayout relativeLayout;
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.popup);

       DisplayMetrics dm = new DisplayMetrics();
       getWindowManager().getDefaultDisplay().getMetrics(dm);
       int width = dm.widthPixels;
       int height = dm.heightPixels;
       getWindow().setLayout((int)(width*0.8), (int)(height*0.8));

       /*layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
       ViewGroup container = (ViewGroup) layoutInflater.inflate(null, null);
       View layout = layoutInflater.inflate(R.layout.popup,
               (ViewGroup) findViewById(R.id.popup_element));

       popUp = new PopupWindow(container, 400, 400, true);
       popUp.showAtLocation(relativeLayout, Gravity.NO_GRAVITY, 500, 500);*/










   }


}
