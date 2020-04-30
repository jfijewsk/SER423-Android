package edu.asu.bsse.jfijewsk.lab7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class AddNewPlace extends AppCompatActivity {

    private TextView nameTF;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final MainActivity mainActivity = new MainActivity();

        setContentView(R.layout.activity_add_new_place2);

        //Create the tool bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);

       // final LayoutInflater factory = getLayoutInflater();
        //final View toolBarView = factory.inflate(R.layout.app_toolbar, null);

        Button button = (Button) toolbar.findViewById(R.id.new_place_button);
        button.setText("Back");

        // Get all the Textfields
        nameTF = (TextView) findViewById(R.id.nameTF);
        final TextView addressTF1 = (TextView) findViewById(R.id.addressTF1);
        final TextView addressTF2 = (TextView) findViewById(R.id.addressStreetTF2);
        final TextView addressTitleTF = (TextView) findViewById(R.id.addressTitle);
        final TextView descriptionTF = (TextView) findViewById(R.id.descriptionTF);
        final TextView categoryTF = (TextView) findViewById(R.id.categoryTF);
        final TextView latitudeTF = (TextView) findViewById(R.id.latitudeTF);
        final TextView longitudeTF = (TextView) findViewById(R.id.longitudeTF);
        final TextView elevationTF = (TextView) findViewById(R.id.elevationTF);

        Button saveButton = (Button) findViewById(R.id.saveBtn);
        saveButton.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {

                String nameInput = nameTF.getText().toString();

                // Only save if they enter in a name
                if(!nameInput.equals("")) {
                    String singleAddress = addressTF1.getText().toString() + "$" + addressTF2.getText().toString();
                    double latitude = 0.0;
                    double longitude = 0.0;
                    double elevation = 0.0;
                    // Try to get the double values

                    try {
                        latitude = Double.parseDouble(String.valueOf(latitudeTF.getText()));
                        longitude = Double.parseDouble(String.valueOf(longitudeTF.getText()));
                        elevation = Double.parseDouble(String.valueOf(elevationTF.getText()));

                    } catch (Exception e) {
                        Log.d("ERROR", "Could not turn entered values into doubles");
                    }
                    PlaceDescription newPlace = new PlaceDescription(nameTF.getText().toString(), descriptionTF.getText().toString(),
                            categoryTF.getText().toString(), addressTitleTF.getText().toString(), singleAddress, elevation, latitude, longitude);

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

                    popUpMessage(mainActivity.addPlace(newPlaceSql));


                    //MainActivity.dataBase.insert("places", null, newPlaceSql);
                }

                else{
                    Toast.makeText(getApplicationContext(), "You did not enter a name", Toast.LENGTH_SHORT).show();

                }
            }
                ;
        });

    }

    public void testing(View view){
        Log.d("Testing", "Button hit");
        finish();
        //Intent intent = new Intent(this, AddNewPlace.class);
       // startActivity(intent);
    }

    private void popUpMessage(boolean result){

        if(result) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(nameTF.getText().toString() + " added to sql database!");
            alertDialogBuilder.setPositiveButton("yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Log.d("Debug", "Clicked ok");
                            finish();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(nameTF.getText().toString() + " failed to be added to sql database! Make sure there is not already a duplicate place in the database.");
            alertDialogBuilder.setPositiveButton("yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Log.d("Debug", "Clicked ok");
                            //Toast.makeText(this,"You clicked yes button",Toast.LENGTH_LONG).show();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    public void toolBarAction(View view){
        //Log.d("Testing", "Button hit");

        finish();
    }
}
