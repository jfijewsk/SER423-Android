package edu.asu.bsse.jfijewsk.lab7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AddNewPlace extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_new_place2);

        //Create the tool bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);

       // final LayoutInflater factory = getLayoutInflater();
        //final View toolBarView = factory.inflate(R.layout.app_toolbar, null);

        Button button = (Button) toolbar.findViewById(R.id.new_place_button);
        button.setText("Back");

        // Get all the Textfields
        final TextView nameTF = (TextView) findViewById(R.id.nameTF);
        final TextView addressTF1 = (TextView) findViewById(R.id.address1TF);
        final TextView addressTF2 = (TextView) findViewById(R.id.addressTF2);
        final TextView addressTitleTF = (TextView) findViewById(R.id.addressTitleTF);
        final TextView descriptionTF = (TextView) findViewById(R.id.descriptionTF);
        final TextView categoryTF = (TextView) findViewById(R.id.categoryTF);
        final TextView latitudeTF = (TextView) findViewById(R.id.latitudeTF);
        final TextView longitudeTF = (TextView) findViewById(R.id.longitudeTF);
        final TextView elevationTF = (TextView) findViewById(R.id.elevationTF);

        Button saveButton = (Button) findViewById(R.id.saveBtn);
        saveButton.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                Log.d("Save Button hit", "Saving");

                String singleAddress = addressTF1.getText().toString() + "$" +addressTF2.getText().toString();
                double latitude = 0.0;
                double longitude = 0.0;
                double elevation = 0.0;
                // Try to get the double values

                try{
                    latitude = Double.parseDouble(String.valueOf(latitudeTF.getText()));
                    longitude = Double.parseDouble(String.valueOf(longitudeTF.getText()));
                    elevation = Double.parseDouble(String.valueOf(elevationTF.getText()));

                }
                catch(Exception e){
                    Log.d("ERROR", "Could not turn entered values into doubles");
                }
                PlaceDescription newPlace = new PlaceDescription(nameTF.getText().toString(), descriptionTF.getText().toString(),
                        categoryTF.getText().toString(), addressTitleTF.getText().toString(), singleAddress, elevation, latitude, longitude );

                // Add new place to sql dataBase

                ContentValues newPlaceSql = new ContentValues();
                newPlaceSql.put("name", nameTF.getText().toString());
                newPlaceSql.put("addressTitle", addressTitleTF.getText().toString());
                newPlaceSql.put("addressStreet", singleAddress);
                newPlaceSql.put("description", descriptionTF.getText().toString());
                newPlaceSql.put("category", categoryTF.getText().toString());
                newPlaceSql.put("latitude", latitude);
                newPlaceSql.put("longitude", longitude);
                newPlaceSql.put("elevation", elevation);
                MainActivity.dataBase.insert("places", null, newPlaceSql);


                MainActivity.dataBase.execSQL("insert into places (name, addressTitle, addressStreet, description, category, latitude, longitude, elevation)" +
                        " VALUES ('ASU West', 'ASU West Campus', '13591 N 47th Ave$Phoenix AZ 85051', 'Home of ASUs Applied Computing Program', 'School', '33.608979', '-112.159469', '1100.0')");
                }
        });

    }

    public void testing(View view){
        Log.d("Testing", "Button hit");
        finish();
        //Intent intent = new Intent(this, AddNewPlace.class);
       // startActivity(intent);
    }
}
