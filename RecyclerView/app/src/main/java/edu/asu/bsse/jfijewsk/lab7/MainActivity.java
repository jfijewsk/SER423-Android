package edu.asu.bsse.jfijewsk.lab7;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

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

    private RecyclerView listOfCoursesRV;
    private RecyclerView.Adapter anAdapter;
    private RecyclerView.LayoutManager aLayoutManager;
    private HashMap<String,String> placeNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        placeNames = new HashMap<String,String>();
        placeNames.put("Ser100","Object-Oriented Software Development");
        placeNames.put("Ser200","Core Data Structures with Object Oriented Programming");
        placeNames.put("Ser215","Software Enterprise: Personal Process");
        placeNames.put("Ser216","Software Enterprise: Personal Process and Quality");
        placeNames.put("Ser221","Programming Languages and Their Execution Environment");
        placeNames.put("Ser222","Design and Analysis of Data Structures and Algorithms");
        placeNames.put("Ser232","Computer Systems Fundamentals I");
        placeNames.put("Ser250","Microcomputer Architecture and Programming");
        placeNames.put("Ser315","Software Enterprise: Design and Process");
        placeNames.put("Ser316","Software Enterprise: Construction and Transition");
        placeNames.put("Ser321","Principles of Distributed Software Systems");
        placeNames.put("Ser322","Principles of Database Management");
        placeNames.put("Ser334","Operating Systems and Networks");
        placeNames.put("Ser401","Computing Capstone Project I");
        placeNames.put("Ser402","Computing Capstone Project II");
        placeNames.put("Ser415","Software Enterprise: Inception and Elaboration");
        placeNames.put("Ser416","Software Enterprise: Project and Process Management");
        placeNames.put("Ser421","Web-Based Applications and Mobile Systems");
        placeNames.put("Ser422","Web Application Programming");
        placeNames.put("Ser423", "Mobile Systems");

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
            PlaceDB db = new PlaceDB((Context)this);
            SQLiteDatabase crsDB = db.openDB();

            Cursor cur = crsDB.rawQuery("select name, category from places;",
                    new String[]{});

            while(cur.moveToNext()){
                try{
                    placeNames.put(cur.getString(0), cur.getString(1));
                    System.out.println("SELECTED PLACE =" + cur.getString(0));
                }catch(Exception ex){
                    android.util.Log.w(this.getClass().getSimpleName(),"exception stepping thru cursor"+ex.getMessage());
                }
            }
           // System.out.println("SELECTED PLACE =" + cur.getString(0));
            Log.d("test", "Made it here");

        }
        catch(Exception e){
            Log.d("test", "Errored at getting database file");
            Log.d("test", e.toString());

            //System.out.print(e);
        }
    }
}
