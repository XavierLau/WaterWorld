package project.comp3717.bcit.ca.waterworld;

import android.provider.BaseColumns;

/**
 * Created by Xavier Lau on 3/11/2016.
 */
public final class CountryContract
{
    private CountryContract()
    {
    }

    public static abstract class CountryEntry
            implements BaseColumns
    {
        public static final String TABLE_NAME                   = "Country";
        public static final String COLUMN_NAME_NAME             = "Name";
        public static final String COLUMN_NAME_DESCRIPTION      = "Description";
        public static final String COLUMN_NAME_RATING           = "Rating";
    }
}
