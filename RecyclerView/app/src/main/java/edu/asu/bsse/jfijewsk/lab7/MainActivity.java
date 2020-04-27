package edu.asu.bsse.jfijewsk.lab7;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.Toolbar;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

/*
 MainActivity.java
 Assign7jfijewsk
 Created by James on 3/18/20.
 Copyright © 2020 James. All rights reserved.
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 http://www.apache.org/licenses/LICENSE-2.0
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 @author   James Fijewski   mailto:jfijewsk@asu.edu.
 @version March 30, 2020
 */

public class MainActivity extends AppCompatActivity {

    private static RecyclerView listOfCoursesRV;
    private static RecyclerView.Adapter anAdapter;
    private static RecyclerView.LayoutManager aLayoutManager;
    private static HashMap<String,String> placeNames;
    private static Context mContext;
    private static PlaceDB db;
    private SQLiteDatabase dataBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getApplicationContext();

        //Create the tool bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);

        placeNames = new HashMap<String,String>();

        setContentView(R.layout.activity_main);
        listOfCoursesRV = (RecyclerView)findViewById(R.id.recycler_view);
        listOfCoursesRV.setHasFixedSize(true);
        aLayoutManager = new LinearLayoutManager(this);
        listOfCoursesRV.setLayoutManager(aLayoutManager);
        anAdapter = new PlaceListAdapter(placeNames);
        listOfCoursesRV.setAdapter(anAdapter);

        // Try to get database
        String selectedPlace = "null";
        try{
            db = new PlaceDB((Context)this);
            dataBase = db.openDB();

            Cursor cur = dataBase.rawQuery("select name, category from places;",
                    new String[]{});

            while(cur.moveToNext()){
                try{
                    placeNames.put(cur.getString(0), cur.getString(1));
                    Log.d("Debud", "SELECTED PLACE =" + cur.getString(0));
                    Log.d("Debud1", "SELECTED PLACE =" + cur.getString(1));

                }catch(Exception ex){
                    android.util.Log.w(this.getClass().getSimpleName(),"exception stepping thru cursor"+ex.getMessage());
                }
            }


        }
        catch(Exception e){
            Log.d("test", "Errored at getting database file");
            Log.d("test", e.toString());

            //System.out.print(e);
        }
    }

    public void testing(View view){
        Log.d("Testing", "Button hit");

        Intent intent = new Intent(this, AddNewPlace.class);
        startActivity(intent);
    }

    public boolean addPlace(ContentValues newPlace){
        //dataBase.execSQL("insert into places (name, addressTitle, addressStreet, description, category, latitude, longitude, elevation)" +
        //        " VALUES ('ASU West', 'ASU West Campus', '13591 N 47th Ave$Phoenix AZ 85051', 'Home of ASUs Applied Computing Program', 'School', '33.608979', '-112.159469', '1100.0')");

        try{

            //Log.d("Info", DB_PATH.toString());
            //Context dabaBaseContent =  ContextSingleton.getContext();

            if(db == null){
                Log.d("Debug", "db is null");
                db = new PlaceDB(this.getApplicationContext());
            }
            //PlaceDB db = new PlaceDB(this);
            dataBase = db.openDB();

            long result = dataBase.insert("places", null, newPlace);
            if(result == -1){
                throw new RuntimeException();
            }
            dataBase.close();
            db.close();

            Log.d("NEWPLACE DATA", newPlace.getAsString("name"));

            placeNames.put(newPlace.getAsString("name"), newPlace.getAsString("category"));
            anAdapter = new PlaceListAdapter(placeNames);
            listOfCoursesRV.setAdapter(anAdapter);
            return true;

        }
        catch(Exception e){
            Log.d("test", "Errored at getting or saving database file");
            Log.d("test", e.toString());

            //System.out.print(e);
        }



        return false;
    }

}
