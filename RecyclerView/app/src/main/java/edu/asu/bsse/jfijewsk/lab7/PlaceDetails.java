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
import android.widget.Button;
import android.widget.TextView;

public class PlaceDetails extends AppCompatActivity {

    PlaceDescription currentPlace;


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
        final TextView nameTF = (TextView) findViewById(R.id.detailNameTF);
        final TextView addressTF1 = (TextView) findViewById(R.id.detailAddressTF1);
        final TextView addressTF2 = (TextView) findViewById(R.id.detailAddressTF2);
        final TextView addressTitleTF = (TextView) findViewById(R.id.detailAddressTitleTF);
        final TextView descriptionTF = (TextView) findViewById(R.id.detailDescriptionTF);
        final TextView categoryTF = (TextView) findViewById(R.id.detailCategoryTF);
        final TextView latitudeTF = (TextView) findViewById(R.id.detailLatitudeTF);
        final TextView longitudeTF = (TextView) findViewById(R.id.detailLongitudeTF);
        final TextView elevationTF = (TextView) findViewById(R.id.detailElevationTF);

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

        Log.d("DATA", "GOT PLACE INFO and address is " + currentPlace.getAddress());
    }
}
