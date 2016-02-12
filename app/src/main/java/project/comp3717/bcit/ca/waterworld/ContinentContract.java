package project.comp3717.bcit.ca.waterworld;

import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;

public final class ContinentContract
{
    private ContinentContract()
    {
    }

    public static abstract class ContinentEntry
            implements BaseColumns
    {
        public static final String TABLE_NAME           = "Continent";
        public static final String COLUMN_NAME_NAME     = "Name";
    }
}