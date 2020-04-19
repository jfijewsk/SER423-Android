package edu.asu.bsse.jfijewsk.lab7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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

    }

    public void testing(View view){
        Log.d("Testing", "Button hit");
        finish();
        //Intent intent = new Intent(this, AddNewPlace.class);
       // startActivity(intent);
    }
}
