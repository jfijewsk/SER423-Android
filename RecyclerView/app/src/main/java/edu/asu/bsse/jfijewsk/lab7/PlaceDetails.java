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
import android.widget.AdapterView;
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
    TextView greatCircleResult;
    TextView initHeadingResult;


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

        greatCircleResult = (TextView) findViewById(R.id.greatCircleResult);
        initHeadingResult = (TextView) findViewById(R.id.initHeadingResult);

        Button saveButton = (Button) findViewById(R.id.saveChangeBtn);
        Button deleteBtn = (Button) findViewById(R.id.deletePlaceBtn);

        getPlaceDetails(getIntent().getStringExtra("placeName"));
        final Spinner placeSpinner = (Spinner) findViewById(R.id.secondPlaceSpinner);

        placeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Log.d("SPINNER DEBUG", "Selected " + placeSpinner.getSelectedItem().toString());

                final MainActivity mainActivity = new MainActivity();

                PlaceDescription secondPlace = mainActivity.getPlace(placeSpinner.getSelectedItem().toString());

                double greatCircle = calcGreatCircle(currentPlace.getLatitude(), currentPlace.getLongitude(), secondPlace.getLatitude(), secondPlace.getLongitude());
                double initHeading = getBearing(currentPlace.getLatitude(), currentPlace.getLongitude(), secondPlace.getLatitude(), secondPlace.getLongitude());

                greatCircleResult.setText(String.valueOf(greatCircle));
                initHeadingResult.setText(String.valueOf(initHeading));


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
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
            double initHeading = getBearing(currentPlace.getLatitude(), currentPlace.getLongitude(), secondPlace.getLatitude(), secondPlace.getLongitude());

            greatCircleResult.setText(String.valueOf(greatCircle));
            initHeadingResult.setText(String.valueOf(initHeading));
        }



    }

    public double calcGreatCircle(double startLat, double startLong, double endLat, double endLong){
        //Log.d("CALCGREATECRICLE", String.valueOf(startLat));
        //Log.d("CALCGREATECRICLE", String.valueOf(startLong));
        //Log.d("CALCGREATECRICLE", String.valueOf(endLat));
        //Log.d("CALCGREATECRICLE", String.valueOf(endLong));

        final int EARTH_RADIUS = 6371;

        double dLat  = Math.toRadians((endLat - startLat));
        double dLong = Math.toRadians((endLong - startLong));

        startLat = Math.toRadians(startLat);
        endLat   = Math.toRadians(endLat);

        double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (EARTH_RADIUS * c)/1.609;

    }

    private static double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }

    public double getBearing(double lat1, double long1, double lat2, double long2)
    {
        double lat1R = Math.toRadians(lat1);
        double lat2R = Math.toRadians(lat2);
        double dLngR = Math.toRadians(long2- long1);
        double a = Math.sin(dLngR) * Math.cos(lat2R);
        double b = Math.cos(lat1R) * Math.sin(lat2R) - Math.sin(lat1R) * Math.cos(lat2R)
                * Math.cos(dLngR);
        return initialBearing(Math.atan2(a, b));
    }

    public static double normalizeBearing(double bearing) {
        if (Double.isNaN(bearing) || Double.isInfinite(bearing))
            return Double.NaN;
        double bearingResult = bearing % 360;
        if (bearingResult < 0)
            bearingResult += 360;
        return bearingResult;
    }

    public static double initialBearing(double input) {
        return normalizeBearing(Math.toDegrees(input));
    }
}
