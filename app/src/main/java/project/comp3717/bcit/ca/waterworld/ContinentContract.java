package project.comp3717.bcit.ca.waterworld;

import android.provider.BaseColumns;

/**
 * Created by Xavier Lau on 3/11/2016.
 */
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
