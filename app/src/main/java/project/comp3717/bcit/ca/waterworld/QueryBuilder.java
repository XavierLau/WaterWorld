package project.comp3717.bcit.ca.waterworld;

import project.comp3717.bcit.ca.waterworld.ConnectionTest;

/**
 * Created by Connor Phalen on 25/03/2016.
 */
public class QueryBuilder {

    // Reference: https://michaelkyazze.wordpress.com/2014/05/18/android-mongodb-mongolab-hosted-sample-app-part-one/

    /**
     * Gets the name of the MongoLab Database.
     * @return Returns the name of the database.
     */
    public String getDBName()
    {
        return "waterqualityinspector";
    }

    /**
     * Grabs the main account API Key to connect to the database through.
     * @return Returns and API Key.
     */
    public String getAPIKey()
    {
        return "ADVrQRa6oYvobyjN8ut13VloOk1Xsf4q";
    }

    public String getBaseURL()
    {
        return "https://api.mlab.com/api/1/databases/" + getDBName() + "/collections/";
    }

    /**
     * Gets the URL extension that includes a copy of the API Key.
     * @return Returns the API Key to be used with a URL.
     */
    public String getAPIKeyURLExtension()
    {
        return "?apiKey="+ getAPIKey();
    }

    /**
     * Returns the "countryinfo" collection name.
     * @return Returns the "countryinfo" collection name.
     */
    public String getCollectionName()
    {
        return "countryinfo";
    }

    /**
     * Builds the full URL that allows access to the "countryinfo" collection
     * in the database.
     * @return Returns the full URL to access the "countryinfo" collection.
     */
    public String buildCountryAccessURL()
    {
        return getBaseURL() + getCollectionName() + getAPIKeyURLExtension();
    }


}
