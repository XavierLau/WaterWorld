package project.comp3717.bcit.ca.waterworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AfricaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_africa);
    }

    public void viewCountryInfo(final View view){
        Toast.makeText(this, "Text", Toast.LENGTH_SHORT).show();
    }
}
