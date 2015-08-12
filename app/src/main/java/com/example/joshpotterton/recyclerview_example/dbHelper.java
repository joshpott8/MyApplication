package com.example.joshpotterton.recyclerview_example;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class dbHelper extends SQLiteOpenHelper {

    public dbHelper(Context context){
        super(context, "FavouriteArticles.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE FavouriteArticles (Title TEXT, Desc TEXT)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP IF EXISTS FavouriteArticles";

        db.execSQL(query);

        onCreate(db);
    }

    public void insertArticle(HashMap<String, String> queryValues){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("Title", queryValues.get("Title"));
        values.put("Desc", queryValues.get("Desc"));

        database.insert("FavouriteArticles", null, values);

        database.close();
    }

    public void deleteRef(String title){
        SQLiteDatabase database = this.getWritableDatabase();

        String query = "DELETE FROM FavouriteArticles WHERE Title = '" + title + "'";

        database.execSQL(query);
    }

    public ArrayList<HashMap<String, String>> getArticles(){
        ArrayList<HashMap<String, String>> articles = new ArrayList<HashMap<String, String>>();

        String query = "SELECT * FROM FavouriteArticles";

        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                HashMap<String, String> article = new HashMap<String, String>();

                article.put("Title", cursor.getString(0));
                article.put("Desc", cursor.getString(1));

                articles.add(article);

            } while(cursor.moveToNext());
        }

        return articles;
    }
}
