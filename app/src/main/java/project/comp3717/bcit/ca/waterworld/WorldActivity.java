package project.comp3717.bcit.ca.waterworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class WorldActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final ArrayList<String> continents;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world);

        continents = getIntent().getExtras().getStringArrayList("continents");
    }

    public void changeActivity(final View view)
    {
        Intent intent;

        switch((String)view.getTag()){
            case "0":
                intent = new Intent(this, AsiaActivity.class);
                break;
            case "1":
                intent = new Intent(this, AfricaActivity.class);
                break;
            case "2":
                intent = new Intent(this, EuropeActivity.class);
                break;
            case "3":
                intent = new Intent(this, NorthAmericaActivity.class);
                break;
            case "4":
                intent = new Intent(this, AntarcticaActivity.class);
                break;
            case "5":
                intent = new Intent(this, OceaniaActivity.class);
                break;
            case "6":
                intent = new Intent(this, SouthAmericaActivity.class);
                break;
            default:
                intent = new Intent(this, WorldActivity.class);
                break;
        }
        startActivity(intent);
    }
}
