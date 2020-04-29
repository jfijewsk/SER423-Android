package edu.asu.bsse.jfijewsk.lab7;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PlaceDetails extends AppCompatActivity {

    PlaceDescription currentPlace;
    TextView nameTF;
    TextView addressTF1;
    TextView addressTF2;
    TextView addressTitleTF;
    TextView descriptionTF;
    TextView categoryTF;
    TextView latitudeTF;
    TextView longitudeTF;
    TextView elevationTF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        //Create the tool bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);

        Button button = (Button) toolbar.findViewById(R.id.new_place_button);
        button.setText("Back");

        // Get all the Textfields
        nameTF = (TextView) findViewById(R.id.detailNameTF);
        addressTF1 = (TextView) findViewById(R.id.detailAddressTF1);
        addressTF2 = (TextView) findViewById(R.id.detailAddressTF2);
        addressTitleTF = (TextView) findViewById(R.id.detailAddressTitleTF);
        descriptionTF = (TextView) findViewById(R.id.detailDescriptionTF);
        categoryTF = (TextView) findViewById(R.id.detailCategoryTF);
        latitudeTF = (TextView) findViewById(R.id.detailLatitudeTF);
        longitudeTF = (TextView) findViewById(R.id.detailLongitudeTF);
        elevationTF = (TextView) findViewById(R.id.detailElevationTF);

        Button saveButton = (Button) findViewById(R.id.saveChangeBtn);
        Button deleteBtn = (Button) findViewById(R.id.deletePlaceBtn);

        getPlaceDetails(getIntent().getStringExtra("placeName"));
    }

    public void toolBarAction(View view){
        //Log.d("Testing", "Button hit");

        finish();
    }

    public void getPlaceDetails(String name) {
        // Get the selected place information
        final MainActivity mainActivity = new MainActivity();

        currentPlace = mainActivity.getPlace(name);

        Log.d("DEBUG", currentPlace.getAddress());
        String[] addressArray = currentPlace.getAddress().split("\\$");

        nameTF.setText(currentPlace.getName());
        addressTF1.setText(addressArray[0]);
        addressTF2.setText(addressArray[1]);
        addressTitleTF.setText(currentPlace.getAddress_title());
        descriptionTF.setText(currentPlace.getDescription());
        categoryTF.setText(currentPlace.getCategory());
        elevationTF.setText(Double.toString(currentPlace.getElevation()));
        latitudeTF.setText(Double.toString(currentPlace.getLatitude()));
        longitudeTF.setText(Double.toString(currentPlace.getLongitude()));

        //Log.d("DATA", "GOT PLACE INFO and address is " + currentPlace.getAddress());
        // Populate spinner
        List<String> spinnerArray =  new ArrayList<String>();
        String[] allPlacesNames = mainActivity.getAllPlaces();

        if(allPlacesNames != null) {
            for (int i = 0; i < allPlacesNames.length; i++) {
                spinnerArray.add(allPlacesNames[i]);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, spinnerArray);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Spinner placeSpinner = (Spinner) findViewById(R.id.secondPlaceSpinner);
            placeSpinner.setAdapter(adapter);

            PlaceDescription secondPlace = mainActivity.getPlace(placeSpinner.getSelectedItem().toString());
            double greatCircle = calcGreatCircle(currentPlace.getLatitude(), currentPlace.getLongitude(), secondPlace.getLatitude(), secondPlace.getLongitude());

            Log.d("GREAT CIRCLE CALC", String.valueOf(greatCircle));
        }



    }

    public double calcGreatCircle(double lat1, double long1, double lat2, double long2){
        // great circle distance in radians
        double angle1 = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.cos(long1 - long2));

        // convert back to degrees
        angle1 = Math.toDegrees(angle1);

        // each degree on a great circle of Earth is 60 nautical miles
        double distance1 = 60 * angle1;

        System.out.println(distance1 + " nautical miles");

        double a = Math.pow(Math.sin((lat2-lat1)/2), 2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin((long2-long1)/2), 2);

        // great circle distance in radians
        double angle2 = 2 * Math.asin(Math.min(1, Math.sqrt(a)));

        // convert back to degrees
        angle2 = Math.toDegrees(angle2);

        // each degree on a great circle of Earth is 60 nautical miles
        double distance2 = 60 * angle2;

        return distance2;
    }
}
