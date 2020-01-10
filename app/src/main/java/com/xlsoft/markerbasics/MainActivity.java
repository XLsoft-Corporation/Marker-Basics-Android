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
        key.setAPIKey("woaqx7oL/6rN8DgQGkd+F47mHvXjXvRcfdy/M5IqIGbXvTrzf1sazVkLHjD9jxutHh5usgByJhakOAvE5EI3omhwk+CYtySgx/3jKBy/d7/4OVvuh3+/umBtCb8qEd2Tuto0fkUNFU+e2Pem7I/JMqNyStBvB6u6KmSuuWlGMeAu7XWNlsLJyhn8iSdLlgXEJDnw+qJCYWmuZhVD48HHTsX20DcQFdhQQxvnnpqbGhXsRbn53z0hyrPE38p9snoFyTKMiNcB7iJh00sEF+Zq3O0I087EapTsk78g6hjCJcZjrDl8dnDRmm/Dk5aASDjkTfWFkOYO5LH0RGnHxCuaa3MhVb5ao3P5aArMwRuTVBh5ZZOHjBi8VjRqK+UN+hs0lk4GiRVfVph2+ejb4REoSouxSi9oiqeNlTzezuy8U4W93CSFyn5X4BHgmsL1nVq3YfS95jrMjhbk64FZYZ+9FidxoulaNEREaXgRQQo1LViZXI1ESLqTiQPGGQvQ9JBf+j2k1MBfhCX9SkEjug3q8Tn5c1oIk4ryDgGfaq1lexD71wI0Q5a9d+8vMg8gE3FKWrzm63M75kv8bIWvq3u18Pl23A2WaX6lwuNyeprGCHaD25V5s41uIm62kySrQjEfbLfKAxi3VPEVlUCpqlLZqGj6chDOTWYb9S76atUiFXE=");
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
