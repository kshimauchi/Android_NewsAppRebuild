package com.kshimauchi.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.kshimauchi.myapplication.data.DatabaseHelper;
import com.kshimauchi.myapplication.data.DatabaseUtilities;
import com.kshimauchi.myapplication.models.NewsItem;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
/**
 * Created by kshim on 7/22/2017.
 */

public class RefreshTasks  {
//Refreshes the article list to be populating and shown in the views is called at a single entry
    //within the mainActivity class
    public static void refreshArticles(Context context) throws IOException {
        ArrayList<NewsItem> result = null;
        URL url = NetworkUtils.buildURL();
        SQLiteDatabase db = new DatabaseHelper(context).getWritableDatabase();
        try{
            DatabaseUtilities.deleteAll(db);  //drops the table
            String json = NetworkUtils.getResponseFromHttpURL(url);         //gets articles
            result = NetworkUtils.parseJSON(json);                                         //parses the articles and is stored in a results arraylist
            DatabaseUtilities.bulkInsert(db, result);                                       //inserts the data from the arraylist into the database

        }catch (IOException | JSONException e){
            e.printStackTrace();
        }
        db.close();
    }
}
