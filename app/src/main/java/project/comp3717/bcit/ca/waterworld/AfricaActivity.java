package project.comp3717.bcit.ca.waterworld;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOverlay;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AfricaActivity extends AppCompatActivity {

    final int OVERLAY_X_MARGIN = 75;
    final int OVERLAY_Y_MARGIN = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_africa);
    }

    public void buttonExitOverlay(View currentButton){

    }

    public void viewCountryInfo(final View view){

        View parent = (View)view.getParent().getParent();

        //Log.d(Integer.toString(parent.getMeasuredWidth()), "Parent Width");
        //Log.d(Integer.toString(parent.getMeasuredHeight()), "Parent Height");

        int  width  = parent.getMeasuredWidth();
        int  height = parent.getMeasuredHeight();

        LinearLayout              overlay = new LinearLayout(getApplicationContext());
        LinearLayout.LayoutParams param   = new LinearLayout.LayoutParams(width - (OVERLAY_X_MARGIN * 2),
                                                                          height -(OVERLAY_Y_MARGIN * 2));

        overlay.setBackgroundColor(Color.BLUE);

        overlay.setX(OVERLAY_X_MARGIN);
        overlay.setY(OVERLAY_Y_MARGIN);

        this.addContentView(overlay, param);

        overlay.bringToFront();

        Button exitOverlayButton = new Button(getApplicationContext());

        overlay.addView(exitOverlayButton);

        exitOverlayButton.setX(overlay.getX() - OVERLAY_X_MARGIN);
        exitOverlayButton.setY(overlay.getY() - OVERLAY_Y_MARGIN);

        exitOverlayButton.setWidth(OVERLAY_X_MARGIN);
        exitOverlayButton.setHeight(OVERLAY_X_MARGIN);

        TextView countryTitle = new TextView(getApplicationContext());

        overlay.addView(countryTitle);

        countryTitle.setX(overlay.getX());
        countryTitle.setY(overlay.getY() + OVERLAY_Y_MARGIN);

        countryTitle.setWidth(500);
        countryTitle.setHeight(500);

        countryTitle.setText(view.getTag().toString());

        countryTitle.setTextSize(40);

        /*
        for(int i=0; i<((ViewGroup)parent).getChildCount(); ++i) {
            View child = ((ViewGroup)parent).getChildAt(i);
            child.setEnabled(false);
        }
        */

    }
}
