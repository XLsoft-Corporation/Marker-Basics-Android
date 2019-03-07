package com.xlsoft.markerbasics;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import eu.kudan.kudan.ARAPIKey;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ARAPIKey key = ARAPIKey.getInstance();
        key.setAPIKey("x5twkAEPQXupAlIDv3PurcSOwnr9c3940VLf2e711EDe5r9kfk2CpvWSX2qAepXDGRNIU1SNHWZZZh3bMfH1s5iw9DQxI5Q4Si4n5V+eIlH6a8U9KVWs3bzRCPVxKJZuiBjRXnqymQYJ9RM6t6WZFlrWa69U2OuOp6gwAwGW3YniNA/FbQOkXR0mepIbIIwY3Eps05445ILiBuBWtp2+VlXA/GWg9BXCGptgcdbScLbXvTky6OijvhKqlJOOoa7yFL1Sn2234PSnlO9CNt+soj2ZSCrxN4JvcOVnjjXsmDpCXpGf8dHf7feQQIAyYiHNGX5rqNsA6WbSu+yf7NKPgZgx8Cxha7GC35d2vjzZA3daOYA+Kpzwtt3PzEHuiwdyskpRs0ULWb9Jp2UyHZPRqr1g1q3e07qK0HkGsb1yQZmaXOXcrcvgfbVVThaCxKFbKRBv8XWI2sF912XpsidAYE0R/RE40D+Mup3FotSx507ydA0U2frWdyT9nwxsCEUFF9dhAjUAHaf/tG+eaRu0nP7f4AiQfnam914uNCw7YVYzF8Gi4ekEDU2zTalGhVo5iyUSqdNsJ5W7VLwKSs76HaeMVVSEwpMnrrIw6ebjmoUPscbg6toZnOpe2YS+UvsXPNjrMD8col9EzkrJZ4dT321n9SesWtIY9Bw2UlvHlvI=");
        permissionsRequest();
    }

    // Requests app permissions
    public void permissionsRequest() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 111);

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 111: {
                if (grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED ) {

                } else {
                    permissionsNotSelected();
                }
            }
        }
    }

    private void permissionsNotSelected() {
        AlertDialog.Builder builder = new AlertDialog.Builder (this);
        builder.setTitle("Permissions Requred");
        builder.setMessage("Please enable the requested permissions in the app settings in order to use this demo app");
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener () {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                System.exit(1);
            }
        });
        AlertDialog noInternet = builder.create();
        noInternet.show();
    }


    public void startARActivity(View view) {
        Intent intent = new Intent(MainActivity.this, ARCameraActivity.class);
        startActivity(intent);
    }
}
